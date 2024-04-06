package Product;

import Asset.Asset;
import TradingStrategy.MyEuropeanOption;
import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.plots.Named;
import net.finmath.plots.Plot;
import net.finmath.plots.Plot2D;
import net.finmath.stochastic.RandomVariable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;


public abstract class Option extends Derivative 
{

    public Option(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility)
    {
        super(price, asset, derivativeDate, strikePrice, volatility);
    }
    

    public abstract void calculatePrice();

    private double calculateAssumedPrice(double initialPrice, int days, double strikePrice, boolean isCall)
    {
        if (days>getNumberOfTimeSteps()) {
            days = getNumberOfTimeSteps();
        }

        double maturity= getNumberOfTimeSteps()-days;
        var europeanOption = new MyEuropeanOption(maturity,strikePrice);
        var simulation = (initialPrice>=0) ? new MonteCarloAssetModel(generateProcessForPriceAndDays(initialPrice,getNumberOfTimeSteps()-days)) :
                new MonteCarloAssetModel(getProcess());
        RandomVariable valueOfCall = null;
        try {
            valueOfCall = europeanOption.getCallOrPutValue(0.0, simulation, isCall);
        } catch (CalculationException e) {
            return 0;
        }

        return valueOfCall.average().doubleValue();

    }

    private double calculateAssumedPriceCommon(int days, double strikePrice, boolean isCall)
    {
        return calculateAssumedPrice(-1,days,strikePrice,isCall);
    }

    private double calculateAssumedPriceCommonWithNewInitialPrice(double initialPrice, int days, double strikePrice, boolean isCall)
    {
        return calculateAssumedPrice(initialPrice,days,strikePrice,isCall);
    }

    public double calculateAssumedCallPrice(int days, double strikePrice)
    {
        return calculateAssumedPriceCommon(days, strikePrice, true);
    }

    public double calculateAssumedCallPrice(LocalDate endDate, double strikePrice)
    {
        return calculateAssumedCallPrice(getNumberOfTimeStepsUntil(endDate), strikePrice);
    }

    public double calculateAssumedPutPrice(int days, double strikePrice)
    {
        return calculateAssumedPriceCommon(days, strikePrice, false);
    }

    private double calculateAssumedOptionPrice(int day, double usedPrice, List<Double> path, boolean isCall)
    {
        return calculateAssumedPriceCommonWithNewInitialPrice(path.get(day), day, usedPrice, isCall);
    }

    private void printAverageAndOption(double temporaryStrikePrice, boolean isCall)
    {
        String text = isCall ? "call cost" : "put profit";

        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();

        final double usedPrice = temporaryStrikePrice<=0 ? getStrikePrice() : temporaryStrikePrice;
        DoubleUnaryOperator doubleUnaryOperator1= day->getAverage().get((int)day);
        operators.add(new Named<>("average",doubleUnaryOperator1));

        final List<Double> path=getRandomPath();
        DoubleUnaryOperator doubleUnaryOperator2= day->calculateAssumedOptionPrice((int)day, usedPrice,path, true)+Math.min(usedPrice,path.get(path.size()-1));
        operators.add(new Named<>(text+" on "+usedPrice,doubleUnaryOperator2));

        DoubleUnaryOperator doubleUnaryOperator3= isCall ? day->path.get((int)day): day -> Math.max(usedPrice,path.get(path.size()-1)) - calculateAssumedOptionPrice((int)day, usedPrice,path, false);;
        operators.add(new Named<>("random path "+usedPrice,doubleUnaryOperator3));
        Plot plot = new Plot2D(0.0, getNumberOfTimeSteps(), getNumberOfTimeSteps(), operators);
        plot.setTitle("Average (red) and "+text+" for 115 (green) and a random path (blue)").setXAxisLabel("day").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void printAverageAndCallOption(double temporaryStrikePrice)
    {
        printAverageAndOption(temporaryStrikePrice, true);
    }

    public void printAverageAndPutOption(double temporaryStrikePrice)
    {
        printAverageAndOption(temporaryStrikePrice, false);
    }

    public void printOptions(double temporaryStrikePrice)
    {
        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();

        final double usedPrice= (temporaryStrikePrice<=0 ? getStrikePrice() : temporaryStrikePrice);
        DoubleUnaryOperator doubleUnaryOperator1= day->calculateAssumedPutPrice((int)day, usedPrice);
        operators.add(new Named<>("put price on "+usedPrice,doubleUnaryOperator1));
        DoubleUnaryOperator doubleUnaryOperator2= day->calculateAssumedCallPrice((int)day, usedPrice);
        operators.add(new Named<>("call price on "+usedPrice,doubleUnaryOperator2));
        Plot plot = new Plot2D(0.0, getNumberOfTimeSteps(), getNumberOfTimeSteps(), operators);
        plot.setTitle("Put and call price on "+usedPrice).setXAxisLabel("day").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<Double> getOptionPrices(double usedPrice, int steps, int cycles, boolean call, List<Double> path)
    {
        ArrayList<Double> prices=new ArrayList<>();
        for(int i=0;i<cycles;i++)
        {
            prices.add(calculateAssumedOptionPrice(i*steps, usedPrice, path, call));
        }

        return prices;

    }
}

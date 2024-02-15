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
import java.util.Date;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public abstract class Option extends Derivative {
    public Option(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility) {
        super(price, asset, derivativeDate, strikePrice, volatility);
    }

    public abstract void calculatePrice();

    public double calculateAssumedPriceCommon(int days, double strikePrice, boolean isCall) {
        if (days>getNumberofTimeSteps()) days=getNumberofTimeSteps();

        double maturity=(double)(getNumberofTimeSteps()-days);
        var europeanOption = new MyEuropeanOption(maturity,strikePrice);
        var simulation = new MonteCarloAssetModel(getProcess());
        RandomVariable valueOfCall = null;
        try {
            valueOfCall = europeanOption.getCallOrPutValue(0.0, simulation, isCall);
        } catch (CalculationException e) {
            return 0;
        }
        return valueOfCall.average().doubleValue();
    }

    public double calculateAssumedPriceCommonWithNewInitialPrice(double initialPrice, int days, double strikePrice, boolean isCall) {

        if (days>getNumberofTimeSteps()) days=getNumberofTimeSteps();
        double maturity=(double)(getNumberofTimeSteps()-days);
        var europeanOption = new MyEuropeanOption(maturity,strikePrice);
        var simulation = new MonteCarloAssetModel(generateProcessForPriceandDays(initialPrice,getNumberofTimeSteps()-days));
        RandomVariable valueOfCall = null;
        try {
            valueOfCall = europeanOption.getCallOrPutValue(0.0, simulation, isCall);
        } catch (CalculationException e) {
            return 0;
        }
        return valueOfCall.average().doubleValue();
    }
    public double calculateAssumedCallPrice(int days, double strikePrice)
    {
        double callp=calculateAssumedPriceCommon(days, strikePrice, true);
        return callp;
    }
    public double calculateAssumedCallPrice(LocalDate endDate, double strikePrice)
    {
        int days=getNumberofTimeStepsUntil(endDate);
        return calculateAssumedCallPrice(days, strikePrice);
    }
    public double calculateAssumedPutPrice(int days, double strikePrice)
    {
        return calculateAssumedPriceCommon(days, strikePrice, false);
    }

    private double calculateAssumedOptionPrice(int day, double usedPrice, List<Double> path, boolean isCall)
    {
        double optionPrice=calculateAssumedPriceCommonWithNewInitialPrice(path.get(day), day, usedPrice, isCall);
        return optionPrice;
    }
    public void printAverageAndCallOption(double temporaryStrikePrice){
        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();
        if (temporaryStrikePrice<=0) temporaryStrikePrice=getStrikePrice();
        final double usedPrice= (temporaryStrikePrice<=0 ? getStrikePrice() : temporaryStrikePrice);
        DoubleUnaryOperator doubleUnaryOperator1= day->getAverage().get((int)day);
        operators.add(new Named<>("average",doubleUnaryOperator1));

        final List<Double> path=getRandomPath();
        DoubleUnaryOperator doubleUnaryOperator2= day->calculateAssumedOptionPrice((int)day, usedPrice,path, true)+Math.min(usedPrice,path.get(path.size()-1));
        operators.add(new Named<>("call cost on "+usedPrice,doubleUnaryOperator2));

        DoubleUnaryOperator doubleUnaryOperator3= day->path.get((int)day);
        operators.add(new Named<>("random path "+usedPrice,doubleUnaryOperator3));
        Plot plot = new Plot2D(0.0, getNumberofTimeSteps(), getNumberofTimeSteps(), operators);
        plot.setTitle("Average (red) and call cost for 115 (green) and a random path (blue)").setXAxisLabel("day").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void printAverageAndPutOption(double temporaryStrikePrice){
        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();
        if (temporaryStrikePrice<=0) temporaryStrikePrice=getStrikePrice();
        final double usedPrice= (temporaryStrikePrice<=0 ? getStrikePrice() : temporaryStrikePrice);
        DoubleUnaryOperator doubleUnaryOperator1= day->getAverage().get((int)day);
        operators.add(new Named<>("average",doubleUnaryOperator1));

        final List<Double> path=getRandomPath();
        DoubleUnaryOperator doubleUnaryOperator2= day -> Math.max(usedPrice,path.get(path.size()-1)) - calculateAssumedOptionPrice((int)day, usedPrice,path, false);
        operators.add(new Named<>("put profit on "+usedPrice,doubleUnaryOperator2));

        DoubleUnaryOperator doubleUnaryOperator3= day->path.get((int)day);
        operators.add(new Named<>("random path "+usedPrice,doubleUnaryOperator3));
        Plot plot = new Plot2D(0.0, getNumberofTimeSteps(), getNumberofTimeSteps(), operators);
        plot.setTitle("Average (red) and put profit for 115 (green) and a random path (blue)").setXAxisLabel("day").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void printOptions(double temporaryStrikePrice){
        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();
        if (temporaryStrikePrice<=0) temporaryStrikePrice=getStrikePrice();
        final double usedPrice= (temporaryStrikePrice<=0 ? getStrikePrice() : temporaryStrikePrice);
        DoubleUnaryOperator doubleUnaryOperator1= day->calculateAssumedPutPrice((int)day, usedPrice);
        operators.add(new Named<>("put price on "+usedPrice,doubleUnaryOperator1));
        DoubleUnaryOperator doubleUnaryOperator2= day->calculateAssumedCallPrice((int)day, usedPrice);
        operators.add(new Named<>("call price on "+usedPrice,doubleUnaryOperator2));
        Plot plot = new Plot2D(0.0, getNumberofTimeSteps(), getNumberofTimeSteps(), operators);
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
        int day=0;
        for(int i=0;i<cycles;i++)
        {
            prices.add(calculateAssumedOptionPrice(day+i*steps, usedPrice, path, call));
        }
        return prices;
    }
}

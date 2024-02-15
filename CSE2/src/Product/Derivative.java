package Product;

import Asset.Asset;
import Helpers.TimeSerie;
import net.finmath.exception.CalculationException;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.process.EulerSchemeFromProcessModel;
import net.finmath.plots.DoubleToRandomVariableFunction;
import net.finmath.plots.Plot;
import net.finmath.plots.Plot2D;
import net.finmath.plots.PlotProcess2D;
import net.finmath.time.TimeDiscretizationFromArray;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public abstract class Derivative extends Spot {
    private LocalDate derivativeDate;
    private double strikePrice;
    private double volatility;

    private TimeSerie timeSerie;

    private TimeDiscretizationFromArray td;

    private final int NUMOFPATHS=1000;

    public EulerSchemeFromProcessModel getProcess() {
        return process;
    }

    private EulerSchemeFromProcessModel process;

    private BrownianMotion brownianMotion;

    public List<Double> getAverage() {
        return average;
    }

    private List<Double> average;

    private List<Double> randomPath;

    public Derivative(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility) {
        super(price, asset);
        this.derivativeDate = derivativeDate;
        this.strikePrice = strikePrice;
        this.volatility = volatility;
        timeSerie=new TimeSerie(LocalDate.now(),derivativeDate);
        average=new ArrayList<>();
        randomPath=new ArrayList<>();
        generateProcess();
        generateAverage();
        generatePath();
    }

    public void recreateEndDate(int days)
    {
        timeSerie.resetLength(days);
        generateProcess();
        generateAverage();
    }

    private void generateProcess(){
        var model = new BlackScholesModel(getAssetPrice(), getAsset().getDailyRiskFreeRate(), volatility);
        td = new TimeDiscretizationFromArray(0.0, getNumberofTimeSteps(), 1.0);

        brownianMotion = new BrownianMotionFromMersenneRandomNumbers(td, 1, NUMOFPATHS, 3231);
        process = new EulerSchemeFromProcessModel(model, brownianMotion);
    }
    public EulerSchemeFromProcessModel generateProcessForPriceandDays(double currentPrice, int timesteps){
        BrownianMotion localMotion;
        if (timesteps>=getNumberofTimeSteps())
            localMotion=brownianMotion;
        else {
            TimeDiscretizationFromArray ltd = new TimeDiscretizationFromArray(0.0, timesteps, 1.0);
            localMotion = brownianMotion.getCloneWithModifiedTimeDiscretization(ltd);
        }
        var model = new BlackScholesModel(currentPrice, getAsset().getDailyRiskFreeRate(), volatility);
        return new EulerSchemeFromProcessModel(model, localMotion);
    }

    private void generateAverage(){
        for (int i=0;i<=getNumberofTimeSteps();i++) {
            try {
                var randomValue = process.getProcessValue(i);
                average.add(randomValue[0].getAverage());
            } catch (CalculationException e) {

            }
        }
    }

    public void printAverage(){
        DoubleUnaryOperator doubleUnaryOperator=day->average.get((int)day);
        Plot plot = new Plot2D(0.0, getNumberofTimeSteps(), getNumberofTimeSteps(), doubleUnaryOperator);
        plot.setTitle("Average").setXAxisLabel("day").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void printPath(int numofPaths){
        DoubleToRandomVariableFunction paths = time -> process.getProcessValue(td.getTimeIndex(time), 0 /* assetIndex */);
        var plot = new PlotProcess2D(td, paths, numofPaths);
        plot.setTitle("Black Scholes model paths").setXAxisLabel("time").setYAxisLabel("value");
        plot.show();

    }
    private void generatePath()
    {
        int rnd=(int)(Math.random()*NUMOFPATHS);
        for(int i=0;i<=getNumberofTimeSteps();i++)
        {
            randomPath.add(process.getProcessValue(i,0).get(rnd));
        }
    }
    public List<Double> getRandomPath()
    {
        /*
        ArrayList<Double> result=new ArrayList<>();
        int rnd=(int)(Math.random()*NUMOFPATHS);
        for(int i=0;i<=getNumberofTimeSteps();i++)
        {
            result.add(process.getProcessValue(i,0).get(rnd));
        }
        return result;
        */
        return  randomPath;

    }

    public abstract void calculatePrice();

    // Getters and setters for the private variables

    public LocalDate getDerivativeDate() {
        return derivativeDate;
    }

    public void setDerivativeDate(LocalDate derivativeDate) {
        this.derivativeDate = derivativeDate;
        timeSerie.changeTime(LocalDate.now(),derivativeDate);
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public int getNumberofTimeSteps(){
        //return 252;
        return timeSerie.getNumberofWorkDates();
    }
    public int getNumberofTimeStepsUntil(LocalDate endDate){
        //return 252;
        return timeSerie.getNumberofWorkDatesUntil(endDate);
    }
}

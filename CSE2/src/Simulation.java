import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import Asset.KubernetesPod;
import Helpers.General2DTable;
import Product.Call;
import Resources.Resources;
import net.finmath.exception.CalculationException;
import net.finmath.plots.*;


public class Simulation
{
    private Market market;
    private Market exampleMarket;
    private static final int STEPS = 252; // 252 trading days in a year
    private static final double VOLATILITY = 0.015; //Stock price volatility (e.g.: 2%)
    //for new version:
    private static final double TIME_STEP = 1.0;
    private static final double STRIKE_PRICE = 115.0;
    double RISK_FREE_RATE = 0.05;
    private final Random random = new Random();
    KubernetesPod kubernetesPod;
    Call option;


    public Simulation(Market market, Market exampleMarket)
    {
        this.market = market;
        this.exampleMarket = exampleMarket;
        kubernetesPod=new KubernetesPod(5.0,new Resources(2, 5, 2),
                "Budapest", RISK_FREE_RATE);
        option=new Call(-1, kubernetesPod, LocalDate.now().plusYears(1),STRIKE_PRICE,VOLATILITY);
    }

    public static void main(String[] args)
    {
        Market market = new Market();
        Market exampleMarket = new Market();
        Simulation simulation = new Simulation(market, exampleMarket);
        //simulation.simulate();
        //150 days call options calculated every 10th days several different strike prices
        simulation.printOptionValues(true);

        //150 days put options...
        simulation.printOptionValues(false);

        //plots of averages, paths, call options...
        simulation.simulateBlackSholes2();
    }

    public void simulateBlackSholes2()
    {
        option.recreateEndDate(STEPS);
        System.out.println(option.calculateAssumedCallPrice(0,STRIKE_PRICE));
        option.printAverage();
        option.printPath(100);
        option.printAverageAndCallOption(-1);
        option.printAverageAndPutOption(-1);
        option.printOptions(-1);
    }

    private void createAndShowTable(boolean call, KubernetesPod kubernetesPod, Double endPrice, List<Double> path, ArrayList<List<Double>> results) {
        General2DTable table=new General2DTable((call ? "Call" : "Put").concat(" option values for different strike prices"), "150 days, spot price= "
                .concat(String.valueOf(kubernetesPod.getPrice()).concat(", price at 150. day: ").concat(endPrice.toString())),
                17,7 );

        table.addHeader("Strike price",0);
        for(int i=0;i<16;i++)
            table.addHeader("Day ".concat(String.valueOf(i*10)),i+1);
        table.addData("Daily price",0,0);
        for(int i=0;i<6;i++)
            table.addData(90+i*5,0,i+1);
        for(int i=0;i<16;i++) {
            table.addData(path.get(i*10), i + 1, 0);

            for (int j = 0; j < 6; j++) {
                table.addData(results.get(j).get(i), i + 1, j + 1);
                Double win=0.0;
                if (call) //buy
                {
                    //option cost vs strike price. win: option cost<end price
                    // Option cost: option price + min(end price, strike price)
                    //win: end price-option cost
                    win=endPrice - (results.get(j).get(i) + Math.min(endPrice, 90.0 + j * 5.0));
                }
                else //put (sell)
                {
                    //option profit vs end price. Win: option profit>end price
                    // Option profit: max(end price,strike price)-option price
                    //win: option profit-end price
                    win = Math.max(endPrice, 90.0 + j * 5.0) - results.get(j).get(i) - endPrice;

                }
                if (win > 0.5) table.setColor(Color.GREEN, i + 1, j + 1);
                else if (win < -0.5) table.setColor(Color.RED, i + 1, j + 1);
            }
        }

        table.show();

    }

    private void createAndShowPlot(ArrayList<Named<DoubleUnaryOperator>> operators)
    {
        Plot plot = new Plot2D(0.0, 15, 16, operators);
        plot.setTitle("...").setXAxisLabel("day*10").setYAxisLabel("value");
        try {
            plot.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void calculateOptionPricesAndAddOperators(boolean call, List<Double> path, ArrayList<Named<DoubleUnaryOperator>> operators, ArrayList<List<Double>> results)
    {
        for(int i=0;i<=6;i++) {
            results.add(option.getOptionPrices(90+i*5,10,16, call,path));
            int finalI = i;
            operators.add(new Named<>("average", day->results.get(finalI).get((int)day)));
        }
    }
    public void printOptionValues(boolean call)
    {
        option.recreateEndDate(150);
        ArrayList<Named<DoubleUnaryOperator>> operators=new ArrayList<>();
        ArrayList<List<Double>> results=new ArrayList<>();
        final List<Double> path=option.getRandomPath();
        calculateOptionPricesAndAddOperators(call, path, operators, results);
        createAndShowPlot(operators);
        Double endPrice=path.get(150);
        createAndShowTable(call,kubernetesPod,endPrice,path,results);

    }

    // Getters and setters for the private variables

    public Market getMarket()
    {
        return market;
    }

    public void setMarket(Market market)
    {
        this.market = market;
    }

    public Market getExampleMarket()
    {
        return exampleMarket;
    }

    public void setExampleMarket(Market exampleMarket)
    {
        this.exampleMarket = exampleMarket;
    }
}

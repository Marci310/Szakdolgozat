import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import Assets.KubernetesPod;
import Helpers.PlotShowCase;
import Helpers.TimeSeries;
import Assets.Resources;
import Product.*;


public class Simulation {
    private Market market;
    private final Random random = new Random();
    List<LocalDate> calendar;

    public Simulation(Market market, LocalDate[] dates) throws Exception {
        this.market = market;
        TimeSeries workCalculator = new TimeSeries(dates[0], dates[1]);
        this.calendar = workCalculator.getWorkDates();
        Resources resource1 = new Resources(2, 4, 10);
        Resources resource2 = new Resources(1, 4, 20);
        KubernetesPod pod1 = new KubernetesPod(1, resource1, 5, market.getRiskFreeInterestRate(), 60);
        KubernetesPod pod2 = new KubernetesPod(30, resource1, 5, market.getRiskFreeInterestRate(), 60);
        KubernetesPod pod3= new KubernetesPod(10,resource2,5,market.getRiskFreeInterestRate(),30);
        market.addAsset(pod1, calendar.size());
        market.addAsset(pod2, calendar.size());
        market.addAsset(pod3, calendar.size());
        PlotShowCase plot = new PlotShowCase();
        PlotShowCase plot1 = new PlotShowCase();
        plot.showCaseOne(pod1.getAssetPrice().subList(0, 60), "Price movement of Kubernetes Pod");
        //plot1.showCaseOne(pod3.getAssetPrice().subList(0, 30), "Price movement of Kubernetes Pod 1");
        Agent agent1 = new Agent();
        Agent agent2 = new Agent();
        Agent agent3 = new Agent();
        market.addAgent(agent1);
        market.addAgent(agent2);

        //market.makeTrade(0,new BullPutSpread(market.getImpliedVolatility(),30, 70,71, calendar.get(0),calendar.get(29),pod1,0));
        //market.makeTrade(0,new BullCallSpread(market.getImpliedVolatility(),30, 71,70, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new BearCallSpread(market.getImpliedVolatility(),30, 71,71.5, calendar.get(0),calendar.get(29),pod1,0));
        //market.makeTrade(0,new BearCallSpread(market.getImpliedVolatility(),30, 70,71, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new ShortCall(market.getImpliedVolatility(),30, 50, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new ShortPut(market.getImpliedVolatility(),30, 6, calendar.get(0),calendar.get(29),pod1));
        //market.makeTrade(0,new LongCall(market.getImpliedVolatility(),30, 60, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new LongPut(market.getImpliedVolatility(),30, 5, calendar.get(0),calendar.get(29),pod1));


    }

    public void simulate() {
        market.makeTrade(0, new LongPut(market.getImpliedVolatility(), 11, 14, calendar.get(0), calendar.get(10), market.getAsset(0), 0));
        market.makeTrade(0, new ShortPut(market.getImpliedVolatility(), 11, 14, calendar.get(0), calendar.get(10), market.getAsset(0), 0));
        //market.makeTrade(0,new BearPutSpread(market.getImpliedVolatility(),11, 13.4,13.5, calendar.get(19),calendar.get(29),market.getAsset(0),19));
        market.makeTrade(0,new BullPutSpread(market.getImpliedVolatility(),11, 13.7,13.9, calendar.get(29),calendar.get(39),market.getAsset(0),29));
        for (int i = 0; i < calendar.size(); ++i) {
            /*if (i == 0)
                market.makeTrade(0, new LongPut(market.getImpliedVolatility(), 60, Math.round(market.getAsset(1).getCurrentPrice(i) * 10 / 10)  + 3, calendar.get(i), calendar.get(60), market.getAsset(0), i));
            if (i == 30) {
                market.makeTrade(0, new ShortPut(market.getImpliedVolatility(), 30, Math.round(market.getAsset(1).getCurrentPrice(i) * 10 / 10) + 3, calendar.get(i), calendar.get(i + 31), market.getAsset(0), i));
                market.makeTrade(0,new BullPutSpread(market.getImpliedVolatility(),30, Math.round(market.getAsset(1).getCurrentPrice(i) * 10 / 10) + 2,Math.round(market.getAsset(1).getCurrentPrice(i) * 10 / 10) + 3, calendar.get(i),calendar.get(i + 31),market.getAsset(0),i));
            }*/
            /*if(i == 0){
                market.makeTrade(0,new LongCall(market.getImpliedVolatility(),30, market.getAsset(0).getCurrentPrice(i), calendar.get(i),calendar.get(29),market.getAsset(0),i));
                market.makeTrade(0,new ShortCall(market.getImpliedVolatility(),30, market.getAsset(0).getCurrentPrice(i) - 2, calendar.get(i),calendar.get(29),market.getAsset(0),i));
                market.makeTrade(0,new BullCallSpread(market.getImpliedVolatility(),30, market.getAsset(2).getCurrentPrice(i) + 4,market.getAsset(2).getCurrentPrice(i) + 1, calendar.get(i),calendar.get(29),market.getAsset(2),i));
                market.makeTrade(0,new BearCallSpread(market.getImpliedVolatility(),30, market.getAsset(2).getCurrentPrice(i) - 6,market.getAsset(2).getCurrentPrice(i) -3, calendar.get(i),calendar.get(29),market.getAsset(2),i));
            }*/

            market.showcaseAgents(i, calendar.get(i));
        }
        try {
            PrintWriter writer = new PrintWriter("trades.txt");
            writer.println(market.getTrades());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(market.getTrades());
    }
}

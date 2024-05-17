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

    public Simulation(Market market,LocalDate[] dates) throws Exception {
        this.market = market;
        TimeSeries workCalculator= new TimeSeries(dates[0],dates[1]);
        this.calendar = workCalculator.getWorkDates();
        Resources resource1 = new Resources(2,4,10);
        Resources resource2 = new Resources(1,4,20);
        KubernetesPod pod1= new KubernetesPod(100,resource1,50,market.getRiskFreeInterestRate(),30);
        KubernetesPod pod2= new KubernetesPod(10,resource2,50,market.getRiskFreeInterestRate(),30);
        market.addAsset(pod1, calendar.size());
        market.addAsset(pod2, calendar.size());
        PlotShowCase plot = new PlotShowCase();
        plot.showCaseOne(pod1.getAssetPrice().subList(0,60),"alma");
        Agent agent1 = new Agent();
        Agent agent2 = new Agent();
        market.addAgent(agent1);
        market.addAgent(agent2);
        //market.makeTrade(0,new BullPutSpread(market.getImpliedVolatility(),30, 69,70, calendar.get(0),calendar.get(29),pod1,0));
        //market.makeTrade(0,new BearCallSpread(market.getImpliedVolatility(),30, 71,71.5, calendar.get(0),calendar.get(29),pod1,0));
        //market.makeTrade(0,new BearCallSpread(market.getImpliedVolatility(),30, 70,71, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new ShortCall(market.getImpliedVolatility(),30, 50, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new ShortPut(market.getImpliedVolatility(),30, 6, calendar.get(0),calendar.get(29),pod1));
        //market.makeTrade(0,new LongCall(market.getImpliedVolatility(),30, 60, calendar.get(0),calendar.get(29),pod1,30));
        //market.makeTrade(0,new LongPut(market.getImpliedVolatility(),30, 5, calendar.get(0),calendar.get(29),pod1));


    }

    public void simulate(){
        for (int i = 0; i< calendar.size(); ++i){
            market.showcaseAgents(i, calendar.get(i));

        }
    }

}

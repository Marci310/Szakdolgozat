import Assets.Asset;
import Product.Derivative;
import Product.Option;
import Product.Spread;

import java.time.LocalDate;
import java.util.ArrayList;


public class Market {
    private ArrayList<Agent> agents = new ArrayList<>();
    private ArrayList<Asset> assets = new ArrayList<>();

    /*the assets volatility. it is only here because we use roughly the same assets.
    using this will allow us to change the asset volatility evereywhere and not have to iterate throught
    them individually*/
    private double impliedVolatility;

    private String trades = "";

    /*
        we will keep this at 3% for the sake of easiness
         */

    private double riskFreeInterestRate;

    public Market(double impliedVolatility, double riskFreeInterestRate) {

        this.impliedVolatility = impliedVolatility;
        this.riskFreeInterestRate = riskFreeInterestRate;
    }

    public void addAsset(Asset asset, int workDays) {
        asset.addAssetPrice(new Derivative(asset, workDays, impliedVolatility).getRandomPath());
        assets.add(asset);
    }


    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public void showcaseAgents(int i, LocalDate currentDate) {
        for(Agent agent: agents) {
            try {
                agent.showcasePortfolio(i, currentDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void makeTrade(int agent, Option option) {
        agents.get(agent).addOption(option);
        trades += "Agent " + agent + " bought " + option.getTitle() + "\n";
    }

    public void makeTrade(int agent, Spread spread) {
        agents.get(agent).addSpread(spread);
        trades += "Agent " + agent + " bought " + spread.getTitle() + "\n";
    }

    public Asset getAsset(int i) {
        return assets.get(i);
    }


    // Getters and setters for the private variables
    public Agent getAgent(int index) {
        return agents.get(index);
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public double getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(int impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public String getTrades() {
        return trades;
    }

    public double getRiskFreeInterestRate() {
        return riskFreeInterestRate;
    }

    public void setRiskFreeInterestRate(int riskFreeInterestRate) {
        this.riskFreeInterestRate = riskFreeInterestRate;
    }
}

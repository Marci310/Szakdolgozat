import Asset.Asset;
import Product.Product;


public class Market {
    private Agent[] agents;
    private Asset[] assets;
    private int impliedVolatility;
    private String trades;
    private int riskFreeInterestRate;


    public Market(Agent[] agents, Asset[] assets, int impliedVolatility, int riskFreeInterestRate) {
        this.agents = agents;
        this.assets = assets;
        this.impliedVolatility = impliedVolatility;
        this.riskFreeInterestRate = riskFreeInterestRate;
    }

    public Market() {    /*ez ide nem kell*/
        this.agents = null;
        this.assets = null;
        this.impliedVolatility = 1;
        this.riskFreeInterestRate = 1;
    }


    public void makeTrade(Agent agent, Product product) {
        // Implementation goes here
    }

    // Getters and setters for the private variables

    public Agent[] getAgents() {
        return agents;
    }

    public void setAgents(Agent[] agents) {
        this.agents = agents;
    }

    public Asset[] getAssets() {
        return assets;
    }

    public void setAssets(Asset[] assets) {
        this.assets = assets;
    }

    public int getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(int impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public String getTrades() {
        return trades;
    }

    public void setTrades(String trades) {
        this.trades = trades;
    }

    public int getRiskFreeInterestRate() {
        return riskFreeInterestRate;
    }

    public void setRiskFreeInterestRate(int riskFreeInterestRate) {
        this.riskFreeInterestRate = riskFreeInterestRate;
    }
}

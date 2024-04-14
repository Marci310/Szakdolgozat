import TradingStrategy.TradingStrategy;


public class Agent {
    private TradingStrategy trading;


    public Agent(TradingStrategy trading) {
        this.trading = trading;
    }

    // Getters and setters for the private variable

    public TradingStrategy getTrading() {
        return trading;
    }

    public void setTrading(TradingStrategy trading) {
        this.trading = trading;
    }
}

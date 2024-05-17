package Product;


import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

import java.time.LocalDate;

public class LongCall extends Call {

    public LongCall(double volatility, int maturity, double strikePrice, LocalDate startDate, LocalDate expirationDate, Asset asset, int i) {
        super(volatility, maturity, strikePrice, startDate, expirationDate, asset,i);

    }

    public double calculateProfit(double currentPrice) {
        double profit = (currentPrice - getStrikePrice() - getValue()) * 100;
        return Math.max(-(getValue() * 100), profit);
    }

    @Override
    public String getTitle() {
        return ("Long Call option profit from " + getStartDate().toString() + " till " + getExpirationDate().toString() + "\n at strike: " + getStrikePrice() + " with original: " + getOriginalPrice());
    }


}

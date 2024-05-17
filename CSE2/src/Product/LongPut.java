package Product;


import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

import java.time.LocalDate;

public class LongPut extends Put {

    public LongPut(double volatility, int maturity, double strikePrice, LocalDate startDate, LocalDate expirationDate, Asset asset, int i) {
        super(volatility, maturity, strikePrice, startDate, expirationDate, asset,i);

    }

    public double calculateProfit(double currentPrice) {
        double profit = (getStrikePrice() - currentPrice - getValue()) * 100;
        return Math.max(-(getValue() * 100), profit);
    }

    @Override
    public String getTitle() {
        return ("Long Put option profit from " + getStartDate().toString() + " till " + getExpirationDate().toString() + "\n at strike: " + getStrikePrice() + " with original: " + getOriginalPrice());
    }


}

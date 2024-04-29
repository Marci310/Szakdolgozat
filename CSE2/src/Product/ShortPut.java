package Product;


import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

import java.time.LocalDate;

public class ShortPut extends Put {

    public ShortPut(double volatility, int maturity,  double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset) {
        super(volatility, maturity,strikePrice, startDate,expirationDate,asset);

    }

    public double calculateProfit(double currentPrice) {
        double profit = (currentPrice - getStrikePrice() + getValue()) * 100;
        return Math.min(profit, getValue() * 100);
    }

    @Override
    public String getTitle() {
        return ("Short Put option profit from " + getStartDate().toString() + " till " + getExpirationDate().toString()+ "\n at strike: "+ getStrikePrice() + " with original: " + getOriginalPrice());
    }
}
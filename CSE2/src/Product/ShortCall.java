package Product;


import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

import java.time.LocalDate;

public class ShortCall extends Call {

    public ShortCall(double volatility, int maturity,  double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset, int i) {
        super(volatility, maturity,strikePrice, startDate,expirationDate,asset,i);

    }

    public double calculateProfit(double currentPrice) {
        double profit = (getStrikePrice() - currentPrice + getValue()) * 100;
        return Math.min(profit, getValue() * 100);
    }

    @Override
    public String getTitle() {
        return ("Short Call option profit from " + getStartDate().toString() + " till " + getExpirationDate().toString()+ "\n at strike: "+ getStrikePrice() + " with original: " + getOriginalPrice());
    }

}

package Product;


import net.finmath.functions.AnalyticFormulas;
import Assets.Asset;

import java.time.LocalDate;

public class Call extends Option {

    public Call(double volatility, int maturity,  double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset) {
        super(volatility, maturity,strikePrice, startDate,expirationDate,asset);
        getOptionPrice();
    }

    @Override
    public void getOptionPrice() {
        double volatility = getVolatility();
        int maturity = getMaturity()/365;
        double originalPrice = getOriginalPrice();
        double strikePrice = getStrikePrice();
        LocalDate startDate = getStartDate();
        double riskFreeRate = getRiskFreeRate();

        setValue(AnalyticFormulas.blackScholesOptionValue(originalPrice, riskFreeRate, volatility, maturity, strikePrice,true));
    }

    @Override
    public double calculateProfit(double currentPrice) {
        return 0;
    }

    @Override
    public String getTitle() {
        return "Call";
    }

}
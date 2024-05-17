package Product;


import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

import java.time.LocalDate;

public class Put extends Option {

    public Put(double volatility, int maturity,  double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset, int i) {
        super(volatility, maturity,strikePrice, startDate,expirationDate,asset,i);
        getOptionPrice();
    }

    @Override
    public void getOptionPrice() {
        double volatility = getVolatility();
        double maturity = (double)getMaturity()/365;
        double originalPrice = getOriginalPrice();
        double strikePrice = getStrikePrice();
        LocalDate startDate = getStartDate();
        double riskFreeRate = getRiskFreeRate();

        setValue( AnalyticFormulas.blackScholesOptionValue(originalPrice, riskFreeRate, volatility, maturity, strikePrice,false));
    }

    @Override
    public double calculateProfit(double currentPrice) {
        return 0;
    }

    @Override
    public String getTitle() {
        return "Put";
    }

}
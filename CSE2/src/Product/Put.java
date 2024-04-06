package Product;

import Asset.Asset;
import java.time.LocalDate;


public class Put  extends Option
{

    public Put(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility)
    {
        super(price, asset, derivativeDate, strikePrice, volatility);
        calculatePrice();
    }

    @Override
    public void calculatePrice()
    {
        // Implementation for calculating the price of a put option goes here
        this.setPrice(calculateAssumedPutPrice(getNumberOfTimeSteps(), getStrikePrice()));
    }
}

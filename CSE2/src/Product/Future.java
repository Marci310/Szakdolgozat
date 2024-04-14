package Product;

import Asset.Asset;

import java.time.LocalDate;
import java.util.Date;

public class Future extends Derivative {
    public Future(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility) {
        super(price, asset, derivativeDate, strikePrice, volatility);
    }

    @Override
    public void calculatePrice() {
        // Implementation for calculating the price of a future goes here
    }
}
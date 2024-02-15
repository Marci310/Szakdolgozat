package Product;

import Asset.Asset;
import net.finmath.plots.Named;
import net.finmath.plots.Plot;
import net.finmath.plots.Plot2D;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.DoubleUnaryOperator;

public class Call  extends Option {
    public Call(double price, Asset asset, LocalDate derivativeDate, double strikePrice, double volatility) {
        super(price, asset, derivativeDate, strikePrice, volatility);
        calculatePrice();
    }

    @Override
    public void calculatePrice() {
        // Implementation for calculating the price of a call option goes here
        this.setPrice(calculateAssumedCallPrice(getNumberofTimeSteps(), getStrikePrice()));
    }

}

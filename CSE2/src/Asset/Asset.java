package Asset;

import Product.Product;
import Resources.Resources;

import java.util.Date;

public class Asset {
    private static final int STEPS = 252; // 252 trading days in a year

    private double unitPrice;
    private double price;
    private Resources resources;
    private String location;

    private double riskFreeRate;
    private double dailyRiskFreeRate;

    public Asset(double price, Resources resources, String location, double riskFreeRate) {
        this.unitPrice = price;
        this.resources = resources;
        this.location = location;
        this.riskFreeRate=riskFreeRate;
        createPrice();
        dailyRiskFreeRate=Math.pow(1.0 + riskFreeRate, 1.0 / (double) STEPS) - 1.0;
    }

    public void createPrice() {
        // Implementation for creating the price of the asset goes here
        price=unitPrice*resources.getSize();
    }

    // Getters for the private variables

    public double getPrice() {
        return price;
    }

    public Resources getResources() {
        return resources;
    }

    public String getLocation() {
        return location;
    }
    //risk free rate for a year
    public double getRiskFreeRate() {
        return riskFreeRate;
    }
    public double getDailyRiskFreeRate() {
        return dailyRiskFreeRate;
    }
}


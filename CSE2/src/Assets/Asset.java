package Assets;

import java.util.List;

public class Asset {
    private double price;
    private int distance;
    private int unitCount;
    private Resources resources;
    private double riskFreeRate;
    private List<Double> assetPrice;
    public double days;

    public Asset(int unitCount, Resources resources, int distance, double riskFreeRate, int steps) {
        this.unitCount = unitCount;
        this.distance = distance;
        this.resources = resources;
        createPrice();
        this.riskFreeRate = riskFreeRate;
    }

    public void createPrice() {
        double resourcePrice = resources.getPrice();
        double distanceDiscount = 0.2 * (1 - (double) distance / 100);
        price = (resourcePrice - (distanceDiscount * resourcePrice) + 0.1 * resourcePrice) * unitCount;
    }


    // Getters for the private variables

    public double getPrice() {
        return price;
    }

    public double getDailyRiskFreeRate(int steps) {
        return Math.pow(1.0 + riskFreeRate, 1.0 / (double) steps) - 1.0;
    }

    public void addAssetPrice(List<Double> assetPrice) {
        this.assetPrice = assetPrice;
    }

    public List<Double> getAssetPrice() {
        return assetPrice;
    }

    public double getCurrentPrice(int day) {
        return assetPrice.get(day);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "price=" + price +
                ", distance=" + distance +
                ", unitCount=" + unitCount +
                ", resources=" + resources +
                ", dailyRiskFreeRate=" + riskFreeRate +
                '}';
    }
}


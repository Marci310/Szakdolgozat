package Product;

import Assets.Asset;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public abstract class Option
{
    private LocalDate startDate;
    private LocalDate expirationDate;
    private double strikePrice;
    private double originalPrice;
    private int maturity;
    private double volatility;
    private double value;
    private double riskFreeRate;
    private List<Double> profits = new ArrayList<>();
    private Asset asset;

    public Option(double volatility, int maturity, double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset, int i) {
        this.volatility = volatility;
        this.maturity = maturity;
        this.originalPrice = asset.getCurrentPrice(i);
        this.strikePrice = strikePrice;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.riskFreeRate = asset.getDailyRiskFreeRate(maturity);
        this.asset = asset;
    }

    public abstract void getOptionPrice();

    public abstract double calculateProfit(double currentPrice);

    public boolean addOptionProfit(double profit, LocalDate currentDate){
        if(currentDate.isAfter(expirationDate)) {
            return false;
        }
        else if (currentDate.isEqual(expirationDate)) {
            profits.add(profit);
            return true;
        }
        profits.add(profit);
        return false;
    }

    public abstract String getTitle();

    public List<Double> getAssetPrices(){
        return asset.getAssetPrice();
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public double getStrikePrice() {
        return (double) Math.round(strikePrice * 100) /100;
    }

    public double getOriginalPrice() {
        return (double) Math.round(originalPrice * 100) /100;
    }

    public int getMaturity() {
        return maturity;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getValue() {
        return value;
    }

    public double getRiskFreeRate() {
        return riskFreeRate;
    }

    public List<Double> getProfits() {
        return profits;
    }
}

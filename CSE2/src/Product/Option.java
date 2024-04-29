package Product;

import Assets.Asset;
import net.finmath.functions.AnalyticFormulas;

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

    public Option(double volatility, int maturity, double strikePrice, LocalDate startDate,LocalDate expirationDate, Asset asset) {
        this.volatility = volatility;
        this.maturity = maturity;
        this.originalPrice = asset.getPrice();
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
        return strikePrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
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

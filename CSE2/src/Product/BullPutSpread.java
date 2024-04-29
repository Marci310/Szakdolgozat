package Product;

import Assets.Asset;

import java.time.LocalDate;

public class BullPutSpread extends Spread{

    public BullPutSpread(double volatility, int maturity, double longStrikePrice,double shortStrikePrice, LocalDate startDate, LocalDate expirationDate, Asset asset){
        super(new LongPut(volatility, maturity, longStrikePrice, startDate, expirationDate, asset), new ShortPut(volatility, maturity, shortStrikePrice, startDate, expirationDate, asset));
    }

    @Override
    public String getTitle() {
        return "Bull Put Spread with long put at " + getOption1().getStrikePrice() + " and short put at " + getOption2().getStrikePrice() + "\n from" + getOption1().getStartDate().toString() + " till " + getOption1().getExpirationDate().toString();
    }
}

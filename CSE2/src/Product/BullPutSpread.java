package Product;

import Assets.Asset;

import java.time.LocalDate;

/**Long call and higher short call*/
public class BullPutSpread extends Spread{

    public BullPutSpread(double volatility, int maturity, double longStrikePrice,double shortStrikePrice, LocalDate startDate, LocalDate expirationDate, Asset asset, int i){
        super(new LongPut(volatility, maturity, longStrikePrice, startDate, expirationDate, asset,i), new ShortPut(volatility, maturity, shortStrikePrice, startDate, expirationDate, asset,i));
    }

    @Override
    public String getTitle() {
        return "Bull Put Spread with long put at " + getOption1().getStrikePrice() + " and short put at " + getOption2().getStrikePrice() + "\n from" + getOption1().getStartDate().toString() + " till " + getOption1().getExpirationDate().toString();
    }
}

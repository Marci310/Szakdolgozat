package Product;

import Assets.Asset;

import java.time.LocalDate;

/**Short put with higher long put*/
public class BearPutSpread extends Spread{
    public BearPutSpread(double volatility, int maturity, double shortStrikePrice, double longStrikePrice, LocalDate startDate, LocalDate expirationDate, Asset asset, int i){
        super(new LongCall(volatility, maturity, longStrikePrice, startDate, expirationDate, asset,i), new ShortCall(volatility, maturity, shortStrikePrice, startDate, expirationDate, asset,i));
    }

    @Override
    public String getTitle() {
        return "Bear Put Spread with long call at " + getOption1().getStrikePrice() + " and short call at " + getOption2().getStrikePrice() + "\n from" + getOption1().getStartDate().toString() + " till " + getOption1().getExpirationDate().toString();
    }
}

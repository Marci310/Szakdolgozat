import Helpers.PlotShowCase;
import Helpers.TableShowCase;
import Product.Option;
import Product.Spread;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Agent {
    private final List<Option> options = new ArrayList<>();
    private final List<Spread> spreads = new ArrayList<>();
    private static final PlotShowCase plot = new PlotShowCase();
    private static final TableShowCase table = new TableShowCase();


    public Agent() {

    }

    public void addOption(Option option) {
        options.add(option);
    }

    public void addSpread(Spread spread) {
        spreads.add(spread);
    }

    public void showcasePortfolio(int i, LocalDate currentDate) throws Exception {
        for (Option option : options) {
            List<Double> originalPath = option.getAssetPrices();
            if (option.addOptionProfit(option.calculateProfit(originalPath.get(i)), currentDate)) {
                List<List<Double>> pathes = new ArrayList<>();
                List<Double> path = originalPath.subList((i+1) - option.getMaturity(), i);
                pathes.add(path);
                pathes.add(option.getProfits());
                plot.showCaseMore(pathes, option.getTitle());
                table.showCase(pathes,option.getTitle());
            }
        }
        for (Spread spread : spreads) {
            List<Double> originalPath = spread.getOption1().getAssetPrices();
            if (spread.getOption1().addOptionProfit(spread.calculateProfit(originalPath.get(i)), currentDate)) {
                List<List<Double>> pathes = new ArrayList<>();
                List<Double> path = originalPath.subList((i+1) - spread.getOption1().getMaturity(), i);
                pathes.add(path);
                pathes.add(spread.getOption1().getProfits());
                plot.showCaseMore(pathes, spread.getTitle());
                table.showCase(pathes,spread.getTitle());
            }
        }
    }


}

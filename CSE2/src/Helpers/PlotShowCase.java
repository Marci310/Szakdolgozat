package Helpers;

import net.finmath.plots.Named;
import net.finmath.plots.Plot;
import net.finmath.plots.Plot2D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class PlotShowCase {

//may not need
    public void showCaseOne(List<Double> path,String title) throws Exception {
        ArrayList<Named<DoubleUnaryOperator>> operators = new ArrayList<>();
        operators.add(new Named<>("average", day -> path.get((int) day)));
        Plot plot = new Plot2D((double) 0,(double) path.size()-1,path.size(),operators);
        plot.setTitle(title).setXAxisLabel("days").setYAxisLabel("value");
        plot.show();
    }

    public void showCaseMore(List<List<Double>> pathes, String title) throws Exception {
        ArrayList<Named<DoubleUnaryOperator>> operators = new ArrayList<>();
        pathes.forEach(path->operators.add(new Named<>("average", day -> path.get((int) day))));
        Plot plot = new Plot2D((double) 0,(double) pathes.get(0).size()-1,pathes.get(0).size(),operators);
        plot.setTitle(title).setXAxisLabel("days").setYAxisLabel("value");
        plot.show();
    }


}

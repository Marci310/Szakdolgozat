package Product;

import Assets.Asset;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.process.EulerSchemeFromProcessModel;
import net.finmath.time.TimeDiscretizationFromArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Derivative {


    private Asset asset;
    private double volatility;
    private final int workdays;
    private TimeDiscretizationFromArray td;
    private final int NUMOFPATHS = 100;
    private EulerSchemeFromProcessModel process;
    private BrownianMotion brownianMotion;
    private final List<Double> randomPath;


    public Derivative(Asset asset, int workDays,double volatility) {
        this.asset=asset;
        this.workdays = workDays;
        this.volatility=volatility;
        randomPath = new ArrayList<>();
        generateProcess();
        generatePath();
    }


    private void generateProcess() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) + 1000;
        System.out.println(randomNumber);
        var model = new BlackScholesModel(asset.getPrice(), asset.getDailyRiskFreeRate(workdays), volatility);
        td = new TimeDiscretizationFromArray(0.0, workdays, 1);
        brownianMotion = new BrownianMotionFromMersenneRandomNumbers(td, 1, NUMOFPATHS, 5143);
        process = new EulerSchemeFromProcessModel(model, brownianMotion);
    }

    private void generatePath() {
        for (int i = 0; i <= workdays; i++) {
            double value = 0;
            for (int j = 0; j < NUMOFPATHS; j++)
                value+=process.getProcessValue(i, 0).get(j);
            randomPath.add(value/NUMOFPATHS);
        }
    }

    public EulerSchemeFromProcessModel getProcess() {
        return process;
    }


    public List<Double> getRandomPath() {
        return randomPath;
    }


    // Getters and setters for the private variables

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

}

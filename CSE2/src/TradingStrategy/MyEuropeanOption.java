package TradingStrategy;

import java.util.HashMap;
import java.util.Map;

import net.finmath.exception.CalculationException;
import net.finmath.modelling.Model;
import net.finmath.montecarlo.assetderivativevaluation.AssetModelMonteCarloSimulationModel;
import net.finmath.montecarlo.assetderivativevaluation.products.AbstractAssetMonteCarloProduct;
import net.finmath.stochastic.RandomVariable;


public class MyEuropeanOption extends AbstractAssetMonteCarloProduct {

    private final double maturity;
    private final double strike;
    private final Integer underlyingIndex;
    private final String nameOfUnderlying;


    public MyEuropeanOption(String underlyingName, double maturity, double strike) {
        this.nameOfUnderlying = underlyingName;
        this.maturity = maturity;
        this.strike = strike;
        this.underlyingIndex = 0;
    }

    public MyEuropeanOption(double maturity, double strike, int underlyingIndex) {
        this.maturity = maturity;
        this.strike = strike;
        this.underlyingIndex = underlyingIndex;
        this.nameOfUnderlying = null;
    }

    public MyEuropeanOption(double maturity, double strike) {
        this(maturity, strike, 0);
    }


    public RandomVariable getCallOrPutValue(double evaluationTime, AssetModelMonteCarloSimulationModel model, boolean call) throws CalculationException {
        RandomVariable underlyingAtMaturity = model.getAssetValue(this.maturity, this.underlyingIndex);
        RandomVariable values = call ? underlyingAtMaturity.sub(this.strike).floor(0.0) : underlyingAtMaturity.bus(this.strike).floor(0.0);
        RandomVariable numeraireAtMaturity = model.getNumeraire(this.maturity);
        RandomVariable monteCarloWeights = model.getMonteCarloWeights(this.maturity);
        values = values.div(numeraireAtMaturity).mult(monteCarloWeights);
        RandomVariable numeraireAtEvalTime = model.getNumeraire(evaluationTime);
        RandomVariable monteCarloWeightsAtEvalTime = model.getMonteCarloWeights(evaluationTime);
        values = values.mult(numeraireAtEvalTime).div(monteCarloWeightsAtEvalTime);

        return values;

    }

    public Map<String, Object> getValues(double evaluationTime, Model model) {
        Map<String, Object> result = new HashMap<>();
        try {
            double value = this.getValue(evaluationTime, (AssetModelMonteCarloSimulationModel) model).getAverage();
            result.put("value", value);
        } catch (CalculationException var7) {
            result.put("exception", var7);
        }

        return result;

    }

    public String toString() {
        return "EuropeanOption [maturity=" + this.maturity + ", strike=" + this.strike + ", underlyingIndex=" + this.underlyingIndex
                + ", nameOfUnderliyng=" + this.nameOfUnderlying + "]";
    }

    public double getMaturity() {
        return this.maturity;
    }

    public double getStrike() {
        return this.strike;
    }

    public Integer getUnderlyingIndex() {
        return this.underlyingIndex;
    }

    public String getNameOfUnderlying() {
        return this.nameOfUnderlying;
    }

    @Override
    public RandomVariable getValue(double v, AssetModelMonteCarloSimulationModel assetModelMonteCarloSimulationModel) throws CalculationException {
        return getCallOrPutValue(v, assetModelMonteCarloSimulationModel, true);
    }
}

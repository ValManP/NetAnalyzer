package core.ga.operators.factories.alterer.types;

/**
 * Created by Valerii Pozdiaev on 2017.
 */
public enum CrossoverTypes implements IAltererType {
    SINGLE_POINT_CROSSOVER,
    MULTI_POINT_CROSSOVER,
    PARTIALLY_MATCHED_CROSSOVER,
    UNIFORM_CROSSOVER;

    private double probability;

    CrossoverTypes() {
        this.probability = IAltererType.probability;
    }

    @Override
    public double getProbability() {
        return this.probability;
    }

    @Override
    public void setProbability(double probability) {
        this.probability = probability;
    }
}

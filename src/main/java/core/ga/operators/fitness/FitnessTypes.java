package core.ga.operators.fitness;

import core.ga.operators.factories.alterer.types.SelectorTypes;

/**
 * Created by Valerii Pozdiaev on 2017.
 */
public enum FitnessTypes {
    CONSTANT_WEIGHT_FITNESS,
    RANDOM_WEIGHT_FITNESS;

    public final static double FITNESS_VARIABLE = 0.5;
    private double fitnessVariable;

    FitnessTypes() {
        this.fitnessVariable = FITNESS_VARIABLE;
    }

    public FitnessTypes withFitnessVariable(double fitnessVariable) {
        this.fitnessVariable = fitnessVariable;
        return this;
    }

    public double getFitnessVariable() {
        return fitnessVariable;
    }
}

package core.ga.operators.fitness.factory;

import core.ga.operators.fitness.FitnessTypes;
import core.ga.operators.fitness.GAFitness;
import core.ga.operators.fitness.impl.ConstantWeightFitness;
import core.ga.operators.fitness.impl.RandomWeightFitness;
import core.model.network.NetworkDescription;
import core.model.strategies.ICapacityCalculationStrategy;

public class FitnessFactory {
    public GAFitness getFitness(FitnessTypes fitnessType, ICapacityCalculationStrategy capacityCalculationStrategy, NetworkDescription networkDescription) {
        GAFitness result = null;
        double fitnessVariable = fitnessType.getFitnessVariable();

        if (fitnessType == FitnessTypes.CONSTANT_WEIGHT_FITNESS) {
            result = new ConstantWeightFitness(capacityCalculationStrategy, networkDescription, fitnessVariable);
        } else if (fitnessType == FitnessTypes.RANDOM_WEIGHT_FITNESS) {
            result = new RandomWeightFitness(capacityCalculationStrategy, networkDescription);
        }

        return result;
    }
}

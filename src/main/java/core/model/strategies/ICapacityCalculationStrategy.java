package core.model.strategies;

import core.model.network.AbstractNetwork;

public interface ICapacityCalculationStrategy {
    double calculate(AbstractNetwork network);
}

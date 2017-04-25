package core.model.strategies;

import core.model.controllers.NetworkController;
import core.model.inventory.NetworkElement;
import core.model.network.AbstractNetwork;
import core.model.network.impl.DoubleNetwork;

public class MinGuaranteedCapacityStrategy implements ICapacityCalculationStrategy {
    @Override
    public double calculate(AbstractNetwork abstractNetwork) {
        double networkCapacity = Double.MAX_VALUE;
        double currentCapacity;

        if (abstractNetwork instanceof DoubleNetwork) {
            DoubleNetwork network = (DoubleNetwork) abstractNetwork;

            for (NetworkElement user : network.getUsers()) {
                currentCapacity = NetworkController.calculateNetworkCapacityForUser(network, user);
                if (currentCapacity < networkCapacity) {
                    networkCapacity = currentCapacity;
                }
            }
        }

        return (networkCapacity == Double.MAX_VALUE) ? 0.0 : networkCapacity;
    }
}

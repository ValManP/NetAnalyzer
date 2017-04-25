package core.model.strategies;

import core.model.controllers.NetworkController;
import core.model.inventory.NetworkElement;
import core.model.network.AbstractNetwork;
import core.model.network.impl.DoubleNetwork;
import core.utils.CommonTool;

import java.util.List;

public class MostDistantClientCapacityStrategy implements ICapacityCalculationStrategy {
    @Override
    public double calculate(AbstractNetwork abstractNetwork) {
        double networkCapacity = 0.0;

        if (abstractNetwork instanceof DoubleNetwork) {
            DoubleNetwork network = (DoubleNetwork) abstractNetwork;

            List<NetworkElement> users = network.getUsers();
            NetworkElement minInCapacityUser = null;
            double minIncomingCapacity = Double.MAX_VALUE, currentIncomingCapacity;
            for (NetworkElement user : users) {
                currentIncomingCapacity = getSummaryIncomingCapacity(network, user);
                if (currentIncomingCapacity < minIncomingCapacity) {
                    minIncomingCapacity = currentIncomingCapacity;
                    minInCapacityUser = user;
                }
            }

            networkCapacity = NetworkController.calculateNetworkCapacityForUser(network, minInCapacityUser);
        }

        return networkCapacity;
    }

    private double getSummaryIncomingCapacity(DoubleNetwork network, NetworkElement user) {
        double capacity = 0.0;
        int position = network.getNEPosition(user);

        for (int i = 0; i < network.getSize(); i++) {
            if (position != i && CommonTool.isExist(network.getLinksMatrix()[i][position])) {
                capacity += network.getLinksMatrix()[i][position].getCapacity();
            }
        }

        return capacity;
    }
}

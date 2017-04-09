package core.model.controllers;

import core.model.inventory.NetworkElement;
import core.model.inventory.impl.networkelement.EmptyElement;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import lpsolve.LpSolve;

import java.util.List;
import java.util.Random;

public class NetworkController {
    public static double calculateNetworkCost(DoubleNetwork network) {
        double cost = 0;

        for (NetworkElement hub : network.getSwitches()) {
            cost += ((SwitchNetworkElement) hub).getPrice();
        }

        return cost;
    }

    public static double calculateNetworkCapacityByMinValue(DoubleNetwork network) {
        double networkCapacity = Double.MAX_VALUE;
        double currentCapacity;

        for (NetworkElement user : network.getUsers()) {
            currentCapacity = calculateNetworkCapacityForUser(network, user);
            if (currentCapacity < networkCapacity) {
                networkCapacity = currentCapacity;
            }
        }

        return (networkCapacity == Double.MAX_VALUE) ? 0.0 : networkCapacity;
    }

    private static double calculateNetworkCapacityForUser(DoubleNetwork network, NetworkElement user) {
        int size = network.getNetworkElementsCount();
        double[][] networkMatrix = convertNetworkToArray(network, size);
        int userPosition = network.getNEPosition(user);

        LpSolve solver = LpSolveController.getLpSolver(size * size);
        try {
            prerequisitesForCalculation(network, networkMatrix, solver, size);

            LpSolveController.setLowerConstraint(networkMatrix, solver, userPosition, size);
            LpSolveController.setObjectiveFunction(networkMatrix, solver, userPosition, size);
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0.0;
        }
        // TODO Check correctness
        // LpSolveController.setUniqueLowerConstraint(networkMatrix, solver, userPosition, size);

        return LpSolveController.solve(solver);
    }

    private static void prerequisitesForCalculation(DoubleNetwork network, double[][] networkMatrix, LpSolve solver, int size) {
        for (NetworkElement root : network.getRoots()) {
            LpSolveController.setUpperConstraint(networkMatrix, solver, network.getNEPosition(root), size);
        }
        for (NetworkElement hub : network.getSwitches()) {
            int hubPosition = network.getNEPosition(hub);
            LpSolveController.setUpperConstraint(networkMatrix, solver, hubPosition, size);
            LpSolveController.setEqualConstraint(networkMatrix, solver, hubPosition, size);
        }
    }

    private static double[][] convertNetworkToArray(DoubleNetwork network, int neSize) {
        List<NetworkElement> networkElements = network.getNetworkElements();
        double[][] networkArray = new double[neSize][neSize];

        for (int row = 0; row < neSize; row++) {
            for (int col = 0; col < neSize; col++) {
                if (row == col) {
                    networkArray[row][col] = networkElements.get(row).getCapacity();
                } else {
                    if (network.isLinkExist(row, col)) {
                        networkArray[row][col] = 1.0;
                    } else {
                        networkArray[row][col] = 0.0;
                    }
                }
            }
        }

        return networkArray;
    }

    public static void generateNetwork(DoubleNetwork network, int layerSize, int layersCount, double minCapacity, double maxCapacity) {
        network.resize(layerSize * layersCount);
        Random random = new Random();
        double randomValue = 0;


        for (int i = 0; i < layerSize; i++) {
            randomValue = (maxCapacity - minCapacity) * random.nextDouble() + minCapacity;
            network.addRoot(new RootNetworkElement(randomValue), i);
        }

        for (int row = 1; row < layersCount - 1; row++) {
            for (int col = 0; col < layerSize; col++) {
                network.addEmptyElement(new EmptyElement(), row * layerSize + col);
                for (int i = 0; i < layerSize; i++) {
                    network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0),
                            (row - 1) * layerSize + i,
                            row * layerSize + col);
                }
            }
        }


        for (int col = 0; col < layerSize; col++) {
            network.addUser(new UserNetworkElement(50, 30), (layersCount - 1) * layerSize + col);
            for (int i = 0; i < layerSize; i++) {
                network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0),
                        (layersCount - 2) * layerSize + i,
                        (layersCount - 1) * layerSize + col);
            }
        }
    }
}

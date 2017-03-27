package experiments;

import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.CrossoverTypes;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.inventory.impl.storage.Device;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.AbstractNetwork;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import experiments.ExperimentExecutor;
import experiments.ExperimentParameters;
import experiments.impl.CrossoverExperiment;
import experiments.impl.MutatorExperiment;
import experiments.impl.PopulationExperiment;
import experiments.impl.SelectorExperiment;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Поздяев on 22.03.2017.
 */
public class ExperimentTest {
    private int storageSize = 20;
    private int networkSize = 21;
    private double minCapacity = 60, maxCapacity = 140, minPrice = 50, maxPrice = 120;
    private AbstractNetwork network;
    private AbstractStorage storage;

    @Before
    public void setUp() throws Exception {
        network = createNetwork(networkSize);
        storage = createStorage(storageSize, minCapacity, maxCapacity, minPrice, maxPrice);
    }

    @Test
    public void canExecuteCrossoverExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new CrossoverExperiment(
                new ExperimentParameters(network, storage, 0.1, 5,
                        null, MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(2.0), 5, 5),
                "crossover.txt"));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteMutatorExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new MutatorExperiment(
                new ExperimentParameters(network, storage, 0.1, 5,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), null,
                        SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(2.0), 5, 5),
                "mutator.txt"));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecutePopulationExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new PopulationExperiment(
                new ExperimentParameters(network, storage, 5, 5,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(2.0), 5, 3),
                "population.txt"));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteSelectorExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new SelectorExperiment(
                new ExperimentParameters(network, storage, 0.1, 5,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2),  MutatorTypes.MUTATOR.withProbability(0.01),
                        null, 5, 5),
                "selector.txt"));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    private AbstractStorage createStorage(int storageSize, double minCapacity, double maxCapacity,
                                          double minPrice, double maxPrice) {
        AbstractStorage storage = new DoubleStorage();
        Random random = new Random();

        for (int i = 0; i < storageSize; ++i) {
            storage.addElement(new Device("device#" + i, (maxCapacity - minCapacity) * random.nextDouble() + minCapacity,
                    (maxPrice - minPrice) * random.nextDouble() + minPrice));
        }

        return storage;
    }

    private DoubleNetwork createNetwork(int size) {
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(130.0), 0);
        network.addRoot(new RootNetworkElement(120.0), 1);
        network.addRoot(new RootNetworkElement(150.0), 2);

        network.addUser(new UserNetworkElement(40.0, 60.0), 3);
        network.addUser(new UserNetworkElement(30.0, 50.0), 4);
        network.addUser(new UserNetworkElement(40.0, 60.0), 5);
        network.addUser(new UserNetworkElement(30.0, 50.0), 6);
        network.addUser(new UserNetworkElement(40.0, 60.0), 7);
        network.addUser(new UserNetworkElement(30.0, 50.0), 8);
        network.addUser(new UserNetworkElement(40.0, 60.0), 9);
        network.addUser(new UserNetworkElement(30.0, 50.0), 10);
        network.addUser(new UserNetworkElement(30.0, 50.0), 11);


        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 12);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 14);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 13);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 20);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 12, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 16);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 15);
        network.addLink(new DoubleLink(true, 100.0, 20.0, 0.0), 13, 14);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 15, 5);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 15, 6);
        network.addLink(new DoubleLink(true, 100.0, 20.0, 0.0), 15, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 16, 7);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 16, 8);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 20, 16);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 17, 19);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 14, 18);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 9);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 10);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 19, 10);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 19, 11);

        return network;
    }
}

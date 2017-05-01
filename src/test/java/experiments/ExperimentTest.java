package experiments;

import core.ga.operators.factories.alterer.types.CrossoverTypes;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.model.controllers.NetworkController;
import core.model.controllers.StorageController;
import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.inventory.impl.storage.Device;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.AbstractNetwork;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import experiments.impl.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

@Ignore
public class ExperimentTest {
    private int storageSize = 60;
    private double minCapacity = 60, maxCapacity = 140, minPrice = 50, maxPrice = 120;
    private AbstractNetwork network;
    private AbstractStorage storage;

    @Before
    public void setUp() throws Exception {
        network = new DoubleNetwork();
        NetworkController.generateNetwork((DoubleNetwork) network, 7, 7, 60, 300);
        storage = new DoubleStorage();
        StorageController.getInstance().generateStorage((DoubleStorage) storage, storageSize, minCapacity, maxCapacity, minPrice, maxPrice);
    }

    @Test
    public void canExecuteCrossoverExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new CrossoverExperiment(
                new ExperimentParameters(network, storage, 0.1, 10,
                        null, MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(2.0), 5, 4),
                generateFileName("crossover")));

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
                new ExperimentParameters(network, storage, 0.1, 10,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), null,
                        SelectorTypes.TOURNAMENT_SELECTOR, 5, 4),
                generateFileName("mutator")));

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
                new ExperimentParameters(network, storage, 5, 10,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR, 5, 4),
                generateFileName("population")));

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
                new ExperimentParameters(network, storage, 0.1, 10,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2),  MutatorTypes.MUTATOR.withProbability(0.01),
                        null, 5, 4),
                generateFileName("selector")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteConvergenceExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new ConvergenceExperiment(
                new ExperimentParameters(network, storage, 1, 50,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR, 10, 4),
                generateFileName("convergence")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteConvergenceWithDiffCrossoverExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new ConvergenceWithDiffCrossoverExperiment(
                new ExperimentParameters(network, storage, 0.1, 10,
                        null, MutatorTypes.MUTATOR.withProbability(0.01),
                        SelectorTypes.TOURNAMENT_SELECTOR, 5, 3),
                generateFileName("convergenceCrossover")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteConvergenceWithDiffMutatorExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new ConvergenceWithDiffMutatorExperiment(
                new ExperimentParameters(network, storage, 0.1, 10,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), null,
                        SelectorTypes.TOURNAMENT_SELECTOR, 5, 3),
                generateFileName("convergenceMutator")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Test
    public void canExecuteConvergenceWithTournamentSelectorExperiment() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        executor.addExperiment(new ConvergenceWithTournamentSelectorExperiment(
                new ExperimentParameters(network, storage, 1, 10,
                        CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.2), MutatorTypes.MUTATOR.withProbability(0.01),
                        null, 5, 3),
                generateFileName("convergenceTournament")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    @Ignore
    @Test
    public void canExecuteExperimentsWithLargeNetwork() {
        // Arrange
        ExperimentExecutor executor = new ExperimentExecutor();
        DoubleNetwork largeNetwork = new DoubleNetwork();
        NetworkController.generateNetwork(largeNetwork, 10, 10, 150, 300);
        DoubleStorage largeStorage = new DoubleStorage();
        StorageController.getInstance().generateStorage(largeStorage, 60, 100, 250, 100, 300);

        executor.addExperiment(new CrossoverExperiment(
                new ExperimentParameters(largeNetwork, largeStorage, 0.1, 1,
                        null, MutatorTypes.MUTATOR.withProbability(0.05),
                        SelectorTypes.TOURNAMENT_SELECTOR, 5, 3),
                generateFileName("largeCrossover")));

        // Act
        executor.process();

        // Assert
        assertNotNull(executor);
    }

    private static AbstractStorage createStorage(int storageSize, double minCapacity, double maxCapacity,
                                                 double minPrice, double maxPrice) {
        AbstractStorage storage = new DoubleStorage();
        Random random = new Random();

        for (int i = 0; i < storageSize; ++i) {
            storage.addElement(new Device("device#" + i, (maxCapacity - minCapacity) * random.nextDouble() + minCapacity,
                    (maxPrice - minPrice) * random.nextDouble() + minPrice));
        }

        return storage;
    }

    private static DoubleNetwork createNetwork(int size) {
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

    private static String generateFileName(String experiment) {
        return experiment.concat("_").concat(LocalDate.now().toString()).concat(".txt");
    }
}

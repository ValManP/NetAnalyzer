package core.ga;

import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.CrossoverTypes;
import core.ga.operators.factories.alterer.types.IAltererType;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.ga.operators.fitness.FitnessTypes;
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
import core.model.strategies.MostDistantClientCapacityStrategy;
import org.jenetics.MonteCarloSelector;
import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionResult;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EvolutionTest {
    private int networkSize = 6;
    private int initialPopulation = 50;
    private AbstractNetwork network;
    private AbstractStorage storage;
    private Evolution evolution;

    @Test
    public void canCreateEvolution() {
        // Arrange
        AbstractNetwork network = createNetwork(networkSize);
        AbstractStorage storage = createStorage();

        // Act
        Evolution evolution = new Evolution(network, storage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.75));

        // Assert
        assertNotNull(evolution);
    }

    @Test
    public void canCompileBuilder() {
        // Arrange
        AbstractNetwork network = createNetwork(networkSize);
        AbstractStorage storage = createStorage();
        Evolution evolution = new Evolution(network, storage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.75));

        // Act
        evolution.builder();

        // Assert
        assertNotNull(evolution.getBuilder());
    }

    @Test
    public void canSetInitialPopulation() {
        // Arrange
        AbstractNetwork network = createNetwork(networkSize);
        AbstractStorage storage = createStorage();
        Evolution evolution = (new Evolution(network, storage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.75))).builder();
        int population = 50;

        // Act
        evolution.initialPopulation(population);

        // Assert
        assertEquals(population, evolution.getBuilder().getPopulationSize());
    }

    @Test
    public void canAddAlters() {
        // Arrange
        AbstractNetwork network = createNetwork(networkSize);
        AbstractStorage storage = createStorage();
        Evolution evolution = (new Evolution(network, storage, FitnessTypes.RANDOM_WEIGHT_FITNESS.withFitnessVariable(0.75))).builder();

        // Act
        IAltererType crossover = CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.4);
        IAltererType mutator = MutatorTypes.MUTATOR.withProbability(0.1);
        evolution.alterer(crossover, mutator);

        // Assert
        assertNotNull(evolution.getBuilder().getAlterers());
    }

    @Test
    public void canAddSelector() {
        // Arrange
        AbstractNetwork network = createNetwork(networkSize);
        AbstractStorage storage = createStorage();
        Evolution evolution = (new Evolution(network, storage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.75))).builder();

        // Act
        SelectorTypes selector = SelectorTypes.MONTE_CARLO_SELECTOR;
        evolution.selector(selector);

        // Assert
        assertTrue(evolution.getBuilder().getOffspringSelector() instanceof MonteCarloSelector);
        assertNotNull(evolution.getBuilder().getSurvivorsSelector() instanceof MonteCarloSelector);
    }

    @Before
    public void setUp() throws Exception {
        network = createNetwork(networkSize);
        storage = createStorage();
        evolution = (new Evolution(network, storage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.75))).builder()
                .alterer(CrossoverTypes.UNIFORM_CROSSOVER.withProbability(0.2), MutatorTypes.MUTATOR.withProbability(0.1))
                .selector(SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(2.0))
                .initialPopulation(initialPopulation)
                .buildEngine();
    }

    @Test
    public void canIterateEvolution() {
        // Arrange
        int generations = 2;

        // Act
        EvolutionResult result = evolution.iterate(generations);

        // Assert
        assertEquals(generations, result.getGeneration());
    }

    @Test
    public void canEvolveToBestResult() {
        // Arrange
        int generations = 2;

        // Act
        EvolutionResult result = evolution.evolve(generations);

        // Assert
        assertTrue(generations >= result.getGeneration());
    }

    @Test
    public void canEvolveToBestPhenotype() {
        // Arrange
        int generations = 2;

        // Act
        Phenotype result = evolution.evolveToBestPhenotype(generations);

        // Assert
        assertTrue(generations >= result.getGeneration());
    }

    @Test
    public void canEvolveBySteadyFitness() {
        // Arrange
        int generations = 2;
        int threads = 10;

        // Act
        EvolutionResult result = evolution
                .executors(threads)
                .evolveBySteadyFitness(generations);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void canEvolveWithLargeNet() {
        // Arrange
        int generations = 5;

        DoubleNetwork largeNetwork = new DoubleNetwork();
        NetworkController.generateNetwork(largeNetwork, 3, 3, 150, 300);
        DoubleStorage largeStorage = new DoubleStorage();
        StorageController.getInstance().generateStorage(largeStorage, 4, 100, 250, 100, 300);

        Evolution evolution = (new Evolution(largeNetwork, largeStorage, FitnessTypes.CONSTANT_WEIGHT_FITNESS.withFitnessVariable(0.5)))
                .capacityCalculationStrategy(new MostDistantClientCapacityStrategy())
                .builder()
                .alterer(CrossoverTypes.SINGLE_POINT_CROSSOVER.withProbability(0.5), MutatorTypes.MUTATOR.withProbability(0.05))
                .selector(SelectorTypes.TOURNAMENT_SELECTOR)
                .initialPopulation(10)
                .buildEngine();

        // Act
        EvolutionResult result = evolution
                .evolve(generations);

        // Assert
        try {
            FileWriter fileWriter = new FileWriter("test.txt", true);
            fileWriter.write(result.getBestFitness() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.print(e);
        }

        assertNotNull(result);
    }

    private static AbstractStorage createStorage() {
        AbstractStorage storage = new DoubleStorage();

        storage.addElement(new Device("device#1", 10.0, 10));
        storage.addElement(new Device("device#2", 30.0, 15.0));
        storage.addElement(new Device("device#3", 20.0, 10.0));
        storage.addElement(new Device("device#4", 70.0, 50.0));
        storage.addElement(new Device("device#5", 90.0, 60.0));
        storage.addElement(new Device("device#6", 60.0, 50.0));

        return storage;
    }

    private static DoubleNetwork createNetwork(int size) {
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(100.0), 0);
        //network.addSwitch(new SwitchNetworkElement<>(40.0, 10.0), 1);
        //network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 2);
        //network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 3);
        network.addUser(new UserNetworkElement(40.0, 60.0), 4);
        network.addUser(new UserNetworkElement(30.0, 50.0), 5);

        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 1);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 2);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 3, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 3, 5);

        return network;
    }
}

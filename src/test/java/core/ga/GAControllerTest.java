/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga;

import core.controllers.GAController;
import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.configuration.suppliers.NetworkSupplier;
import core.ga.operators.configuration.validators.NetworkAlleleValidator;
import core.ga.operators.configuration.validators.NetworkValidator;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.NetworkDescription;
import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import org.jenetics.Alterer;
import org.jenetics.AnyGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GAControllerTest {
    private int networkSize = 6;
    private int populationSize = 50;
    private int generations = 100;
    private int minDeviceCount = 2;
    private DoubleNetwork network;
    private AbstractStorage storage;
    private NetworkDescription networkDescription;
    private NetworkSupplier networkSupplier;
    private NetworkValidator networkValidator;
    private NetworkAlleleValidator networkAlleleValidator;
    private List<Alterer> alteres;

    @Before
    public void setup() {
        network = createNetwork(networkSize);
        storage = createStorage();
        networkDescription = new NetworkDescription(network, storage);
        networkDescription.setMinDeviceCount(minDeviceCount);
        networkSupplier = new NetworkSupplier(networkDescription);
        networkValidator = new NetworkValidator(networkDescription);
        networkAlleleValidator = new NetworkAlleleValidator();
        alteres = new ArrayList<>();
    }

    @Test
    public void canCreateEngine() {
        // Act
        Engine engine = GAController.createEngine(networkDescription, networkSupplier, networkValidator, networkAlleleValidator, alteres, populationSize);

        // Assert
        assertNotNull(engine);
    }

    @Test
    public void canExecuteEngine() {
        // Arrange
        Engine engine = GAController.createEngine(networkDescription, networkSupplier, networkValidator, networkAlleleValidator, alteres, populationSize);
        int bySteadyFitness = 5;

        // Act
        Phenotype<AnyGene<NetworkAllele>, Double> phenotype = GAController.execute(engine, generations, bySteadyFitness);

        System.out.println("Phenotype = " + phenotype.getGeneration() + " fitness = " + phenotype.getFitness());
        // Assert
        assertNotNull(phenotype);
    }

    @Test
    public void canExecuteOneEvolutionStep() {
        // Arrange
        Engine engine = GAController.createEngine(networkDescription, networkSupplier, networkValidator, networkAlleleValidator, alteres, populationSize);
        int gen = 1;

        // Act
        EvolutionResult<AnyGene<NetworkAllele>, Double> result = GAController.evolve(engine, gen);

        // Assert
        assertEquals(gen, result.getGeneration());
    }

    @Test
    public void canIterateEvolution() {
        // Arrange
        Engine engine = GAController.createEngine(networkDescription, networkSupplier, networkValidator, networkAlleleValidator, alteres, populationSize);
        int gen = 2;

        // Act
        EvolutionResult<AnyGene<NetworkAllele>, Double> result = GAController.iterate(engine, gen);

        // Assert
        assertEquals(gen, result.getGeneration());
    }

    public static AbstractStorage createStorage() {
        AbstractStorage storage = new DoubleStorage();

        storage.addElement(new SwitchNetworkElement<>(10.0, 10));
        storage.addElement(new SwitchNetworkElement<>(30.0, 15.0));
        storage.addElement(new SwitchNetworkElement<>(20.0, 10.0));
        storage.addElement(new SwitchNetworkElement<>(70.0, 50.0));
        storage.addElement(new SwitchNetworkElement<>(90.0, 60.0));
        storage.addElement(new SwitchNetworkElement<>(60.0, 50.0));

        return storage;
    }

    public static DoubleNetwork createNetwork(int size) {
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement<>(100.0), 0);
        //network.addSwitch(new SwitchNetworkElement<>(40.0, 10.0), 1);
        //network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 2);
        //network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 3);
        network.addUser(new UserNetworkElement<>(40.0, 60.0), 4);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 5);

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

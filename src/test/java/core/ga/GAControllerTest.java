/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga;

import core.algorithm.controllers.GAController;
import core.algorithm.controllers.NetworkController;
import core.algorithm.ga.operators.suppliers.NetworkSupplier;
import core.algorithm.ga.operators.validators.NetworkAlleleValidator;
import core.algorithm.ga.operators.validators.NetworkValidator;
import core.algorithm.inventory.NetworkDescription;
import core.algorithm.inventory.Storage;
import core.algorithm.inventory.networkelement.RootNetworkElement;
import core.algorithm.inventory.networkelement.SwitchNetworkElement;
import core.algorithm.inventory.networkelement.UserNetworkElement;
import core.algorithm.model.DoubleLink;
import core.algorithm.model.DoubleNetwork;
import org.jenetics.engine.Engine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GAControllerTest {
    @Test
    public void canCreateEngine() {
        // Arrange
        int size = 6;
        DoubleNetwork network = createNetwork(size);
        Storage storage = createStorage();
        NetworkDescription networkDescription = new NetworkDescription(network, storage);
        NetworkSupplier networkSupplier = new NetworkSupplier(networkDescription);
        NetworkValidator networkValidator = new NetworkValidator(networkDescription);
        NetworkAlleleValidator networkAlleleValidator = new NetworkAlleleValidator();
        int populationSize = 50;

        // Act
        Engine engine = GAController.createEngine(networkDescription, networkSupplier, networkValidator, networkAlleleValidator, populationSize);

        // Assert
        assertNotNull(engine);
    }



    private Storage createStorage() {
        Storage storage = new Storage();

        storage.addElement(new SwitchNetworkElement<>(10.0, 10));
        storage.addElement(new SwitchNetworkElement<>(30.0, 15.0));
        storage.addElement(new SwitchNetworkElement<>(20.0, 10.0));
        storage.addElement(new SwitchNetworkElement<>(70.0, 50.0));
        storage.addElement(new SwitchNetworkElement<>(90.0, 60.0));
        storage.addElement(new SwitchNetworkElement<>(60.0, 50.0));

        return storage;
    }

    private DoubleNetwork createNetwork(int size) {
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement<>(100.0), 0);
        network.addSwitch(new SwitchNetworkElement<>(40.0, 10.0), 1);
        network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 2);
        network.addSwitch(new SwitchNetworkElement<>(30.0, 10.0), 3);
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

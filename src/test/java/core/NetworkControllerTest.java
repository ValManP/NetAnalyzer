/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core;

import core.model.controllers.NetworkController;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NetworkControllerTest {
    private int size = 6;
    private DoubleNetwork network;

    @Before
    public void setup() {
        network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(new SwitchNetworkElement("device#1", 40.0, 10.0), 1);
        network.addSwitch(new SwitchNetworkElement("device#2", 30.0, 10.0), 2);
        network.addSwitch(new SwitchNetworkElement("device#3", 30.0, 10.0), 3);
        network.addUser(new UserNetworkElement(40.0, 60.0), 4);
        network.addUser(new UserNetworkElement(30.0, 50.0), 5);

        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 1);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 2);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 3, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 3, 5);
    }

    @Test
    public void canCalculateCapacity() {
        // Act
        double expectedValue = 60.0;
        double actualValue = NetworkController.calculateNetworkCapacityByMinValue(network);

        // Assert
        assertEquals(expectedValue, actualValue, 0.01);
    }

    @Test
    public void canCalculateCost() {
        // Act
        double expectedValue = 30.0;
        double actualValue = NetworkController.calculateNetworkCost(network);

        // Assert
        assertEquals(expectedValue, actualValue, 0.01);
    }
}

package core;

import core.model.controllers.StorageController;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NetworkTest {
    @Test
    public void canCreate() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);

        // Act&Assert
        assertEquals(size, network.getSize());
    }

    @Test
    public void canAddNetworkElement() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);

        // Act
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(new SwitchNetworkElement("device#1", 100.0, 10.0), 2);
        network.addUser(new UserNetworkElement(100.0, 20.0), 4);

        // Assert
        int expectedCount = 6;
        assertEquals(expectedCount, network.getNetworkElementsCount());
    }

    @Test
    public void canFindNetworkElementPosition() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);

        // Act
        SwitchNetworkElement hub = new SwitchNetworkElement("device#1", 100.0, 10.0);
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(hub, 1);
        network.addUser(new UserNetworkElement(100.0, 20.0), 4);

        // Assert
        int expectedPosition = 1;
        assertEquals(expectedPosition, network.getNEPosition(hub));
    }

    @Test
    public void canRemoveNetworkElement() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);
        SwitchNetworkElement hub = new SwitchNetworkElement("device#1", 100.0, 10.0);
        RootNetworkElement root = new RootNetworkElement(100.0);
        UserNetworkElement user = new UserNetworkElement(100.0, 20.0);
        network.addRoot(root, 0);
        network.addSwitch(hub, 2);
        network.addUser(user, 4);

        // Act
        network.removeRoot(root);
        network.removeSwitch(hub);
        network.removeUser(user);

        // Assert
        int expectedPosition = -1;
        assertEquals(expectedPosition, network.getNEPosition(root));
        assertEquals(expectedPosition, network.getNEPosition(hub));
        assertEquals(expectedPosition, network.getNEPosition(user));
    }

    @Test
    public void canGetGroupsOfNetworkElement() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(new SwitchNetworkElement("device#1", 100.0, 10.0), 2);
        network.addUser(new UserNetworkElement(100.0, 20.0), 4);

        // Act&Assert
        int expectedSize = 1;
        assertEquals(expectedSize, network.getRoots().size());
        assertEquals(expectedSize, network.getSwitches().size());
        assertEquals(expectedSize, network.getUsers().size());
    }

    @Test
    public void canAddLink() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(new SwitchNetworkElement("device#1", 100.0, 10.0), 1);

        // Act
        network.addLink(new DoubleLink(true, 100.0, 20.0, 32.0), 0, 1);

        // Assert
        assertTrue(network.isLinkExist(0, 1));
    }

    @Test
    public void canShowTraffic() {
        // Arrange
        int size = 6;
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement(100.0), 0);
        network.addSwitch(new SwitchNetworkElement("device#1", 100.0, 10.0), 1);
        network.addLink(new DoubleLink(true, 100.0, 20.0, 32.0), 0, 1);

        // Act
        double expectedValue = 45.0;
        network.setTraffic(0, 1, expectedValue);

        // Assert
        assertEquals(expectedValue, network.getTraffic(0, 1), 0.01);
    }

    @Test
    public void canGenerateStorage() {
        // Arrange
        int size = 10;
        double minCapacity = 10.0;
        double maxCapacity = 50.0;
        double minPrice = 10.0;
        double maxPrice = 50.0;

        // Act
        DoubleStorage storage = new DoubleStorage();
        StorageController.getInstance()
                .generateStorage(storage, size, minCapacity, maxCapacity, minPrice, maxPrice);

        // Assert
        assertEquals(size, storage.getSize());
    }
}

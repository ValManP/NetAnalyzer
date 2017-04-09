package core.model.network.impl;

import core.model.inventory.NetworkElement;
import core.model.inventory.impl.networkelement.EmptyElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.storage.Device;
import core.model.network.AbstractLink;
import core.model.network.AbstractNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoubleNetwork extends AbstractNetwork<Double, NetworkElement> {
    public DoubleNetwork() {
        this.size = 0;
        networkElements = new ArrayList<>();
        roots = new ArrayList<>();
        switches = new ArrayList<>();
        users = new ArrayList<>();
    }
    
    public DoubleNetwork(int size) {
        this.size = size;
        linksMatrix = new DoubleLink[size][size];
        networkElements = Stream.generate(EmptyElement::new).limit(size).collect(Collectors.toList());
        roots = new ArrayList<>();
        switches = new ArrayList<>();
        users = new ArrayList<>();
    }

    public DoubleNetwork(DoubleNetwork anotherNetwork) {
        this.size = anotherNetwork.getSize();
        linksMatrix = Arrays.copyOf(anotherNetwork.getLinksMatrix(), this.size);
        networkElements = new ArrayList<>(anotherNetwork.getNetworkElements());
        roots = new ArrayList<>(anotherNetwork.getRoots());
        switches = new ArrayList<>(anotherNetwork.getSwitches());
        users = new ArrayList<>(anotherNetwork.getUsers());
    }


    public void addDeviceToSwitch(Device device, int position) {
        NetworkElement element = networkElements.get(position);
        if (element instanceof EmptyElement) {
            addSwitch(new SwitchNetworkElement(device), position);
        } else if (element instanceof SwitchNetworkElement) {
            ((SwitchNetworkElement) element).addDevice(device);
        }
    }

    public void addUser(NetworkElement user) {
        this.size++;
        increaseLinksMatrix();
        addUser(user, this.size - 1);
    }

    public void addSwitch(NetworkElement hub) {
        this.size++;
        increaseLinksMatrix();
        addSwitch(hub, this.size - 1);
    }

    public void addRoot(NetworkElement root) {
        this.size++;
        increaseLinksMatrix();
        addRoot(root, this.size - 1);
    }

    public void addEmptyElement(NetworkElement empty) {
        this.size++;
        increaseLinksMatrix();
        networkElements.add(empty);
    }
    
    @Override
    public void removeRoot(NetworkElement root) {
        super.removeRoot(root);
        networkElements.set(networkElements.indexOf(root), new EmptyElement());
    }

    @Override
    public void removeSwitch(NetworkElement hub) {
        super.removeSwitch(hub);
        networkElements.set(networkElements.indexOf(hub), new EmptyElement());
    }

    @Override
    public void removeUser(NetworkElement user) {
        super.removeUser(user);
        networkElements.set(networkElements.indexOf(user), new EmptyElement());
    }

    @Override
    public List<Integer> getEmptyPosition() {
        List<Integer> emptyPosition = new ArrayList<>();
        for (NetworkElement element : networkElements) {
            if (element instanceof EmptyElement) {
                emptyPosition.add(networkElements.indexOf(element));
            }
        }
        return emptyPosition;
    }

    private void increaseLinksMatrix() {
        networkElements.add(null);
        AbstractLink[][] newLinksMatrix = new AbstractLink[this.size][this.size];
        for (int i = 0; i < this.size - 1; i++) {
            newLinksMatrix[i] = Arrays.copyOf(linksMatrix[i], this.size);
        }

        linksMatrix = newLinksMatrix;
    }

    public void resize(int size) {
        this.size = size;
        linksMatrix = new DoubleLink[size][size];
        networkElements = Stream.generate(EmptyElement::new).limit(size).collect(Collectors.toList());
        roots = new ArrayList<>();
        switches = new ArrayList<>();
        users = new ArrayList<>();
    }
}

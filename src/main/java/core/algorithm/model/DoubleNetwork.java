/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.model;

import core.algorithm.inventory.NetworkElement;
import core.algorithm.inventory.networkelement.EmptyElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoubleNetwork extends AbstractNetwork<Double, NetworkElement<Double>> {
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

    @Override
    public void removeRoot(NetworkElement<Double> root) {
        super.removeRoot(root);
        networkElements.set(networkElements.indexOf(root), new EmptyElement());
    }

    @Override
    public void removeSwitch(NetworkElement<Double> hub) {
        super.removeSwitch(hub);
        networkElements.set(networkElements.indexOf(hub), new EmptyElement());
    }

    @Override
    public void removeUser(NetworkElement<Double> user) {
        super.removeUser(user);
        networkElements.set(networkElements.indexOf(user), new EmptyElement());
    }

    @Override
    public List<Integer> getEmptyPosition() {
        List<Integer> emptyPosition = new ArrayList<>();
        for (NetworkElement<Double> element : networkElements) {
            if (element instanceof EmptyElement) {
                emptyPosition.add(networkElements.indexOf(element));
            }
        }
        return emptyPosition;
    }
}

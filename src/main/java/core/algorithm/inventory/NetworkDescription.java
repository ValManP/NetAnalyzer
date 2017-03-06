/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.inventory;

import core.algorithm.model.AbstractNetwork;

import java.util.List;

public class NetworkDescription {
    private List<Integer> emptyPosition;
    private int minDeviceCount;
    private AbstractNetwork network;
    private Storage storage;

    public NetworkDescription(AbstractNetwork network, Storage storage) {
        this.network = network;
        this.storage = storage;
        emptyPosition = network.getEmptyPosition();
    }

    public int getMinDeviceCount() {
        return minDeviceCount;
    }

    public void setMinDeviceCount(int minDeviceCount) {
        this.minDeviceCount = minDeviceCount;
    }

    public AbstractNetwork getNetwork() {
        return network;
    }

    public void setNetwork(AbstractNetwork network) {
        this.network = network;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<Integer> getEmptyPosition() {
        return emptyPosition;
    }

    public void setEmptyPosition(List<Integer> emptyPosition) {
        this.emptyPosition = emptyPosition;
    }
}

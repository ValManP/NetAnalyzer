/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.network;

import core.model.inventory.AbstractStorage;

import java.util.List;

public class NetworkDescription {
    private List<Integer> emptyPosition;
    private int minDeviceCount;
    private double deviceProbabilityBorder;
    private AbstractNetwork network;
    private AbstractStorage storage;

    public NetworkDescription(AbstractNetwork network, AbstractStorage storage) {
        this.network = network;
        this.storage = storage;
        emptyPosition = network.getEmptyPosition();
    }

    public int getMinDeviceCount() {
        return minDeviceCount;
    }

    public void setMinDeviceCount(int minDeviceCount) {
        this.minDeviceCount = minDeviceCount;
        deviceProbabilityBorder = (double) minDeviceCount / (double) emptyPosition.size();
    }

    public AbstractNetwork getNetwork() {
        return network;
    }

    public void setNetwork(AbstractNetwork network) {
        this.network = network;
    }

    public AbstractStorage getStorage() {
        return storage;
    }

    public void setStorage(AbstractStorage storage) {
        this.storage = storage;
    }

    public List<Integer> getEmptyPosition() {
        return emptyPosition;
    }

    public void setEmptyPosition(List<Integer> emptyPosition) {
        this.emptyPosition = emptyPosition;
    }

    public double getDeviceProbabilityBorder() {
        return deviceProbabilityBorder;
    }
}

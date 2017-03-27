package core.model.network;

import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.storage.Device;
import core.model.inventory.impl.storage.DoubleStorage;

import java.util.List;

public class NetworkDescription {
    private List<Integer> emptyPosition;
    private int minDeviceCount;
    private double deviceProbabilityBorder;
    private AbstractNetwork network;
    private AbstractStorage storage;

    public NormalizeCoefficient capacityNormalizeCoefficient, costNormalizeCoefficient;

    public NetworkDescription(AbstractNetwork network, AbstractStorage storage) {
        this.network = network;
        this.storage = storage;
        emptyPosition = network.getEmptyPosition();

        capacityNormalizeCoefficient = new NormalizeCoefficient();
        costNormalizeCoefficient = new NormalizeCoefficient();
        calculateNormalizeCoefficient(storage);
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

    private void calculateNormalizeCoefficient(AbstractStorage storage) {
        capacityNormalizeCoefficient.min = ((DoubleStorage) storage).getElements().stream().min(Device::compareByCapacity).get().getCapacity();
        capacityNormalizeCoefficient.max = ((DoubleStorage) storage).getElements().stream().max(Device::compareByCapacity).get().getCapacity();

        costNormalizeCoefficient.min = ((DoubleStorage) storage).getElements().stream().min(Device::compareByPrice).get().getPrice();
        costNormalizeCoefficient.max = ((DoubleStorage) storage).getElements().stream()
                .mapToDouble(Device::getPrice).sum();
    }

    public class NormalizeCoefficient {
        private double min;
        private double max;

        public NormalizeCoefficient() {
            min = 0;
            max = 0;
        }

        public double getMin() {
            return min;
        }

        public double getMax() {
            return max;
        }
    }
}

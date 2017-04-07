package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;
import core.model.inventory.impl.storage.Device;
import core.utils.CommonTool;

import java.util.ArrayList;
import java.util.List;

public class SwitchNetworkElement extends NetworkElement {
    List<Device> devices;
    private double price;

    public SwitchNetworkElement(Device device) {
        devices = new ArrayList<>();
        addDevice(device);
    }

    public SwitchNetworkElement(String name, double capacity, double price) {
        devices = new ArrayList<>();
        addDevice(new Device(name, capacity, price));
    }

    public void addDevice(Device device) {
        devices.add(device);
        this.price += device.getPrice();
        this.capacity += device.getCapacity();
    }

    public void removeDevice(String deviceName) {
        Device device = devices.stream().filter(dev -> dev.getName().equals(deviceName)).findFirst().orElse(null);
        if (CommonTool.isExist(device)) {
            devices.removeIf(dev -> dev.getName().equals(deviceName));
            this.price -= device.getPrice();
            this.capacity -= device.getCapacity();
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SwitchNetworkElement{" + "devices=" + devices + ", price=" + price + '}';
    }
}

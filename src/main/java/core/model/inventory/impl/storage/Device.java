package core.model.inventory.impl.storage;

public class Device {
    private String name;
    private double capacity;
    private double price;

    public Device(String name, double capacity, double price) {
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public static int compareByPrice(Device device1, Device device2) {
        return Double.compare(device1.getPrice(), device2.getPrice());
    }

    public static int compareByCapacity(Device device1, Device device2) {
        return Double.compare(device1.getCapacity(), device2.getCapacity());
    }
}

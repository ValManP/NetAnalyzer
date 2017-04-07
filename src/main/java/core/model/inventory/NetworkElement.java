package core.model.inventory;

public abstract class NetworkElement {
    public double capacity;

    public NetworkElement() {
    }

    public NetworkElement(double capacity) {
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "NetworkElement{" + "capacity=" + capacity + '}';
    }
}

package core.model.inventory;

import java.util.Locale;

public abstract class NetworkElement {
    public double capacity;
    protected String name;

    public NetworkElement() {
    }

    public NetworkElement(double capacity) {
        this.capacity = capacity;
    }

    public NetworkElement(String name, double capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + String.format(Locale.US, "%.2f", capacity) + ")";
    }

    public void generateName(int num) {
        name = getElementType() + " " + num;
    }

    public abstract String getElementType();
}

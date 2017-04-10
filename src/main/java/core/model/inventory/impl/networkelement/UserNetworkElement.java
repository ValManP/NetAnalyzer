package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class UserNetworkElement extends NetworkElement {
    private double desiredLevel;

    public UserNetworkElement(double capacity, double desiredLevel) {
        this.capacity = capacity;
        this.desiredLevel = desiredLevel;
    }

    public UserNetworkElement(String name, double capacity) {
        super(name, capacity);
    }

    public double getDesiredLevel() {
        return desiredLevel;
    }

    public void setDesiredLevel(double desiredLevel) {
        this.desiredLevel = desiredLevel;
    }
}

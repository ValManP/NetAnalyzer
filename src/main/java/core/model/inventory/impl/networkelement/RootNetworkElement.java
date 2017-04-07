package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class RootNetworkElement extends NetworkElement {
    public RootNetworkElement(double capacity) {
        super(capacity);
    }

    @Override
    public String toString() {
        return "RootNetworkElement{" + '}';
    }
}

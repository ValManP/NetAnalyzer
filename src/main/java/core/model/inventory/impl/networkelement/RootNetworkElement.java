package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class RootNetworkElement extends NetworkElement {
    public RootNetworkElement(double capacity) {
        super(capacity);
    }

    public RootNetworkElement(String name, double capacity) {
        super(name, capacity);
    }

    @Override
    public String getElementType() {
        return "Root";
    }
}

/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class EmptyElement extends NetworkElement<Double> {
    public EmptyElement() {
        this.capacity = 0.0;
    }
}

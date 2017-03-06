/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.inventory.networkelement;

import core.algorithm.inventory.NetworkElement;

public class EmptyElement extends NetworkElement<Double> {
    public EmptyElement() {
        this.capacity = 0.0;
    }
}

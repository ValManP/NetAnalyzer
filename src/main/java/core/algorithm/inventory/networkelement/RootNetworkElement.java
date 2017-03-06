/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.inventory.networkelement;

import core.algorithm.inventory.NetworkElement;

public class RootNetworkElement<TrafficType> extends NetworkElement<TrafficType> {
    public RootNetworkElement(TrafficType capacity) {
        super(capacity);
    }
}

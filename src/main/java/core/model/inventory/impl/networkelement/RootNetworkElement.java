/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class RootNetworkElement<TrafficType> extends NetworkElement<TrafficType> {
    public RootNetworkElement(TrafficType capacity) {
        super(capacity);
    }
}

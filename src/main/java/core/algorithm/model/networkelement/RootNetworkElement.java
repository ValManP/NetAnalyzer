/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.model.networkelement;

import core.algorithm.model.NetworkElement;

public class RootNetworkElement<TrafficType> extends NetworkElement<TrafficType> {
    public RootNetworkElement(TrafficType capacity) {
        super(capacity);
    }
}

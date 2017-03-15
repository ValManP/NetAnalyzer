/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory.impl.networkelement;

import core.model.inventory.NetworkElement;

public class UserNetworkElement<TrafficType> extends NetworkElement<TrafficType> {
    private TrafficType desiredLevel;

    public UserNetworkElement(TrafficType capacity, TrafficType desiredLevel) {
        super(capacity);
        this.desiredLevel = desiredLevel;
    }

    public TrafficType getDesiredLevel() {
        return desiredLevel;
    }

    public void setDesiredLevel(TrafficType desiredLevel) {
        this.desiredLevel = desiredLevel;
    }
}

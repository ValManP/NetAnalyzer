/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory;

public abstract class NetworkElement<TrafficType> {
    protected TrafficType capacity;

    public NetworkElement() {
    }

    public NetworkElement(TrafficType capacity) {
        this.capacity = capacity;
    }

    public TrafficType getCapacity() {
        return capacity;
    }

    public void setCapacity(TrafficType capacity) {
        this.capacity = capacity;
    }
}

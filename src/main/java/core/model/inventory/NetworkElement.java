/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory;

public abstract class NetworkElement {
    public double capacity;

    public NetworkElement() {
    }

    public NetworkElement(double capacity) {
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}

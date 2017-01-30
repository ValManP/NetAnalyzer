/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.metamodel;

public class Link<V> {
    private V capacity;
    private V reliability;
    private V traffic;

    public V getCapacity() {
        return capacity;
    }

    public void setCapacity(V capacity) {
        this.capacity = capacity;
    }

    public V getReliability() {
        return reliability;
    }

    public void setReliability(V reliability) {
        this.reliability = reliability;
    }

    public V getTraffic() {
        return traffic;
    }

    public void setTraffic(V traffic) {
        this.traffic = traffic;
    }
}

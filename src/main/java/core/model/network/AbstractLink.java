/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.network;

public abstract class AbstractLink<TrafficType> {
    private boolean isDuplex;
    private TrafficType capacity;
    private TrafficType reliability;
    private TrafficType traffic;

    public AbstractLink(boolean isDuplex, TrafficType capacity, TrafficType reliability, TrafficType traffic) {
        this.isDuplex = isDuplex;
        this.capacity = capacity;
        this.reliability = reliability;
        this.traffic = traffic;
    }

    public TrafficType getCapacity() {
        return capacity;
    }

    public void setCapacity(TrafficType capacity) {
        this.capacity = capacity;
    }

    public TrafficType getReliability() {
        return reliability;
    }

    public void setReliability(TrafficType reliability) {
        this.reliability = reliability;
    }

    public TrafficType getTraffic() {
        return traffic;
    }

    public void setTraffic(TrafficType traffic) {
        this.traffic = traffic;
    }

    public boolean isDuplex() {
        return isDuplex;
    }

    public void setDuplex(boolean duplex) {
        isDuplex = duplex;
    }
}

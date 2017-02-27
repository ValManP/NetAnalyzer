/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.model.networkelement;

import core.algorithm.model.NetworkElement;

public class SwitchNetworkElement<TrafficType> extends NetworkElement<TrafficType> {
    private double price;

    public SwitchNetworkElement(TrafficType capacity, double price) {
        super(capacity);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

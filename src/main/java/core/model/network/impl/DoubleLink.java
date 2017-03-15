/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.network.impl;

import core.model.network.AbstractLink;

public class DoubleLink extends AbstractLink<Double> {
    public DoubleLink(boolean isDuplex, Double capacity, Double reliability, Double traffic) {
        super(isDuplex, capacity, reliability, traffic);
    }
}

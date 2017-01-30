/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.fastheuristicalgorithm.model;

import core.algorithm.metamodel.Link;

public class FSOLink extends Link<Double> {
    public FSOLink (double capacity, double reliability, double traffic) {
        this.setCapacity(capacity);
        this.setReliability(reliability);
        this.setTraffic(traffic);
    }
}

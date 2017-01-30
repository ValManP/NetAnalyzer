/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.fastheuristicalgorithm.model;

import core.algorithm.metamodel.Network;

/**
 * Free Space Options Network
 */
public class FSONetwork extends Network<FSOLink> {
    public FSONetwork(int size) {
        this.size = size;
        this.trafficMatrix = new FSOLink[size][size];
    }
}

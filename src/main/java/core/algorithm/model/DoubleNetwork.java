/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.model;

import java.util.ArrayList;

public class DoubleNetwork extends AbstractNetwork<Double, NetworkElement<Double>> {
    public DoubleNetwork(int size) {
        this.size = size;
        linksMatrix = new DoubleLink[size][size];
        networkElements = new ArrayList<>();
        roots = new ArrayList<>();
        switches = new ArrayList<>();
        users = new ArrayList<>();
    }
}

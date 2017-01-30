/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.metamodel;

import tools.CommonTool;

public abstract class Network<V> {
    protected int size;
    protected V[][] trafficMatrix;

    public V getTraffic(int i, int j) {
        if (CommonTool.isBelong(i, size) && CommonTool.isBelong(i, size)) {
            return trafficMatrix[i][j];
        }
        return null;
    }

    public void setTraffic (int i, int j, V traffic) {
        if (CommonTool.isBelong(i, size) && CommonTool.isBelong(i, size)) {
            trafficMatrix[i][j] = traffic;
        }
    }

    public int getSize() {
        return size;
    }
}

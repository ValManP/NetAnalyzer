/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.suppliers;

import core.algorithm.ga.NetworkAllele;
import core.algorithm.inventory.NetworkDescription;

import java.util.Random;
import java.util.function.Supplier;

public class NetworkSupplier implements Supplier<NetworkAllele> {
    NetworkDescription networkDescription;

    public NetworkSupplier(NetworkDescription networkDescription) {
        this.networkDescription = networkDescription;
    }

    @Override
    public NetworkAllele get() {
        Random random = new Random();
        boolean isSet = false;//random.nextBoolean();
        int position = -1;//networkDescription.getEmptyPosition().get(random.nextInt(networkDescription.getEmptyPosition().size()));

        return new NetworkAllele(isSet, position);
    }
}

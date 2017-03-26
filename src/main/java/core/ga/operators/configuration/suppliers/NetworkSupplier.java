/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.configuration.suppliers;

import core.ga.operators.configuration.NetworkAllele;
import core.model.network.NetworkDescription;

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
        boolean isSet = false;
        int position = -1;

        double deviceProbability = random.nextDouble();
        if (deviceProbability < networkDescription.getDeviceProbabilityBorder()) {
            int devicePosition = random.nextInt(networkDescription.getEmptyPosition().size());
            isSet = true;
            position = networkDescription.getEmptyPosition().get(devicePosition);
        }

        return new NetworkAllele(isSet, position);
    }
}

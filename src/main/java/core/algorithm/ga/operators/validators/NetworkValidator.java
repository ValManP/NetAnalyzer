/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.validators;

import core.algorithm.ga.NetworkAllele;
import core.algorithm.inventory.NetworkDescription;
import org.jenetics.util.ISeq;

import java.util.*;
import java.util.function.Predicate;

public class NetworkValidator<A> implements Predicate<ISeq<NetworkAllele>> {
    NetworkDescription networkDescription;

    public NetworkValidator(NetworkDescription networkDescription) {
        this.networkDescription = networkDescription;
    }

    @Override
    public boolean test(ISeq<NetworkAllele> networkAlleles) {

        int valid = validateChromosome(networkAlleles);
        if (valid == 0) {
            fixChromosome(networkAlleles);
            valid = validateChromosome(networkAlleles);
        }
        if (valid != 2) {
            return false;
        }
        return true;
    }

    private int validateChromosome(ISeq<NetworkAllele> networkAlleles) {
        Set<Integer> positions = new HashSet<>();

        int count = 0;
        for (NetworkAllele networkAllele : networkAlleles) {
            if (networkAllele.isSet) {
                count++;
            }
        }
        if (count > networkDescription.getEmptyPosition().size()) {
            return 1;
        }

        for (NetworkAllele networkAllele : networkAlleles) {
            if (networkAllele.isSet && !positions.add(networkAllele.position)) {
                return 0;
            }
        }

        if (positions.size() < networkDescription.getMinDeviceCount()) {
            return 1;
        }
        return 2;
    }

    private void fixChromosome(ISeq<NetworkAllele> networkAlleles) {
        int emptyPositionsSize = networkDescription.getEmptyPosition().size();
        int[] positions = new int[emptyPositionsSize];
        Arrays.fill(positions, 0);

        int devicePosition;

        for (NetworkAllele networkAllele : networkAlleles) {
            if (networkAllele.isSet) {
                devicePosition = networkDescription.getEmptyPosition().indexOf(networkAllele.position);

                if (positions[devicePosition] == 1) {
                    for (int i = 0; i < emptyPositionsSize; i++) {
                        if (positions[i] == 0) {
                            devicePosition = i;
                            break;
                        }
                    }
                }

                networkAllele.position = networkDescription.getEmptyPosition().get(devicePosition);
                positions[devicePosition] = 1;
            }
        }
    }
}

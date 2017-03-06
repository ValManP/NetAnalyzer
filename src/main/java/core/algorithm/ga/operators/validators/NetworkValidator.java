/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.validators;

import core.algorithm.ga.NetworkAllele;
import core.algorithm.inventory.NetworkDescription;
import org.jenetics.util.ISeq;

import java.util.function.Predicate;

public class NetworkValidator<A> implements Predicate<ISeq<NetworkAllele>> {
    NetworkDescription networkDescription;

    public NetworkValidator(NetworkDescription networkDescription) {
        this.networkDescription = networkDescription;
    }

    @Override
    public boolean test(ISeq<NetworkAllele> networkAlleles) {
        return (networkAlleles.stream().filter(allele -> allele.isSet).distinct().count() == networkAlleles.stream().filter(allele -> allele.isSet).count())
                && (networkAlleles.stream().filter(allele -> allele.isSet).count() >= networkDescription.getMinDeviceCount());
    }
}

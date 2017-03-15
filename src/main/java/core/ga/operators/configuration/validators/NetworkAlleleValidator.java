/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.configuration.validators;

import core.ga.operators.configuration.NetworkAllele;

import java.util.function.Predicate;

public class NetworkAlleleValidator implements Predicate<NetworkAllele> {
    @Override
    public boolean test(NetworkAllele networkAllele) {
        return true;
    }
}

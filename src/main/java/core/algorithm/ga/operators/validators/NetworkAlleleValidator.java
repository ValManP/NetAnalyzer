/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.validators;

import core.algorithm.ga.NetworkAllele;

import java.util.function.Predicate;

public class NetworkAlleleValidator implements Predicate<NetworkAllele> {
    @Override
    public boolean test(NetworkAllele networkAllele) {
        return true;
    }
}

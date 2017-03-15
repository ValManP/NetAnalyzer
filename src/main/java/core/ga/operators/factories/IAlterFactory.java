/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.factories;

import core.ga.operators.factories.alterer.types.IAltererType;
import org.jenetics.AbstractAlterer;
import org.jenetics.Gene;

public interface IAlterFactory<G extends Gene<?, G>, C extends Comparable<? super C>> {
    AbstractAlterer<G, C> createAlterer(IAltererType altererType);
}

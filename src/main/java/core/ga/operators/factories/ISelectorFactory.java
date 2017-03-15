/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.factories;

import core.ga.operators.factories.alterer.types.SelectorTypes;
import org.jenetics.Gene;
import org.jenetics.Selector;

public interface ISelectorFactory<G extends Gene<?, G>, C extends Comparable<? super C>> {
    Selector<G, C> createSelector(SelectorTypes selectorTypes);
}

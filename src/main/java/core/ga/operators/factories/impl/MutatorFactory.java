/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.factories.impl;

import core.ga.operators.factories.IAlterFactory;
import core.ga.operators.factories.alterer.types.IAltererType;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import org.jenetics.*;

public class MutatorFactory implements IAlterFactory {
    @Override
    public AbstractAlterer createAlterer(IAltererType altererType) {
        AbstractAlterer result = null;
        double probability = altererType.getProbability();

        if (altererType == MutatorTypes.MUTATOR) {
            result = (IAltererType.probability == probability) ? new Mutator() : new Mutator(probability);
        } else if (altererType == MutatorTypes.SWAP_MUTATOR) {
            result = (IAltererType.probability == probability) ? new SwapMutator() : new SwapMutator(probability);
        } else if (altererType == MutatorTypes.GAUSSIAN_MUTATOR) {
            result = (IAltererType.probability == probability) ? new GaussianMutator() : new GaussianMutator(probability);
        }

        return result;
    }
}

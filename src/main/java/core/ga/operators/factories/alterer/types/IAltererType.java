/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.factories.alterer.types;

import org.jenetics.Alterer;

public interface IAltererType {
    double probability = Alterer.DEFAULT_ALTER_PROBABILITY;

    double getProbability();
    IAltererType withProbability(double probability);
}

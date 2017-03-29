package core.ga.operators.factories.impl;

import core.ga.operators.factories.ISelectorFactory;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import org.jenetics.*;

public class SelectorFactory implements ISelectorFactory {
    @Override
    public Selector createSelector(SelectorTypes selectorTypes) {
        Selector result = null;
        double selectionVariable = selectorTypes.getSelectionVariable();

        if (selectorTypes == SelectorTypes.BOLTZMANN_SELECTOR) {
            result = (SelectorTypes.SELECTION_VARIABLE == selectionVariable) ? new BoltzmannSelector() : new BoltzmannSelector(selectionVariable);
        } else if (selectorTypes == SelectorTypes.EXPONENTIAL_RANK_SELECTOR) {
            result = (SelectorTypes.SELECTION_VARIABLE == selectionVariable) ? new ExponentialRankSelector() : new ExponentialRankSelector(selectionVariable);
        } else if (selectorTypes == SelectorTypes.LINEAR_RANK_SELECTOR) {
            result = (SelectorTypes.SELECTION_VARIABLE == selectionVariable) ? new LinearRankSelector() : new LinearRankSelector(selectionVariable);
        } else if (selectorTypes == SelectorTypes.MONTE_CARLO_SELECTOR) {
            result = new MonteCarloSelector();
        } else if (selectorTypes == SelectorTypes.ROULETTE_WHEEL_SELECTOR) {
            result = new RouletteWheelSelector();
        } else if (selectorTypes == SelectorTypes.STOCHASTIC_UNIVERSAL_SELECTOR) {
            result = new StochasticUniversalSelector();
        } else if (selectorTypes == SelectorTypes.TOURNAMENT_SELECTOR) {
            result = (SelectorTypes.SELECTION_VARIABLE == selectionVariable) ? new TournamentSelector() : new TournamentSelector((int) selectionVariable);
        } else if (selectorTypes == SelectorTypes.TRUNCATION_SELECTOR) {
            result = new TruncationSelector();
        }

        return result;
    }
}

package experiments.impl;

import com.google.common.collect.ImmutableList;
import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.engine.EvolutionResult;

import java.util.List;

public class SelectorExperiment extends AbstractExperiment {
    private List<SelectorTypes> selectorTypes = ImmutableList.<SelectorTypes>builder()
            .add(SelectorTypes.TOURNAMENT_SELECTOR)
            .add(SelectorTypes.TRUNCATION_SELECTOR)
            .add(SelectorTypes.BOLTZMANN_SELECTOR)
            .add(SelectorTypes.EXPONENTIAL_RANK_SELECTOR)
            .add(SelectorTypes.LINEAR_RANK_SELECTOR)
            .add(SelectorTypes.MONTE_CARLO_SELECTOR)
            .add(SelectorTypes.ROULETTE_WHEEL_SELECTOR)
            .add(SelectorTypes.STOCHASTIC_UNIVERSAL_SELECTOR)
            .build();


    public SelectorExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {
        for (SelectorTypes type : selectorTypes) {
            writeToLog(type.name());
            for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
                start();
                Evolution evolution = createEvolution(parameters.setSelector(type));
                EvolutionResult result = evolution.evolve(parameters.getGenerationLimit());
                finish();

                writeToLog(result.getBestFitness().toString(),
                        String.valueOf(result.getTotalGenerations()), String.valueOf(getExperimentTime()));
            }
        }
    }
}

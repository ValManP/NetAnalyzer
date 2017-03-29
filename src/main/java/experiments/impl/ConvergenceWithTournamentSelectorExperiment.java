package experiments.impl;

import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.engine.EvolutionResult;

import java.io.IOException;

public class ConvergenceWithTournamentSelectorExperiment extends AbstractExperiment{
    public ConvergenceWithTournamentSelectorExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {
        int selectorVariable = 2;
        while (selectorVariable <= 10) {
            for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
                start();
                Evolution evolution = createEvolution(parameters.setSelector(SelectorTypes.TOURNAMENT_SELECTOR.withSelectionVariable(selectorVariable)));
                EvolutionResult result = evolution.evolveBySteadyFitness(parameters.getGenerationLimit());
                finish();

                writeToLog(String.valueOf(selectorVariable), result.getBestFitness().toString(),
                        String.valueOf(result.getTotalGenerations()), String.valueOf(getExperimentTime()));
            }

            selectorVariable += 2;
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}

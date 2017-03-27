package experiments.impl;

import core.ga.facade.Evolution;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.engine.EvolutionResult;

import java.io.IOException;

public class ConvergenceExperiment extends AbstractExperiment {
    public ConvergenceExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {
        for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
            start();
            Evolution evolution = createEvolution(parameters);
            EvolutionResult result = evolution.evolveBySteadyFitness(parameters.getGenerationLimit());
            finish();

            writeToLog(result.getBestFitness().toString(),
                    String.valueOf(result.getTotalGenerations()), String.valueOf(getExperimentTime()));
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}

package experiments.impl;

import com.google.common.collect.ImmutableList;
import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.engine.EvolutionResult;

import java.io.IOException;
import java.util.List;

public class ConvergenceWithDiffMutatorExperiment extends AbstractExperiment {
    private List<MutatorTypes> mutatorTypes = ImmutableList.<MutatorTypes>builder()
            .add(MutatorTypes.MUTATOR)
            .add(MutatorTypes.SWAP_MUTATOR)
            .build();

    public ConvergenceWithDiffMutatorExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {
        for (MutatorTypes type : mutatorTypes) {
            double probability = 0.5;
            writeToLog(type.name());
            while (probability <= 1) {
                for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
                    start();
                    Evolution evolution = createEvolution(parameters.setMutator(type.withProbability(probability)));
                    EvolutionResult result = evolution.evolveBySteadyFitness(parameters.getGenerationLimit());
                    finish();

                    writeToLog(result.getBestFitness().toString(),
                            String.valueOf(result.getTotalGenerations()), String.valueOf(getExperimentTime()));
                }

                probability += parameters.getExperimentStep();
            }
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}

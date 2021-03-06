package experiments.impl;

import com.google.common.collect.ImmutableList;
import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.CrossoverTypes;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.engine.EvolutionResult;

import java.io.IOException;
import java.util.List;

public class ConvergenceWithDiffCrossoverExperiment extends AbstractExperiment {
    private List<CrossoverTypes> crossoverTypes = ImmutableList.<CrossoverTypes>builder()
            .add(CrossoverTypes.UNIFORM_CROSSOVER)
            .add(CrossoverTypes.SINGLE_POINT_CROSSOVER)
            .add(CrossoverTypes.MULTI_POINT_CROSSOVER)
            .add(CrossoverTypes.PARTIALLY_MATCHED_CROSSOVER)
            .build();

    public ConvergenceWithDiffCrossoverExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {

        for (CrossoverTypes type : crossoverTypes) {
            double probability = 0.5;
            writeToLog(type.name());
            while (probability <= 1) {
                for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
                    start();
                    Evolution evolution = createEvolution(parameters.setCrossover(type.withProbability(probability)));
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

package experiments.impl;

import core.ga.facade.Evolution;
import experiments.AbstractExperiment;
import experiments.ExperimentParameters;
import org.jenetics.Phenotype;

import java.io.IOException;

public class PopulationExperiment extends AbstractExperiment {
    public PopulationExperiment(ExperimentParameters parameters, String fileName) {
        super(parameters, fileName);
    }

    @Override
    public void execute() {
        int population = parameters.getInitialPopulation(), finish = population * 5;
        while (population < finish) {
            for (int i = 0; i < parameters.getTimeOfExecution(); i++) {
                start();
                Evolution evolution = createEvolution(parameters.setInitialPopulation(population));
                Phenotype phenotype = evolution.evolveToBestPhenotype(parameters.getGenerationLimit());
                finish();

                writeToLog(String.valueOf(population), phenotype.getFitness().toString(),
                        String.valueOf(phenotype.getGeneration()), String.valueOf(getExperimentTime()));
            }

            population += (int) parameters.getExperimentStep();
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}

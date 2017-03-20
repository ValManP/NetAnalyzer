/**
 * Created by Valerii Pozdiaev on 2017.
 */
package experiments;

import core.ga.facade.Evolution;
import core.ga.operators.factories.alterer.types.CrossoverTypes;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.model.inventory.AbstractStorage;
import core.model.network.AbstractNetwork;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public abstract class AbstractExperiment {
    private long startTime, endTime;
    private String logFile;
    private Path file;
    protected ExperimentParameters parameters;

    public AbstractExperiment(ExperimentParameters parameters, String fileName) {
        this.parameters = parameters;
        this.logFile = fileName;
        file = Paths.get(logFile);
    }

    public abstract void execute();

    protected Evolution createEvolution(ExperimentParameters parameters){
        return (new Evolution(parameters.getNetwork(), parameters.getStorage())).builder()
                .alterer(parameters.getCrossover(), parameters.getMutator())
                .selector(parameters.getSelector())
                .initialPopulation(parameters.getInitialPopulation())
                .buildEngine();
    }

    protected void start(){
        startTime = System.nanoTime();
    }

    protected void finish(){
        endTime = System.nanoTime();
    }

    protected double getExperimentTime(){
        return (endTime - startTime) / Math.pow(10.0, 9);
    }

    protected void writeToLog(String... data){
        try {
            Files.write(file, Arrays.asList(data), Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}

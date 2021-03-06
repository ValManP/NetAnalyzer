package experiments;

import core.ga.operators.factories.alterer.types.IAltererType;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.model.inventory.AbstractStorage;
import core.model.network.AbstractNetwork;

public class ExperimentParameters {
    private AbstractNetwork network;
    private AbstractStorage storage;
    private double experimentStep;
    private int timeOfExecution;
    private IAltererType crossover;
    private IAltererType mutator;
    private SelectorTypes selector;
    private int initialPopulation;
    private int generationLimit;

    public ExperimentParameters(AbstractNetwork network, AbstractStorage storage, double experimentStep,
                                int timeOfExecution, IAltererType crossover, IAltererType mutator,
                                SelectorTypes selector, int initialPopulation, int generationLimit) {
        this.network = network;
        this.storage = storage;
        this.experimentStep = experimentStep;
        this.timeOfExecution = timeOfExecution;
        this.crossover = crossover;
        this.mutator = mutator;
        this.selector = selector;
        this.initialPopulation = initialPopulation;
        this.generationLimit = generationLimit;
    }

    public AbstractNetwork getNetwork() {
        return network;
    }

    public AbstractStorage getStorage() {
        return storage;
    }

    public double getExperimentStep() {
        return experimentStep;
    }

    public int getTimeOfExecution() {
        return timeOfExecution;
    }

    public IAltererType getCrossover() {
        return crossover;
    }

    public ExperimentParameters setCrossover(IAltererType crossover) {
        this.crossover = crossover;
        return this;
    }

    public IAltererType getMutator() {
        return mutator;
    }

    public ExperimentParameters setMutator(IAltererType mutator) {
        this.mutator = mutator;
        return this;
    }

    public SelectorTypes getSelector() {
        return selector;
    }

    public ExperimentParameters setSelector(SelectorTypes selector) {
        this.selector = selector;
        return this;
    }

    public int getInitialPopulation() {
        return initialPopulation;
    }

    public ExperimentParameters setInitialPopulation(int initialPopulation) {
        this.initialPopulation = initialPopulation;
        return this;
    }

    public int getGenerationLimit() {
        return generationLimit;
    }
}

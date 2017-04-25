package core.ga.facade;

import core.ga.controllers.GANetworkController;
import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.factories.IAlterFactory;
import core.ga.operators.factories.alterer.types.CrossoverTypes;
import core.ga.operators.factories.alterer.types.IAltererType;
import core.ga.operators.factories.alterer.types.MutatorTypes;
import core.ga.operators.factories.alterer.types.SelectorTypes;
import core.ga.operators.factories.impl.CrossoverFactory;
import core.ga.operators.factories.impl.MutatorFactory;
import core.ga.operators.factories.impl.SelectorFactory;
import core.ga.operators.fitness.FitnessTypes;
import core.model.inventory.AbstractStorage;
import core.model.network.AbstractNetwork;
import core.model.strategies.ICapacityCalculationStrategy;
import core.model.strategies.MinGuaranteedCapacityStrategy;
import org.jenetics.Alterer;
import org.jenetics.AnyGene;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.byFixedGeneration;
import static org.jenetics.engine.limit.bySteadyFitness;

public class Evolution {
    private AbstractNetwork network;
    private AbstractStorage storage;
    private FitnessTypes fitness;
    private ICapacityCalculationStrategy capacityCalculationStrategy = new MinGuaranteedCapacityStrategy();
    private Engine engine;
    private Engine.Builder builder;

    public Evolution(AbstractNetwork network, AbstractStorage storage, FitnessTypes fitness) {
        this.network = network;
        this.storage = storage;
        this.fitness = fitness;
    }

    public Evolution buildEngine() {
        engine = builder.build();
        return this;
    }

    public Evolution alterer(IAltererType crossover, IAltererType mutator) {
        builder.alterers(getAlterer(crossover), getAlterer(mutator));
        return this;
    }

    public Evolution selector(SelectorTypes selectorType) {
        SelectorFactory selectorFactory = new SelectorFactory();
        builder.selector(selectorFactory.createSelector(selectorType));
        return this;
    }

    public Evolution initialPopulation(int populationSize) {
        builder.populationSize(populationSize);
        return this;
    }

    public Evolution capacityCalculationStrategy(ICapacityCalculationStrategy capacityCalculationStrategy) {
        this.capacityCalculationStrategy = capacityCalculationStrategy;
        return this;
    }

    public Evolution builder() {
        builder = GANetworkController.compileBuilder(network, storage, fitness, capacityCalculationStrategy);
        return this;
    }

    public EvolutionResult iterate(int generations) {
        return GANetworkController.iterate(engine, generations);
    }

    public Evolution executors(int numberOfThreads) {
        final ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        builder.executor(executor);
        return this;
    }

    public Phenotype evolveToBestPhenotype(int generations) {
        return (Phenotype<AnyGene<NetworkAllele>, Double>)
                GANetworkController.evolve(engine, byFixedGeneration(generations), toBestPhenotype());
    }

    public EvolutionResult evolve(int generations) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>)
                GANetworkController.evolve(engine, byFixedGeneration(generations), toBestEvolutionResult());
    }

    public EvolutionResult evolveBySteadyFitness(int steadyFitnessLimit) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>)
                GANetworkController.evolve(engine, bySteadyFitness(steadyFitnessLimit), toBestEvolutionResult());
    }

    public AbstractNetwork getNetwork() {
        return network;
    }

    public AbstractStorage getStorage() {
        return storage;
    }

    public Engine getEngine() {
        return engine;
    }

    public Engine.Builder getBuilder() {
        return builder;
    }

    private Alterer getAlterer(IAltererType type) {
        IAlterFactory alterFactory = getFactory(type);
        return alterFactory.createAlterer(type);
    }

    private IAlterFactory getFactory(IAltererType altererType) {
        IAlterFactory factory = null;

        if (altererType instanceof CrossoverTypes) {
            factory = new CrossoverFactory();
        } else if (altererType instanceof MutatorTypes) {
            factory = new MutatorFactory();
        }

        return factory;
    }
}

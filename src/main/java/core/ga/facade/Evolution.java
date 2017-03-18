/**
 * Created by Valerii Pozdiaev on 2017.
 */
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
import core.model.inventory.AbstractStorage;
import core.model.network.AbstractNetwork;
import org.jenetics.Alterer;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import static org.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Evolution {
    private AbstractNetwork network;
    private AbstractStorage storage;
    private Engine engine;
    private Engine.Builder builder;

    public Evolution(AbstractNetwork network, AbstractStorage storage) {
        this.network = network;
        this.storage = storage;
    }

    public Evolution buildEngine(){
        engine = builder.build();
        return this;
    }

    public Evolution alterer(IAltererType crossover, IAltererType mutator){
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

    public Evolution builder() {
        builder = GANetworkController.compileBuilder(network, storage);
        return this;
    }

    public EvolutionResult iterate(int generations) {
        return GANetworkController.iterate(engine, generations);
    }

    public Phenotype evolveToBestPhenotype(int generations) {
        return (Phenotype<AnyGene<NetworkAllele>, Double>) GANetworkController.evolve(engine, generations, toBestPhenotype());
    }

    public EvolutionResult evolve(int generations) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>) GANetworkController.evolve(engine, generations, toBestEvolutionResult());
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

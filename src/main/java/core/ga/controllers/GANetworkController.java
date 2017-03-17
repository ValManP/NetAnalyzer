/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.controllers;

import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.configuration.factory.NetworkConfigurationFactory;
import core.ga.operators.factories.alterer.types.IAltererType;
import core.ga.operators.fitness.GAFitness;
import core.ga.operators.fitness.impl.RandomWeightFitness;
import core.ga.operators.configuration.suppliers.NetworkSupplier;
import core.ga.operators.configuration.validators.NetworkAlleleValidator;
import core.ga.operators.configuration.validators.NetworkValidator;
import core.model.inventory.AbstractStorage;
import core.model.network.AbstractNetwork;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;

import static org.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

public class GANetworkController {
    public static Engine.Builder compileBuilder(AbstractNetwork network, AbstractStorage storage) {
        NetworkDescription networkDescription = new NetworkDescription(network, storage);
        RandomWeightFitness fitnessFunction = new RandomWeightFitness(networkDescription);

        return Engine.builder(fitnessFunction::eval, createNetworkConfigurationFactory(networkDescription));
    }

    public static Object evolve(Engine engine, int generations, Collector collector) {
        return engine
                .stream()
                .limit(generations)
                .collect(collector);
    }

    public static EvolutionResult<AnyGene<NetworkAllele>, Double> iterate(Engine engine, int generation) {
        EvolutionResult<AnyGene<NetworkAllele>, Double> result = null;
        Iterator<EvolutionResult<AnyGene<NetworkAllele>, Double>> iterator = engine.iterator();
        for (int i = 0; i < generation; i++) {
            result = iterator.next();
        }
        return result;
    }

    public static DoubleNetwork applyConfiguration(NetworkDescription networkDescription, Chromosome<AnyGene<NetworkAllele>> chromosome) {
        DoubleNetwork resultNetwork = new DoubleNetwork((DoubleNetwork) networkDescription.getNetwork());

        for (int index = 0; index < chromosome.length(); index++) {
            if (chromosome.getGene(index).getAllele().isSet) {
                resultNetwork.addSwitch(networkDescription.getStorage().getElement(index),
                        chromosome.getGene(index).getAllele().position);
            }
        }

        return resultNetwork;
    }

    private static NetworkConfigurationFactory createNetworkConfigurationFactory(NetworkDescription networkDescription) {
        NetworkSupplier networkSupplier = new NetworkSupplier(networkDescription);
        NetworkValidator networkValidator = new NetworkValidator(networkDescription);
        NetworkAlleleValidator networkAlleleValidator = new NetworkAlleleValidator();

        return new NetworkConfigurationFactory(networkDescription,
                networkSupplier,
                networkAlleleValidator,
                networkValidator);
    }
}

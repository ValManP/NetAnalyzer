package core.ga.controllers;

import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.configuration.factory.NetworkConfigurationFactory;
import core.ga.operators.configuration.suppliers.NetworkSupplier;
import core.ga.operators.configuration.validators.NetworkAlleleValidator;
import core.ga.operators.configuration.validators.NetworkValidator;
import core.ga.operators.fitness.FitnessTypes;
import core.ga.operators.fitness.GAFitness;
import core.ga.operators.fitness.factory.FitnessFactory;
import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.storage.Device;
import core.model.network.AbstractNetwork;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import org.jenetics.AnyGene;
import org.jenetics.Chromosome;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Collector;

public class GANetworkController {
    public static Engine.Builder compileBuilder(AbstractNetwork network, AbstractStorage storage, FitnessTypes fitness) {
        NetworkDescription networkDescription = new NetworkDescription(network, storage);
        int emptyPosCount = network.getEmptyPosition().size();
        networkDescription.setMinDeviceCount(emptyPosCount - emptyPosCount / 3);

        FitnessFactory fitnessFactory = new FitnessFactory();
        GAFitness<AnyGene<NetworkAllele>, Double> fitnessFunction = fitnessFactory.getFitness(fitness, networkDescription);

        return Engine.builder(fitnessFunction::eval, createNetworkConfigurationFactory(networkDescription));
    }

    public static Object evolve(Engine engine, Predicate limit, Collector collector) {
        return engine
                .stream()
                .limit(limit)
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
                resultNetwork.addDeviceToSwitch((Device) networkDescription.getStorage().getElement(index),
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

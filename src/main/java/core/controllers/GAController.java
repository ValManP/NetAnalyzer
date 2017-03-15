/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.controllers;

import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.configuration.factory.NetworkConfigurationFactory;
import core.ga.operators.fitness.impl.RandomWeightFitness;
import core.ga.operators.configuration.suppliers.NetworkSupplier;
import core.ga.operators.configuration.validators.NetworkAlleleValidator;
import core.ga.operators.configuration.validators.NetworkValidator;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import org.jenetics.*;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;

import java.util.Iterator;
import java.util.List;

import static org.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

public class GAController {
    public static Engine createEngine(NetworkDescription networkDescription, NetworkSupplier networkSupplier,
                               NetworkValidator networkValidator, NetworkAlleleValidator networkAlleleValidator, List<Alterer> alterers,
                               int populationSize) {
        RandomWeightFitness fitnessFunction = new RandomWeightFitness(networkDescription);

        Engine.Builder builder = Engine
                .builder(
                        fitnessFunction::eval,
                        new NetworkConfigurationFactory(networkDescription,
                                networkSupplier,
                                networkAlleleValidator,
                                networkValidator))
                .populationSize(populationSize);

        for (Alterer alterer : alterers) {
            builder = builder.alterers(alterer);
        }

        return builder.build();
    }

    public static Phenotype<AnyGene<NetworkAllele>, Double> execute(Engine engine, int generations, int bySteadyFitness) {
        return (Phenotype<AnyGene<NetworkAllele>, Double>)engine.stream()
                .limit(bySteadyFitness(bySteadyFitness))
                .limit(generations)
                .collect(toBestPhenotype());
    }

    public static EvolutionResult<AnyGene<NetworkAllele>, Double> evolve(Engine engine, int generation) {
        return (EvolutionResult<AnyGene<NetworkAllele>, Double>)engine
                .stream()
                .limit(generation)
                .collect(toBestEvolutionResult());
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
}

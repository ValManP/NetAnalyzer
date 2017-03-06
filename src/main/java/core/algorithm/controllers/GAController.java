/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.controllers;

import core.algorithm.ga.NetworkAllele;
import core.algorithm.ga.operators.factory.NetworkConfigurationFactory;
import core.algorithm.ga.operators.fitness.RandomWeightFitness;
import core.algorithm.ga.operators.suppliers.NetworkSupplier;
import core.algorithm.ga.operators.validators.NetworkAlleleValidator;
import core.algorithm.ga.operators.validators.NetworkValidator;
import core.algorithm.inventory.NetworkDescription;
import core.algorithm.inventory.networkelement.SwitchNetworkElement;
import core.algorithm.model.AbstractNetwork;
import core.algorithm.model.DoubleNetwork;
import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Chromosome;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

public class GAController {
    public static Engine createEngine(NetworkDescription networkDescription, NetworkSupplier networkSupplier,
                               NetworkValidator networkValidator, NetworkAlleleValidator networkAlleleValidator,
                               int populationSize) {
        RandomWeightFitness fitnessFunction = new RandomWeightFitness(networkDescription);

        return Engine
                    .builder(
                            fitnessFunction::eval,
                        new NetworkConfigurationFactory(networkDescription,
                                networkSupplier,
                                networkAlleleValidator,
                                networkValidator))
             .populationSize(populationSize)
             .build();
    }

    public static Phenotype<AnyGene<NetworkAllele>, Double> execute(Engine engine) {
        return (Phenotype<AnyGene<NetworkAllele>, Double>)engine.stream()
                .limit(bySteadyFitness(5))
                .limit(10)
                .collect(toBestPhenotype());
    }

    public static DoubleNetwork applyConfiguration(NetworkDescription networkDescription, Chromosome<AnyGene<NetworkAllele>> chromosome) {
        DoubleNetwork resultNetwork = new DoubleNetwork((DoubleNetwork) networkDescription.getNetwork());

        for (int index = 0; index < chromosome.length(); index++) {
            resultNetwork.addSwitch(networkDescription.getStorage().getElement(index),
                    chromosome.getGene(index).getAllele().position);
        }

        return resultNetwork;
    }
}

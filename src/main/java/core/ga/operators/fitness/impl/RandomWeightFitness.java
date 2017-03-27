package core.ga.operators.fitness.impl;

import core.ga.controllers.GANetworkController;
import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.fitness.GAFitness;
import core.model.controllers.NetworkController;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;

import java.util.Random;

public class RandomWeightFitness extends GAFitness<AnyGene<NetworkAllele>, Double> {
    private double weight1;
    private double weight2;

    public RandomWeightFitness(NetworkDescription description) {
        networkDescription = description;
        Random random = new Random();
        weight1 = random.nextDouble();
        weight2 = 1 - weight1;
    }

    @Override
    public Double eval(Genotype<AnyGene<NetworkAllele>> gt) {
        DoubleNetwork network = GANetworkController.applyConfiguration(networkDescription, gt.getChromosome());

        double averageCapacity = normalizeCapacity(NetworkController.calculateNetworkCapacityByMinValue(network));
        double cost = normalizeCost(NetworkController.calculateNetworkCost(network));

        return weight1 * averageCapacity - weight2 * cost;
    }
}

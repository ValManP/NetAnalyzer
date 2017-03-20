/**
 * Created by Valerii Pozdiaev on 2017.
 */
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

public class ConstantWeightFitness extends GAFitness<AnyGene<NetworkAllele>, Double> {
    private double weight1;
    private double weight2;

    public ConstantWeightFitness(NetworkDescription description, double weight1) {
        networkDescription = description;
        this.weight1 = weight1;
        this.weight2 = 1 - weight1;
    }

    @Override
    public Double eval(Genotype<AnyGene<NetworkAllele>> gt) {
        DoubleNetwork network = GANetworkController.applyConfiguration(networkDescription, gt.getChromosome());

        double averageCapacity = NetworkController.calculateNetworkCapacityByMinValue(network);
        double cost = NetworkController.calculateNetworkCost(network);

        return weight1 * averageCapacity - weight2 * cost;
    }
}

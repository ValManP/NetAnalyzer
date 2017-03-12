/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.fitness;

import core.algorithm.controllers.GAController;
import core.algorithm.controllers.NetworkController;
import core.algorithm.ga.NetworkAllele;
import core.algorithm.inventory.NetworkDescription;
import core.algorithm.model.DoubleNetwork;
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


        DoubleNetwork network = GAController.applyConfiguration(networkDescription, gt.getChromosome());

        double averageCapacity = NetworkController.calculateNetworkCapacityByMinValue(network);
        double cost = NetworkController.calculateNetworkCost(network);

        return weight1 * averageCapacity - weight2 * cost;
    }
}

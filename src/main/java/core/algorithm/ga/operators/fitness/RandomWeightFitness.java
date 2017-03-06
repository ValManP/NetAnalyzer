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
    public RandomWeightFitness(NetworkDescription description) {
        networkDescription = description;
    }

    @Override
    public Double eval(Genotype<AnyGene<NetworkAllele>> gt) {
        Random random = new Random();
        double w1 = random.nextDouble();
        double w2 = 1 - w1;

        DoubleNetwork network = GAController.applyConfiguration(networkDescription, gt.getChromosome());

        double averageCapacity = NetworkController.calculateNetworkCapacityByMinValue(network);
        double cost = NetworkController.calculateNetworkCost(network);

        return w1 * averageCapacity - w2 * cost;
    }
}

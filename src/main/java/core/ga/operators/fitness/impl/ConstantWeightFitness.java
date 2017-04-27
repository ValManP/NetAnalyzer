package core.ga.operators.fitness.impl;

import core.ga.controllers.GANetworkController;
import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.fitness.GAFitness;
import core.model.controllers.NetworkController;
import core.model.network.NetworkDescription;
import core.model.network.impl.DoubleNetwork;
import core.model.strategies.ICapacityCalculationStrategy;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;

public class ConstantWeightFitness extends GAFitness<AnyGene<NetworkAllele>, Double> {
    private double weight1;
    private double weight2;

    public ConstantWeightFitness(ICapacityCalculationStrategy capacityStrategy, NetworkDescription description, double weight1) {
        super(capacityStrategy, description);
        this.weight1 = weight1;
        this.weight2 = 1 - weight1;
    }

    @Override
    public Double eval(Genotype<AnyGene<NetworkAllele>> gt) {
        DoubleNetwork network = GANetworkController.applyConfiguration(networkDescription, gt.getChromosome());

        double averageCapacity = normalizeCapacity(calculateCapacity(network));
        double cost = normalizeCost(NetworkController.calculateNetworkCost(network));

        return weight1 * averageCapacity - weight2 * cost;
    }
}

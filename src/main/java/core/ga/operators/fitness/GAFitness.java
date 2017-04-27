package core.ga.operators.fitness;

import core.model.network.AbstractNetwork;
import core.model.network.NetworkDescription;
import core.model.strategies.ICapacityCalculationStrategy;
import org.jenetics.Gene;
import org.jenetics.Genotype;

public abstract class GAFitness<G extends Gene<?, G>, R> {
    private ICapacityCalculationStrategy capacityStrategy;
    protected NetworkDescription networkDescription;

    protected GAFitness(ICapacityCalculationStrategy capacityStrategy, NetworkDescription networkDescription) {
        this.capacityStrategy = capacityStrategy;
        this.networkDescription = networkDescription;
    }

    public abstract R eval(final Genotype<G> gt);

    protected double normalizeCost(double cost) {
        return (cost - networkDescription.costNormalizeCoefficient.getMin())
                / (networkDescription.costNormalizeCoefficient.getMax() - networkDescription.costNormalizeCoefficient.getMin());
    }

    protected double normalizeCapacity(double capacity) {
        return (capacity - networkDescription.capacityNormalizeCoefficient.getMin())
                / (networkDescription.capacityNormalizeCoefficient.getMax() - networkDescription.capacityNormalizeCoefficient.getMin());
    }

    protected double calculateCapacity(AbstractNetwork network) {
        return capacityStrategy.calculate(network);
    }
}

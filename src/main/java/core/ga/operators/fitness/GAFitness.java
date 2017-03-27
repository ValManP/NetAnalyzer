package core.ga.operators.fitness;

import core.model.network.NetworkDescription;
import org.jenetics.Gene;
import org.jenetics.Genotype;

public abstract class GAFitness<G extends Gene<?, G>, R> {
    protected NetworkDescription networkDescription;

    public abstract R eval(final Genotype<G> gt);

    protected double normalizeCost(double cost) {
        return (cost - networkDescription.costNormalizeCoefficient.getMin())
                / (networkDescription.costNormalizeCoefficient.getMax() - networkDescription.costNormalizeCoefficient.getMin());
    }

    protected double normalizeCapacity(double capacity) {
        return (capacity - networkDescription.capacityNormalizeCoefficient.getMin())
                / (networkDescription.capacityNormalizeCoefficient.getMax() - networkDescription.capacityNormalizeCoefficient.getMin());
    }
}

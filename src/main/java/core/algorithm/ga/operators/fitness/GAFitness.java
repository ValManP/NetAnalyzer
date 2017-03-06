/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.fitness;

import core.algorithm.inventory.NetworkDescription;
import org.jenetics.AnyGene;
import org.jenetics.Gene;
import org.jenetics.Genotype;

public abstract class GAFitness<G extends Gene<?, G>, R> {
    protected NetworkDescription networkDescription;

    abstract R eval(final Genotype<G> gt);
}

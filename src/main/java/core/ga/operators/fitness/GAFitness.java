/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.fitness;

import core.model.network.NetworkDescription;
import org.jenetics.Gene;
import org.jenetics.Genotype;

public abstract class GAFitness<G extends Gene<?, G>, R> {
    protected NetworkDescription networkDescription;

    public abstract R eval(final Genotype<G> gt);
}

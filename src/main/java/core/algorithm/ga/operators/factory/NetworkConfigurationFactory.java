/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.ga.operators.factory;

import core.algorithm.ga.NetworkAllele;
import core.algorithm.ga.operators.suppliers.NetworkSupplier;
import core.algorithm.ga.operators.validators.NetworkAlleleValidator;
import core.algorithm.ga.operators.validators.NetworkValidator;
import core.algorithm.inventory.NetworkDescription;
import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.internal.collection.ArrayISeq;
import org.jenetics.util.Factory;
import org.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class NetworkConfigurationFactory implements Factory<Genotype<AnyGene<NetworkAllele>>> {
    private NetworkDescription networkDescription;
    private final NetworkSupplier supplier;
    private final NetworkAlleleValidator alleleValidator;
    private final NetworkValidator alleleSeqValidator;

    public NetworkConfigurationFactory(NetworkDescription networkDescription, NetworkSupplier supplier, NetworkAlleleValidator alleleValidator, NetworkValidator alleleSeqValidator) {
        this.networkDescription = networkDescription;
        this.supplier = supplier;
        this.alleleValidator = alleleValidator;
        this.alleleSeqValidator = alleleSeqValidator;
    }

    @Override
    public Genotype<AnyGene<NetworkAllele>> newInstance() {
        AnyChromosome<NetworkAllele> chromosome = AnyChromosome.of(supplier, alleleValidator,
                alleleSeqValidator, networkDescription.getStorage().getSize());

        initializeChromosome(chromosome);

        return Genotype.of(chromosome);
    }

    private void initializeChromosome(AnyChromosome<NetworkAllele> chromosome) {
        Random random = new Random();
        List<Integer> positions = new ArrayList<>(networkDescription.getEmptyPosition().size());

        int geneNumber, devicePosition, indexOfDevice = 0;
        int deviceCount = random.nextInt(networkDescription.getEmptyPosition().size() - networkDescription.getMinDeviceCount() + 1)
                + networkDescription.getMinDeviceCount();

        while (indexOfDevice != deviceCount) {
            geneNumber = random.nextInt(chromosome.length());
            devicePosition = random.nextInt(networkDescription.getEmptyPosition().size());
            if (chromosome.getGene(geneNumber).getAllele().isSet()) {

                if (positions.get(devicePosition) == 1) {
                    for (int i = 0; i < networkDescription.getEmptyPosition().size(); i++) {
                        if (positions.get(i) == 0) {
                            devicePosition = i;
                            break;
                        }
                    }
                }

                chromosome.getGene(geneNumber).getAllele().isSet = true;
                chromosome.getGene(geneNumber).getAllele().position = devicePosition;
                positions.set(devicePosition, 1);
                indexOfDevice++;
            }
        }
    }
}

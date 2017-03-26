/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.ga.operators.configuration.factory;

import core.ga.operators.configuration.NetworkAllele;
import core.ga.operators.configuration.suppliers.NetworkSupplier;
import core.ga.operators.configuration.validators.NetworkAlleleValidator;
import core.ga.operators.configuration.validators.NetworkValidator;
import core.model.network.NetworkDescription;
import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.util.Factory;

import java.util.*;

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

        //initializeChromosome(chromosome);

        return Genotype.of(chromosome);
    }
    /*
    private void initializeChromosome(AnyChromosome<NetworkAllele> chromosome) {
        Random random = new Random();
        int emptyPositionsSize = networkDescription.getEmptyPosition().size();

        double deviceProbability;
        double deviceProbabilityBorder = (double) networkDescription.getMinDeviceCount() / (double) emptyPositionsSize;
        int devicePosition;
        for (int i = 0; i < chromosome.length(); i++) {
            deviceProbability = random.nextDouble();
            if (deviceProbability < deviceProbabilityBorder) {
                devicePosition = random.nextInt(emptyPositionsSize);
                chromosome.getGene(i).getAllele().isSet = true;
                chromosome.getGene(i).getAllele().position = networkDescription.getEmptyPosition().get(devicePosition);
            }
        }
    }

    private void initializeChromosome(AnyChromosome<NetworkAllele> chromosome) {
        Random random = new Random();
        int emptyPositionsSize = networkDescription.getEmptyPosition().size();
        boolean[] positions = new boolean[emptyPositionsSize];
        Arrays.fill(positions, false);

        int geneNumber, devicePosition, indexOfDevice = 0;
        int deviceCount = random.nextInt(emptyPositionsSize - networkDescription.getMinDeviceCount() + 1)
                + networkDescription.getMinDeviceCount();

        while (indexOfDevice != deviceCount) {
            geneNumber = random.nextInt(chromosome.length());
            devicePosition = random.nextInt(emptyPositionsSize);
            if (!chromosome.getGene(geneNumber).getAllele().isSet()) {

                if (positions[devicePosition]) {
                    for (int i = 0; i < emptyPositionsSize; i++) {
                        if (!positions[i]) {
                            devicePosition = i;
                            break;
                        }
                    }
                }

                chromosome.getGene(geneNumber).getAllele().isSet = true;
                chromosome.getGene(geneNumber).getAllele().position = networkDescription.getEmptyPosition().get(devicePosition);
                positions[devicePosition] = true;
                indexOfDevice++;
            }
        }
    }*/
}

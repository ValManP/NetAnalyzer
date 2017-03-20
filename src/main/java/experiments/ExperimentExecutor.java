/**
 * Created by Valerii Pozdiaev on 2017.
 */
package experiments;

import core.model.inventory.AbstractStorage;
import core.model.inventory.impl.networkelement.RootNetworkElement;
import core.model.inventory.impl.networkelement.SwitchNetworkElement;
import core.model.inventory.impl.networkelement.UserNetworkElement;
import core.model.inventory.impl.storage.DoubleStorage;
import core.model.network.AbstractNetwork;
import core.model.network.impl.DoubleLink;
import core.model.network.impl.DoubleNetwork;

import java.util.Random;

public class ExperimentExecutor {
    // TODO
    // Параметры задачи: размер сети, размер хранилища
    // Изменяемые характеристики алгоритма:
    // Размер популяции, параметры кроссовера, мутатора и селектора
    // Параметры для исследования: время работы, значение фитнесс-функции у лучшей особи

    private int storageSize = 30;
    private int networkSize = 20;
    private double minCapacity = 60, maxCapacity = 140, minPrice = 50, maxPrice = 120;
    private AbstractNetwork network;
    private AbstractStorage storage;

    public ExperimentExecutor() {
        network = createNetwork(networkSize);
        storage = createStorage(storageSize, minCapacity, maxCapacity, minPrice, maxPrice);
    }

    private AbstractStorage createStorage(int storageSize, double minCapacity, double maxCapacity,
                                            double minPrice, double maxPrice) {
        AbstractStorage storage = new DoubleStorage();
        Random random = new Random();

        for (int i = 0; i < storageSize; ++i) {
            storage.addElement(new SwitchNetworkElement<>((maxCapacity - minCapacity) * random.nextDouble() + minCapacity,
                    (maxPrice - minPrice) * random.nextDouble() + minPrice));
        }

        return storage;
    }

    private DoubleNetwork createNetwork(int size) {
        DoubleNetwork network = new DoubleNetwork(size);
        network.addRoot(new RootNetworkElement<>(130.0), 0);
        network.addRoot(new RootNetworkElement<>(120.0), 1);
        network.addRoot(new RootNetworkElement<>(150.0), 2);

        network.addUser(new UserNetworkElement<>(40.0, 60.0), 3);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 4);
        network.addUser(new UserNetworkElement<>(40.0, 60.0), 5);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 6);
        network.addUser(new UserNetworkElement<>(40.0, 60.0), 7);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 8);
        network.addUser(new UserNetworkElement<>(40.0, 60.0), 9);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 10);
        network.addUser(new UserNetworkElement<>(30.0, 50.0), 11);


        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 12);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 0, 14);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 1, 13);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 2, 20);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 12, 3);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 4);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 16);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 13, 15);
        network.addLink(new DoubleLink(true, 100.0, 20.0, 0.0), 13, 14);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 15, 5);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 15, 6);
        network.addLink(new DoubleLink(true, 100.0, 20.0, 0.0), 15, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 16, 7);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 16, 8);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 20, 16);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 17, 19);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 17);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 14, 18);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 9);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 18, 10);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 19, 10);
        network.addLink(new DoubleLink(false, 100.0, 20.0, 0.0), 19, 11);

        return network;
    }
}

package core.model.controllers;

import core.model.inventory.impl.storage.Device;
import core.model.inventory.impl.storage.DoubleStorage;

import java.util.Random;

public class StorageController {
    private static StorageController instance;

    public static StorageController getInstance() {
        if (instance == null) {
            instance = new StorageController();
        }
        return instance;
    }

    public void generateStorage(DoubleStorage storage, int storageSize, double minCapacity, double maxCapacity,
                                double minPrice, double maxPrice) {
        Random random = new Random();

        for (int i = 0; i < storageSize; ++i) {
            storage.addElement(new Device("device#" + i, (maxCapacity - minCapacity) * random.nextDouble() + minCapacity,
                    (maxPrice - minPrice) * random.nextDouble() + minPrice));
        }
    } 
}

/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory.impl.storage;

import core.model.inventory.AbstractStorage;
import core.model.inventory.NetworkElement;

import java.util.List;

public class DoubleStorage extends AbstractStorage<Device> {
    public DoubleStorage() {
        super();
    }

    public DoubleStorage(List<Device> elements) {
        super(elements);
    }
}

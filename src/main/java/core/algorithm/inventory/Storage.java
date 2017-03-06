/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.algorithm.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Storage {
    private List<NetworkElement<Double>> elements;

    public Storage() {
        elements = new ArrayList<>();
    }

    public Storage(List<NetworkElement<Double>> elements) {
        this.elements = elements;
    }

    public void addElement(NetworkElement<Double> element) {
        elements.add(element);
    }

    public void addElements(Collection<NetworkElement<Double>> elementCollection) {
        elements.addAll(elementCollection);
    }

    public NetworkElement<Double> getElement(int position) {
        return elements.get(position);
    }

    public int getElementPosition(NetworkElement<Double> element) {
        return elements.indexOf(element);
    }

    public void removeElement(NetworkElement<Double> element){
        elements.remove(element);
    }

    public void removeElement(int position){
        elements.remove(position);
    }

    public void setElement(NetworkElement<Double> element, int position){
        elements.set(position, element);
    }

    public int getSize() {
        return elements.size();
    }
}

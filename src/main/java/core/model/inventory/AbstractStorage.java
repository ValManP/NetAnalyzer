/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.model.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractStorage<N> {
    protected List<N> elements;

    public AbstractStorage() {
        elements = new ArrayList<>();
    }

    public AbstractStorage(List<N> elements) {
        this.elements = elements;
    }

    public void addElement(N element) {
        elements.add(element);
    }

    public void addElements(Collection<N> elementCollection) {
        elements.addAll(elementCollection);
    }

    public N getElement(int position) {
        return elements.get(position);
    }

    public int getElementPosition(N element) {
        return elements.indexOf(element);
    }

    public void removeElement(N element){
        elements.remove(element);
    }

    public void removeElement(int position){
        elements.remove(position);
    }

    public void setElement(N element, int position){
        elements.set(position, element);
    }

    public int getSize() {
        return elements.size();
    }
}

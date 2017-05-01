package core.model.network;

import core.utils.CommonTool;

import java.util.List;

public abstract class AbstractNetwork<TrafficType, NetworkElement> {
    protected int size;
    protected AbstractLink<TrafficType>[][] linksMatrix;
    protected List<NetworkElement> networkElements;
    protected List<NetworkElement> roots;
    protected List<NetworkElement> switches;
    protected List<NetworkElement> users;

    public void addRoot(NetworkElement root, int position) {
        roots.add(root);
        networkElements.set(position, root);
    }

    public void addUser(NetworkElement user, int position) {
        users.add(user);
        networkElements.set(position, user);
    }

    public void addSwitch(NetworkElement hub, int position) {
        switches.add(hub);
        networkElements.set(position, hub);
    }

    public void addEmptyElement(NetworkElement hub, int position) {
        networkElements.set(position, hub);
    }

    public void removeRoot(NetworkElement root) {
        roots.remove(root);
    }

    public void removeUser(NetworkElement user) {
        users.remove(user);
    }

    public void removeSwitch(NetworkElement hub) {
        switches.remove(hub);
    }

    public int getNEPosition(NetworkElement networkElement) {
        return networkElements.indexOf(networkElement);
    }

    public int getNetworkElementsCount() {
        return networkElements.size();
    }

    public TrafficType getTraffic(int source, int target) {
        if (CommonTool.isBelong(source, size)
                && CommonTool.isBelong(target, size)
                && CommonTool.isExist(linksMatrix[source][target])) {
            return linksMatrix[source][target].getTraffic();
        }
        return null;
    }

    public void setTraffic(int source, int target, TrafficType traffic) {
        if (CommonTool.isBelong(source, size)
                && CommonTool.isBelong(target, size)
                && CommonTool.isExist(linksMatrix[source][target])) {
            linksMatrix[source][target].setTraffic(traffic);
        }
    }

    public int getSize() {
        return size;
    }

    public void addLink(AbstractLink<TrafficType> link, int source, int target) {
        if (CommonTool.isBelong(source, size) && CommonTool.isBelong(target, size)) {
            linksMatrix[source][target] = link;
            if (link.isDuplex()) {
                linksMatrix[target][source] = link;
            }
        }
    }

    public boolean isLinkExist(int source, int target) {
        return CommonTool.isBelong(source, size) && CommonTool.isBelong(target, size) && CommonTool.isExist(linksMatrix[source][target]);
    }

    public NetworkElement getNetworkElement(int num) {
        return networkElements.get(num);
    }

    public List<NetworkElement> getNetworkElements() {
        return networkElements;
    }

    public List<NetworkElement> getRoots() {
        return roots;
    }

    public List<NetworkElement> getSwitches() {
        return switches;
    }

    public List<NetworkElement> getUsers() {
        return users;
    }

    public AbstractLink<TrafficType>[][] getLinksMatrix() {
        return linksMatrix;
    }

    public abstract List<Integer> getEmptyPosition();
}

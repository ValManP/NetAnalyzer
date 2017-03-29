package core.ga.operators.configuration;

public class NetworkAllele {
    public boolean isSet;
    public int position;

    public NetworkAllele(boolean isSet, int position) {
        this.isSet = isSet;
        this.position = position;
    }

    public static int getPosition(NetworkAllele allele) {
        return allele.getPosition();
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

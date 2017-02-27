/**
 * Created by Valerii Pozdiaev on 2017.
 */
package tools;

public class CommonTool {
    public static boolean isBelong(int x, int rangeFrom, int rangeTo) {
        return (x >= rangeFrom && x <= rangeTo);
    }

    public static boolean isBelong(int x, int rangeTo) {
        return isBelong(x, 0, rangeTo);
    }

    public static boolean isExist(Object object) {
        return !(object == null);
    }
}

package core.utils;

public class CommonTool {
    static boolean isBelong(int x, int rangeFrom, int rangeTo) {
        return (x >= rangeFrom && x <= rangeTo);
    }

    public static boolean isBelong(int x, int rangeTo) {
        return isBelong(x, 0, rangeTo);
    }

    public static boolean isExist(Object object) {
        return !(object == null);
    }

    public static boolean isNotNull(String str) {
        return !"".equals(str) && isExist(str);
    }

    public static boolean isNull(String str) {
        return "".equals(str) || !isExist(str);
    }
}

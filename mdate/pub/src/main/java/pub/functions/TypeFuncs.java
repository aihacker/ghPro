package pub.functions;

import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @time 17:29:51
 * @since 2008-12-19
 */
public class TypeFuncs {

    public static boolean isList(Class cls) {
        for (Class itf : cls.getInterfaces()) {
            if (itf.equals(List.class)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isJavaType(Class cls) {
        return cls.getName().startsWith("java");
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException nFE) {
            return false;
        }
    }

}

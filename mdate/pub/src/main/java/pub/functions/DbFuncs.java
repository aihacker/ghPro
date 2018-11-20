package pub.functions;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-5-10
 */
public class DbFuncs {

    public static void tryClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception e) {
                // do nothing
            }
        }
    }

}

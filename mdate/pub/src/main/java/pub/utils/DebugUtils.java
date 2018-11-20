package pub.utils;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2015/7/17
 */
public class DebugUtils {

    public static void Sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

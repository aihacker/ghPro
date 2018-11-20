package pub.types.support;

import pub.functions.DateFuncs;
import pub.types.Formatter;

import java.util.Date;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-29
 */
public class DateFormatter implements Formatter {

    @Override
    public String format(Object obj) {
        if(obj == null) {
            return "";
        }
        Date date= (Date) obj;
        return DateFuncs.toDateString(date);
    }

}

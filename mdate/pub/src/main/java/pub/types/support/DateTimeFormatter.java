package pub.types.support;

import pub.functions.DateFuncs;
import pub.types.Formatter;

import java.util.Date;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-29
 */
public class DateTimeFormatter implements Formatter {

    @Override
    public String format(Object obj) {
        if(obj == null) {
            return "";
        }
        Date date= (Date) obj;
        return DateFuncs.toString(date);
    }

}

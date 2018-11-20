package pub.types.support;

import pub.types.Formatter;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-29
 */
public class BooleanFormatter implements Formatter {

    public static Formatter instance = new BooleanFormatter();

    private String trueStr = "是";
    private String falseStr = "否";

    public BooleanFormatter() {
    }

    public BooleanFormatter(String trueStr, String falseStr) {
        this.trueStr = trueStr;
        this.falseStr = falseStr;
    }

    @Override
    public String format(Object obj) {
        if(obj == null) {
            return "";
        }
        if(obj instanceof Boolean) {
            return ((boolean)obj)? trueStr: falseStr;
        }
        return obj.toString().equals("1")? trueStr: falseStr;
    }


}

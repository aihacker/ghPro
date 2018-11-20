package pub.system.validation.model;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-15
 */
public class RuleInfo {

    public String name;
    public Object param;
    public String message;

    public RuleInfo() {
    }

    public RuleInfo(String name, Object param) {
        this(name, param, null);
    }

    public RuleInfo(String name, Object param, String message) {
        this.name = name;
        this.param = param;
        this.message = message;
    }


}

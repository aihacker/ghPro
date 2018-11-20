package pub.system.validation.model;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-16
 */
public class FieldInfo {

    private String name;
    private String desc;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}

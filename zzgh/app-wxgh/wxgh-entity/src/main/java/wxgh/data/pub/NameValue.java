package wxgh.data.pub;

/**
 * Created by Administrator on 2017/8/3.
 */
public class NameValue {

    private String name;
    private String value;

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public NameValue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

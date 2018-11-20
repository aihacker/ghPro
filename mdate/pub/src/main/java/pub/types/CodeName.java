package pub.types;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-3-29
 */
public class CodeName implements Serializable {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

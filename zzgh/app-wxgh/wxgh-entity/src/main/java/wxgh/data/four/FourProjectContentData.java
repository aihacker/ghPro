package wxgh.data.four;

import java.io.Serializable;

/**
 * Created by WIN on 2016/8/29.
 */
public class FourProjectContentData implements Serializable {

    private String name;
    private Integer fpId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }
}

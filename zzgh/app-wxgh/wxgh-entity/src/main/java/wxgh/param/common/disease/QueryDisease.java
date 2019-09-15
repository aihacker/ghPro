package wxgh.param.common.disease;

import java.io.Serializable;

/**
 * Created by WIN on 2016/9/5.
 */
public class QueryDisease implements Serializable {
    private Integer id;
    private Integer applyId;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
}

package wxgh.param.common.act;

import java.io.Serializable;

/**
 * Created by ✔ on 2016/10/31.
 */
public class QueryActRegular implements Serializable {

    private Integer id;
    private Integer aid;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

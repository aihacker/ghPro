package wxgh.data.union.innovation.work;

import java.io.Serializable;

/**
 * Created by WIN on 2016/8/26.
 */
public class JiShenheData implements Serializable {

    private Integer id;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

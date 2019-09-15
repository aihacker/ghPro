package wxgh.query.tribe;

import pub.bean.SimplePage;

import java.io.Serializable;

/**
 * @author hhl
 * @create 2017-08-04
 **/
public class TribeActQuery extends SimplePage implements Serializable {
    private Integer id;
    private Integer Status;
    private Boolean zhongchou;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Boolean getZhongchou() {
        return zhongchou;
    }

    public void setZhongchou(Boolean zhongchou) {
        this.zhongchou = zhongchou;
    }
}

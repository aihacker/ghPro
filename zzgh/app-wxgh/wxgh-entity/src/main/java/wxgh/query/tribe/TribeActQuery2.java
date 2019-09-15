package wxgh.query.tribe;


import pub.dao.page.Page;
import java.io.Serializable;

/**
 * @author hhl
 * @create 2017-08-04
 **/
public class TribeActQuery2 extends Page implements Serializable {
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

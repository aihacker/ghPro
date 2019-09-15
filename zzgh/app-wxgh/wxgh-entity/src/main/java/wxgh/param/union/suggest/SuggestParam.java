package wxgh.param.union.suggest;

import pub.bean.Page;

import javax.persistence.Entity;

/**
 * @author hhl
 * @create 2017-08-15
 **/
@Entity
public class SuggestParam extends pub.dao.page.Page{
    private Integer cateId;
    private Integer status;

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

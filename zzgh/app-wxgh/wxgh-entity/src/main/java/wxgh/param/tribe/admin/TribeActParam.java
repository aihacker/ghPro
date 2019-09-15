package wxgh.param.tribe.admin;


import pub.dao.page.Page;

/**
 * Created by cby on 2017/8/22.
 */
public class TribeActParam extends Page {
    private Integer id;
    private Integer status;
    private Boolean zhongchou;

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

    public Boolean getZhongchou() {
        return zhongchou;
    }

    public void setZhongchou(Boolean zhongchou) {
        this.zhongchou = zhongchou;
    }
}

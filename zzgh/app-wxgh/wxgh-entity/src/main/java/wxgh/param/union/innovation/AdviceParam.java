package wxgh.param.union.innovation;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/6/7.
 */
public class AdviceParam extends Page {

    private Integer nodeptid;
    private Integer status;

    public Integer getNodeptid() {
        return nodeptid;
    }

    public void setNodeptid(Integer nodeptid) {
        this.nodeptid = nodeptid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

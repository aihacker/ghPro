package wxgh.param.entertain.sport;

import pub.dao.page.Page;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：8:53
 * version：V1.0
 * Description：
 */
public class SportActParam extends Page {

    private Integer status;
    private Integer deptid;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}

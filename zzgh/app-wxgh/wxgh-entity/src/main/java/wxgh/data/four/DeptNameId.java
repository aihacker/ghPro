package wxgh.data.four;

import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2016/12/12
 */
public class DeptNameId implements Serializable {

    private Integer deptid;
    private String deptname;

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}

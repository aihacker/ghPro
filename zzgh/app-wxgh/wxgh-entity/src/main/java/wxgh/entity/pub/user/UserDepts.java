package wxgh.entity.pub.user;

/**
 * Created by Administrator on 2017/8/1.
 */
public class UserDepts {

    private Integer oldDeptid;
    private Integer newDeptid;
    private String depts;

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }

    public Integer getOldDeptid() {
        return oldDeptid;
    }

    public void setOldDeptid(Integer oldDeptid) {
        this.oldDeptid = oldDeptid;
    }

    public Integer getNewDeptid() {
        return newDeptid;
    }

    public void setNewDeptid(Integer newDeptid) {
        this.newDeptid = newDeptid;
    }
}

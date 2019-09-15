package wxgh.param.union.user.excel;
/**
 * Created by DELL on 2018/5/25.
 *
 */
public class UserExcelParam {
    private String userid;
    private Integer deptid;
    private Integer mid;

    public String getUserid() {
        return userid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}

package wxgh.data.entertain.sport;

/**
 * Created by Administrator on 2017/8/25.
 */
public class UserSport {

    private String userid;
    private String username;
    private String avatar;
    private String deptname;
    private Integer stepCount;
    private Integer paiming;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public Integer getPaiming() {
        return paiming;
    }

    public void setPaiming(Integer paiming) {
        this.paiming = paiming;
    }
}

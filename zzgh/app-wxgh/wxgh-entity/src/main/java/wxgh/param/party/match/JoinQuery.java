package wxgh.param.party.match;

/**
 * Created by Administrator on 2017/6/7.
 */
public class JoinQuery {

    private Integer mid;
    private Integer status;
    private Integer type;
    private String userid;

    private String curUserid;

    public String getCurUserid() {
        return curUserid;
    }

    public void setCurUserid(String curUserid) {
        this.curUserid = curUserid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

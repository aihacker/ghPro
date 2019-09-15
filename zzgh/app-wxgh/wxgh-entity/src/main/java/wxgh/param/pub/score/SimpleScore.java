package wxgh.param.pub.score;

/**
 * Created by Administrator on 2017/7/31.
 */
public class SimpleScore {

    private String userid;
    private Float score;
    private Integer status;
    private String info;
    private String byId;

    public String getById() {
        return byId;
    }

    public void setById(String byId) {
        this.byId = byId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

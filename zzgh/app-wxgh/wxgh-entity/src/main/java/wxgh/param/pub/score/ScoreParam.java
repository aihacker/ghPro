package wxgh.param.pub.score;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/17.
 */
public class ScoreParam extends Page {

    private ScoreGroup group;
    private ScoreType type;
    private String userid;
    private Integer status;
    private String byId;
    private String inTypes;
    private String notTypes;

    public ScoreGroup getGroup() {
        return group;
    }

    public void setGroup(ScoreGroup group) {
        this.group = group;
    }

    public ScoreType getType() {
        return type;
    }

    public void setType(ScoreType type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getById() {
        return byId;
    }

    public void setById(String byId) {
        this.byId = byId;
    }

    public String getInTypes() {
        return inTypes;
    }

    public void setInTypes(String inTypes) {
        this.inTypes = inTypes;
    }

    public String getNotTypes() {
        return notTypes;
    }

    public void setNotTypes(String notTypes) {
        this.notTypes = notTypes;
    }
}

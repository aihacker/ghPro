package wxgh.param.common.act;


import pub.bean.Page;

/**
 * Created by Administrator on 2017/5/11.
 */
public class SignQuery extends Page {

    private Integer actId;
    private Integer actType;
    private String userid;
    private Integer status;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
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
}

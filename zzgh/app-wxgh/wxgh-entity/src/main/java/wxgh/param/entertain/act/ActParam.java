package wxgh.param.entertain.act;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/24.
 */
public class ActParam extends Page {

    private Integer actType;
    private Integer status;
    private String groupId;
    private Integer regular;
    private String userid;
    private Integer id;
    private String actId;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Boolean limit;

    public Boolean getLimit() {
        return limit;
    }

    public void setLimit(Boolean limit) {
        this.limit = limit;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ActParam{" +
                "actType=" + actType +
                ", status=" + status +
                ", groupId='" + groupId + '\'' +
                ", regular=" + regular +
                ", userid='" + userid + '\'' +
                ", id=" + id +
                ", actId='" + actId + '\'' +
                ", limit=" + limit +
                '}';
    }
}

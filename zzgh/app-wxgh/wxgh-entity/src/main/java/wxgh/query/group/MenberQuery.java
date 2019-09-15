package wxgh.query.group;

import pub.dao.page.Page;

import java.io.Serializable;

/**
 * @author hhl
 * @create 2017-08-31
 **/
public class MenberQuery extends Page implements Serializable {

    private String userid;
    private String groupId;
    private Integer status = 1;
    private Boolean groupBy;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MenberQuery() {
    }

    public Boolean getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(Boolean groupBy) {
        this.groupBy = groupBy;
    }

    public MenberQuery(String userid, String groupId) {
        this.userid = userid;
        this.groupId = groupId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

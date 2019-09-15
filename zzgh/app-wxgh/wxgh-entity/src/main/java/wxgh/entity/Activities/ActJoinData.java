package wxgh.entity.Activities;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hhl
 * @create 2017-08-22
 **/
public class ActJoinData implements Serializable {

    private Integer id;
    private String userid;
    private String username;
    private Integer joinType;
    private String avatar;
    private Date joinTime;
    private Integer scId;

    private String joinTimeStr;
    private String deptname;

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getJoinTimeStr() {
        return joinTimeStr;
    }

    public void setJoinTimeStr(String joinTimeStr) {
        this.joinTimeStr = joinTimeStr;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}


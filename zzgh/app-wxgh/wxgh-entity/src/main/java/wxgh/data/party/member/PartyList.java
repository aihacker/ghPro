package wxgh.data.party.member;

import java.util.Date;

/**
 * Created by Sheng on 2017/9/5.
 */
public class PartyList {
    private Integer id;
    private String userid;
    private String avatar;
    private String username;
    private String moblie;
    private Date inTime;
    private Date zzTime;
    private String education;

    private Integer type;//1为创建人，2为管理员，3为成员
    private String groupId;
    private String partyWorker;
    private String deptName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getZzTime() {
        return zzTime;
    }

    public void setZzTime(Date zzTime) {
        this.zzTime = zzTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPartyWorker() {
        return partyWorker;
    }

    public void setPartyWorker(String partyWorker) {
        this.partyWorker = partyWorker;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}

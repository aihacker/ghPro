package wxgh.entity.party.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Sheng on 2017/9/5.
 */
@Entity
@Table(name = "t_party_dept_user")
public class PartyMember {

    private Integer id;
    private String userid;
    private Date inTime;
    private Date zzTime;
    private Integer branchId;
    private Integer partyId;
    private Date birthday;
    private Integer sex;
    private String username;
    private String mobile;
    private String education;
    private String partyWorker;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "in_time")
    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    @Column(name = "zz_time")
    public Date getZzTime() {
        return zzTime;
    }

    public void setZzTime(Date zzTime) {
        this.zzTime = zzTime;
    }

    @Column(name = "branch_id")
    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    @Column(name = "party_id")
    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    @Column
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Column(name = "party_worker")
    public String getPartyWorker() {
        return partyWorker;
    }

    public void setPartyWorker(String partyWorker) {
        this.partyWorker = partyWorker;
    }
}

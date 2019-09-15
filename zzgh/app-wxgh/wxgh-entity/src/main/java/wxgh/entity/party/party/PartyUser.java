package wxgh.entity.party.party;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

@Entity
@Table(name = " t_party_user ")
public class PartyUser implements Serializable {

    private Integer id;
    private String userId;
    private String password;
    private float scores;//积分
    private Integer groupId;//党组织ID
    private String nativePlace;//籍贯
    private String nation;//民族
    private String sex;
    private String birthday;
    private String identificationNum;//身份证号
    private String inDate;//入职时间
    private Integer isRepublican;//1.正式党员 2，预备党员
    private String zhuanzhengDate;//转正时间
    private String education;//学历
    private String company;//工作单位
    private String workDate;//工作时间
    private String retiredDate;//退休时间
    private String familyPlace;//户口所在地
    private String ccpdepart;//区内党支部
    private String avatarURL;//个人相片地址
    private String position;//党内职务
    private Integer isNew;//是否为新调度党员
    private String remark;//备注

    private String groupName;
    private String mobile;
    private String username;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private List<String> results;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "avatar_url")
    public String getAvatarURL() {
        return avatarURL;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "userid")
    public String getUserId() {
        return userId;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "scores")
    public float getScores() {
        return scores;
    }

    @Column(name = "groupid")
    public Integer getGroupId() {
        return groupId;
    }

    @Column(name = "native_place")
    public String getNativePlace() {
        return nativePlace;
    }

    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    @Column(name = "birthday")
    public String getBirthday() {
        return birthday;
    }

    @Column(name = "in_date")
    public String getInDate() {
        return inDate;
    }

    @Column(name = "zhuanzheng_date")
    public String getZhuanzhengDate() {
        return zhuanzhengDate;
    }

    @Column(name = "work_date")
    public String getWorkDate() {
        return workDate;
    }

    @Column(name = "retired_date")
    public String getRetiredDate() {
        return retiredDate;
    }
    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    @Column(name = "identification_num")
    public String getIdentificationNum() {
        return identificationNum;
    }

    @Column(name = "is_republican")
    public Integer getIsRepublican() {
        return isRepublican;
    }

    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    @Column(name = "family_place")
    public String getFamilyPlace() {
        return familyPlace;
    }

    @Column(name = "ccpdepart")
    public String getCcpdepart() {
        return ccpdepart;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    @Column(name = "is_new")
    public Integer getIsNew() {
        return isNew;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScores(float scores) {
        this.scores = scores;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void setIdentificationNum(String identificationNum) {
        this.identificationNum = identificationNum;
    }



    public void setIsRepublican(Integer isRepublican) {
        this.isRepublican = isRepublican;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public void setFamilyPlace(String familyPlace) {
        this.familyPlace = familyPlace;
    }

    public void setCcpdepart(String ccpdepart) {
        this.ccpdepart = ccpdepart;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setZhuanzhengDate(String zhuanzhengDate) {
        this.zhuanzhengDate = zhuanzhengDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public void setRetiredDate(String retiredDate) {
        this.retiredDate = retiredDate;
    }
}

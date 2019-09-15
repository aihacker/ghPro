package wxgh.entity.pub;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:27
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_user_info")
public class UserInfo {

    private Integer infoId;
    private String userId;
    private Date birth;
    private String address; //出生地址
    private String nation; //民族
    private Float salary; //工资
    private String politics; //政治面貌
    private String education; //学历
    private String workUnit; //工作单位
    private String job; //工作职务
    private String nativePlace; //籍贯
    private String resume; //个人简历
    private String idea; //管理员审核意见
    private String remark; //备注
    private String photo; //个人图片保存路径
    private Date addTime; //添加时间
    private String idcard; //身份证号码
    private User user;

    private String birthStr;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "birth")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "nation")
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "salary")
    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Column(name = "politics")
    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    @Column(name = "education")
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Column(name = "work_unit")
    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Column(name = "native_place")
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    @Column(name = "resume")
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Column(name = "idea")
    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "idcard")
    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBirthStr() {
        return birthStr;
    }

    public void setBirthStr(String birthStr) {
        this.birthStr = birthStr;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "infoId=" + infoId +
                ", userId='" + userId + '\'' +
                ", birth=" + birth +
                ", address='" + address + '\'' +
                ", nation='" + nation + '\'' +
                ", salary=" + salary +
                ", politics='" + politics + '\'' +
                ", education='" + education + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", job='" + job + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", resume='" + resume + '\'' +
                ", idea='" + idea + '\'' +
                ", remark='" + remark + '\'' +
                ", photo='" + photo + '\'' +
                ", addTime=" + addTime +
                ", idcard='" + idcard + '\'' +
                ", user=" + user +
                ", birthStr='" + birthStr + '\'' +
                '}';
    }
}


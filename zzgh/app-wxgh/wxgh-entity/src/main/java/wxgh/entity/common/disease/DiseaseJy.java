package wxgh.entity.common.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 教育资助
 * <p>
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Entity
@Table(name = "t_disease_jy")
public class DiseaseJy implements Serializable {

    private Integer id;
    private String relation; //关系
    private String name; //姓名
    private Date birth; //出生日期
    private String school; //就读学校
    private String grade; //班级
    private String xuezhi; //学制
    private Float tuition; //学年费用
    private String reason; //申请理由
    private Date addTime; //添加时间
    private String userid; //用户ID
    private Integer applyId; //申请ID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "relation")
    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "birth")
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Column(name = "school")
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Column(name = "grade")
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Column(name = "xuezhi")
    public String getXuezhi() {
        return xuezhi;
    }

    public void setXuezhi(String xuezhi) {
        this.xuezhi = xuezhi;
    }

    @Column(name = "tuition")
    public Float getTuition() {
        return tuition;
    }

    public void setTuition(Float tuition) {
        this.tuition = tuition;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "apply_id")
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
}

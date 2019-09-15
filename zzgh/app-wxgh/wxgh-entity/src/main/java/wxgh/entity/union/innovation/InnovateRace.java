package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_innovate_race")
public class InnovateRace implements Serializable {

    public static final Integer TYPE_LABOUR = 1; //劳动竞赛
    public static final Integer TYPE_SKILL = 2; //技能竞赛

    private Integer id;
    private Integer raceType;
    private Integer deptId;
    private Integer applyId;
    private String raceName;
    private Integer status;
    private Integer type;
    private Date createTime;

    private String auditIdea;
    private Date auditTime;
    private String content;
    private Integer applyStatus;
    private String deptname;
    private String txt;
    private List<String> imgList;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "race_type")
    public Integer getRaceType() {
        return raceType;
    }

    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    @Column(name = "apply_id")
    public Integer getApplyId() {
        return applyId;
    }

    @Column(name = "race_name")
    public String getRaceName() {
        return raceName;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRaceType(Integer raceType) {
        this.raceType = raceType;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

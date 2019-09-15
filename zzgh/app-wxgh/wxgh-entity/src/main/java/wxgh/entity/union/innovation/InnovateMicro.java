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

/**
 * Created by ✔ on 2016/11/10.
 */
@Entity
@Table(name = "t_innovate_micro")
public class InnovateMicro implements Serializable {

    private Integer id;
    private Integer type;
    private String name;
    private Integer deptid;
    private String cate1;
    private String cate2;
    private String point;
    private String behave;
    private Integer pid;
    private String team;
    private Date addTime;
    private Integer adviceId;

    private String imgs;
    private String txt;

    /**
     * @Author: ✔
     * @Description: plus
     * @Date: 9:16, 2016/11/16
     */
    private String auditIdea;
    private Date auditTime;
    private String content;
    private Integer applyStatus;
    private String deptname;
    private Float price;

    private List<String> imgList;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    @Column(name = "cate_1")
    public String getCate1() {
        return cate1;
    }

    @Column(name = "cate_2")
    public String getCate2() {
        return cate2;
    }

    @Column(name = "point")
    public String getPoint() {
        return point;
    }

    @Column(name = "behave")
    public String getBehave() {
        return behave;
    }

    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    @Column(name = "team")
    public String getTeam() {
        return team;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setCate1(String cate1) {
        this.cate1 = cate1;
    }

    public void setCate2(String cate2) {
        this.cate2 = cate2;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public void setBehave(String behave) {
        this.behave = behave;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Column(name = "advice_id")
    public Integer getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Integer adviceId) {
        this.adviceId = adviceId;
    }


    public static String getCate(Integer type) {
        switch (type) {
            case 1:
                return "技能";
            case 2:
                return "营销";
            case 3:
                return "服务";
            case 4:
                return "管理";
            default:
                return "其他";
        }
    }
}

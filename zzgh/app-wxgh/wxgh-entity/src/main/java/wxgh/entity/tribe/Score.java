package wxgh.entity.tribe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/9.
 */

@Entity
@Table(name = "t_score")
public class Score implements Serializable {

    public static final Integer SCORE_TYPE_USER = 1; //个人积分
    public static final Integer SCORE_TYPE_TRIBE = 2; //青年部落积分
    public static final Integer SCORE_TYPE_DI = 3; //纪检学习考试积分

    private Integer id;
    private String userId;
    private float score;
    private float sumScore;
    private Date addTime;
    private Integer addId;
    private String addType;
    private Integer status;
    private String groupStr;
    private Integer dateId;
    private String info;
    private Integer scoreType;

    private String avatar;
    private String theme;
    private String coverImg;

    private String username;
    private String deptname;
    private String phone;
    private float addScore;
    private Integer isCount;

    @Column(name = "iscount")
    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    @Column(name = "add_score")
    public float getAddScore() {
        return addScore;
    }

    public void setAddScore(float addScore) {
        this.addScore = addScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public float getSumScore() {
        return sumScore;
    }

    public void setSumScore(float sumScore) {
        this.sumScore = sumScore;
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

    @Column(name = "score")
    public float getScore() {
        return score;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "add_id")
    public Integer getAddId() {
        return addId;
    }

    @Column(name = "add_type")
    public String getAddType() {
        return addType;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "group_str")
    public String getGroupStr() {
        return groupStr;
    }

    @Column(name = "date_id")
    public Integer getDateId() {
        return dateId;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    @Column(name = "score_type")
    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setGroupStr(String groupStr) {
        this.groupStr = groupStr;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

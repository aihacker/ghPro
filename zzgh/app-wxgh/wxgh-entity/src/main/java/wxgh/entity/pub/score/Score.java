package wxgh.entity.pub.score;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/31.
 */
@Entity
@Table
public class Score implements Serializable {

    private Integer id;
    private String userid;
    private Float score;
    private Integer scoreGroup;
    private Integer scoreType;
    private Integer status;
    private String info;
    private Date addTime;
    private String byId;
    private Float money;
    private String actId;
    private String groupId;

    @Column(name="money")
    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Column(name="act_id")
    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    @Column(name="group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Column
    public Integer getScoreGroup() {
        return scoreGroup;
    }

    public void setScoreGroup(Integer scoreGroup) {
        this.scoreGroup = scoreGroup;
    }

    @Column
    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public String getById() {
        return byId;
    }

    public void setById(String byId) {
        this.byId = byId;
    }
}

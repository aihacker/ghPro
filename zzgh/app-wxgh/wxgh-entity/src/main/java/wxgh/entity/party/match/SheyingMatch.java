package wxgh.entity.party.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ✔ on 2017/4/24.
 */
@Entity
@Table(name = "t_sheying_match")
public class SheyingMatch implements Serializable {
    private Integer id;
    private String name;
    private String cover;
    private String content;
    private String rule;
    private String tel;
    private Integer type;
    private Date startTime;
    private Date endTime;
    private Date addTime;
    private Integer worksType;

    /**
     *@Author:  ✔
     *@Description:  附件字段
     *@Date:  10:52, 2017/4/24
     */
    private String statusStr;
    private Integer status;
    private Integer statusVal;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusVal() {
        return statusVal;
    }

    public void setStatusVal(Integer statusVal) {
        this.statusVal = statusVal;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "cover")
    public String getCover() {
        return cover;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "rule")
    public String getRule() {
        return rule;
    }

    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "works_type")
    public Integer getWorksType() {
        return worksType;
    }

    public void setWorksType(Integer worksType) {
        this.worksType = worksType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

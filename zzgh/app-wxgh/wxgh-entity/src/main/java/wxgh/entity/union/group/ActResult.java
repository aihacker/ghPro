package wxgh.entity.union.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/15.
 */
@Entity
@Table
public class ActResult implements Serializable {

    private Integer id;
    private String title;
    private String info;
    private String imgs;
    private String actId;
    private Integer status;
    private Date addTime;
    private Integer seeNumb;
    private Integer zanNumb;
    private Integer commNumb;
    private String userid;
    private String groupId;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    @Column
    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public Integer getSeeNumb() {
        return seeNumb;
    }

    public void setSeeNumb(Integer seeNumb) {
        this.seeNumb = seeNumb;
    }

    @Column
    public Integer getZanNumb() {
        return zanNumb;
    }

    public void setZanNumb(Integer zanNumb) {
        this.zanNumb = zanNumb;
    }

    @Column
    public Integer getCommNumb() {
        return commNumb;
    }

    public void setCommNumb(Integer commNumb) {
        this.commNumb = commNumb;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

package wxgh.entity.common.female;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:21
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_female_lesson_join")
public class FemaleLessonJoin implements Serializable {

    private Integer id;
    private String userid;
    private Integer fid;
    private Date addTime;

    /**
     *@Author:  ✔
     *@Description:  扩展
     *@Date:  11:23, 2017/5/10
     */
    private String username;
    private String deptname;
    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "fid")
    public Integer getFid() {
        return fid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

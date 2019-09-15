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
 * Created by ✔ on 2017/4/21.
 */
@Entity
@Table(name = "t_sheying_act_join")
public class SheyingActJoin implements Serializable {

    private Integer id;
    private Integer aid;
    private String userid;
    private Date addTime;
    private Integer status;

    /**
     * @Author: ✔
     * @Description: plus as
     * @Date: 11:00, 2017/4/21
     */
    private String username;
    private String avatar;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "aid")
    public Integer getAid() {
        return aid;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

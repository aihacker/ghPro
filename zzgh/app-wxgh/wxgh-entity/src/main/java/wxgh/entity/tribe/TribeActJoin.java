package wxgh.entity.tribe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hhl
 * @create 2017-08-04
 **/
@Entity
@Table(name = "t_tribe_act_join")
public class TribeActJoin implements Serializable {

    private Integer id;
    private Integer actId;
    private String userid;
    private Date joinTime;
    private Integer integral;
    private Integer status;

    /*plus*/
    private String name;
    private String avatar;
    private String deptname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "act_id")
    public Integer getActId() {
        return actId;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "join_time")
    public Date getJoinTime() {
        return joinTime;
    }

    @Column(name = "integral")
    public Integer getIntegral() {
        return integral;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}


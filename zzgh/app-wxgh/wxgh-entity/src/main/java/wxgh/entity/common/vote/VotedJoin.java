package wxgh.entity.common.vote;

import wxgh.entity.pub.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Admin on 2016/7/11.
 */
@Entity
@Table(name = "t_voted_join")
public class VotedJoin implements Serializable {

    private Integer id;
    //用户id
    private String userid;
    //投票时间
    private Date joinTime;
    //主题id
    private Integer votedId;
    //状态
    private Integer status;

    private Integer isdel;

    private Integer optionId;

    private User userInfo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "join_time")
    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    @Column(name = "voted_id")
    public Integer getVotedId() {
        return votedId;
    }

    public void setVotedId(Integer votedId) {
        this.votedId = votedId;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "isdel")
    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    @Column(name = "option_id")
    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}

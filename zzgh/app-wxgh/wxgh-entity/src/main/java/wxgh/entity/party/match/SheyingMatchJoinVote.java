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
 * Created by ✔ on 2017/4/25.
 */
@Entity
@Table(name = "t_sheying_match_join_vote")
public class SheyingMatchJoinVote implements Serializable {
    private Integer id;
    private Integer joinId;
    private String userid;
    private Date addTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "join_id")
    public Integer getJoinId() {
        return joinId;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

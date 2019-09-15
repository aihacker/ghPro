package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
@Entity
@Table(name = "t_race_user_score")
public class RaceUserScore implements Serializable {

    private Integer id;
    private String userid;
    private Float score;
    private Integer raceId;
    private Integer arrangeId;
    private Integer lunNum;
    private Date addTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Column(name = "score")
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Column(name = "race_id")
    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @Column(name = "arrange_id")
    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Column(name = "lun_num")
    public Integer getLunNum() {
        return lunNum;
    }

    public void setLunNum(Integer lunNum) {
        this.lunNum = lunNum;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

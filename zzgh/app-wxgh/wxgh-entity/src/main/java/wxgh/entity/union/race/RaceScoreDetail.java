package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/8.
 */
@Entity
@Table(name = "t_race_score_detail")
public class RaceScoreDetail implements Serializable {

    private Integer id;
    private Integer arrangeId;
    private Integer raceLun;
    private Integer ronda;
    private Float score;
    private String userid;
    private Date addTime;
    private Integer addGroup;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "arrange_id")
    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Column(name = "race_lun")
    public Integer getRaceLun() {
        return raceLun;
    }

    public void setRaceLun(Integer raceLun) {
        this.raceLun = raceLun;
    }

    @Column(name = "ronda")
    public Integer getRonda() {
        return ronda;
    }

    public void setRonda(Integer ronda) {
        this.ronda = ronda;
    }

    @Column(name = "score")
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "add_group")
    public Integer getAddGroup() {
        return addGroup;
    }

    public void setAddGroup(Integer addGroup) {
        this.addGroup = addGroup;
    }
}

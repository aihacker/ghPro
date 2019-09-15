package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/9.
 */
@Entity
@Table(name = "t_race_score")
public class RaceScore implements Serializable {

    private Integer id;
    private Integer raceId;
    private Integer lunNum;
    private Float rival1Score;
    private Float rival2Score;
    private Integer arrangeId;
    private Date addTime;
    private Integer rule;
    private Integer ronda;
    private Integer endIs;

    private String score1;
    private String score2;

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "race_id")
    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @Column(name = "lun_num")
    public Integer getLunNum() {
        return lunNum;
    }

    public void setLunNum(Integer lunNum) {
        this.lunNum = lunNum;
    }

    @Column(name = "rival1_score")
    public Float getRival1Score() {
        return rival1Score;
    }

    public void setRival1Score(Float rival1Score) {
        this.rival1Score = rival1Score;
    }

    @Column(name = "rival2_score")
    public Float getRival2Score() {
        return rival2Score;
    }

    public void setRival2Score(Float rival2Score) {
        this.rival2Score = rival2Score;
    }

    @Column(name = "arrange_id")
    public Integer getArrangeId() {
        return arrangeId;
    }

    public void setArrangeId(Integer arrangeId) {
        this.arrangeId = arrangeId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "rule")
    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    @Column(name = "ronda")
    public Integer getRonda() {
        return ronda;
    }

    public void setRonda(Integer ronda) {
        this.ronda = ronda;
    }

    @Column(name = "end_is")
    public Integer getEndIs() {
        return endIs;
    }

    public void setEndIs(Integer endIs) {
        this.endIs = endIs;
    }
}

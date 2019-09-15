package wxgh.entity.entertain.act;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/19.
 */
@Entity
@Table
public class ScoreRule implements Serializable {

    private Integer id;
    private String ruleId;
    private String name;
    private Float score;
    private Float leaveScore;
    private Float outScore;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Column
    public Float getLeaveScore() {
        return leaveScore;
    }

    public void setLeaveScore(Float leaveScore) {
        this.leaveScore = leaveScore;
    }

    @Column
    public Float getOutScore() {
        return outScore;
    }

    public void setOutScore(Float outScore) {
        this.outScore = outScore;
    }
}

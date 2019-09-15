package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/30.
 */
@Entity
@Table(name = "t_race_score_rule")
public class RaceScoreRule implements Serializable {

    private Integer id;
    private String jia;
    private String yi;
    private Float jiaWin;
    private Float jiaShu;
    private Float yiWin;
    private Float yiShu;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "jia")
    public String getJia() {
        return jia;
    }

    public void setJia(String jia) {
        this.jia = jia;
    }

    @Column(name = "yi")
    public String getYi() {
        return yi;
    }

    public void setYi(String yi) {
        this.yi = yi;
    }

    @Column(name = "jia_win")
    public Float getJiaWin() {
        return jiaWin;
    }

    public void setJiaWin(Float jiaWin) {
        this.jiaWin = jiaWin;
    }

    @Column(name = "jia_shu")
    public Float getJiaShu() {
        return jiaShu;
    }

    public void setJiaShu(Float jiaShu) {
        this.jiaShu = jiaShu;
    }

    @Column(name = "yi_win")
    public Float getYiWin() {
        return yiWin;
    }

    public void setYiWin(Float yiWin) {
        this.yiWin = yiWin;
    }

    @Column(name = "yi_shu")
    public Float getYiShu() {
        return yiShu;
    }

    public void setYiShu(Float yiShu) {
        this.yiShu = yiShu;
    }
}

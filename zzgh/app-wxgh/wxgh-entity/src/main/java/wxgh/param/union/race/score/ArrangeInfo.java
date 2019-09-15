package wxgh.param.union.race.score;

/**
 * Created by Administrator on 2017/3/10.
 */
public class ArrangeInfo {

    private Integer id;
    private Integer lun; //当前轮数
    private Float score1; //比分
    private Float score2; //比分
    private Integer ronda; //当前进行到第几局
    private boolean end; //是否已经结束该轮比赛

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLun() {
        return lun;
    }

    public void setLun(Integer lun) {
        this.lun = lun;
    }

    public Float getScore1() {
        return score1;
    }

    public void setScore1(Float score1) {
        this.score1 = score1;
    }

    public Float getScore2() {
        return score2;
    }

    public void setScore2(Float score2) {
        this.score2 = score2;
    }

    public Integer getRonda() {
        return ronda;
    }

    public void setRonda(Integer ronda) {
        this.ronda = ronda;
    }
}

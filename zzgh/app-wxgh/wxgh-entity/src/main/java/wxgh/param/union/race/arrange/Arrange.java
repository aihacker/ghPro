package wxgh.param.union.race.arrange;

/**
 * Created by Administrator on 2017/3/6.
 */
public class Arrange {

    private Integer rival1; //对手1
    private Integer rival2; //对手2

    public Integer getRival1() {
        return rival1;
    }

    public void setRival1(Integer rival1) {
        this.rival1 = rival1;
    }

    public Integer getRival2() {
        return rival2;
    }

    public void setRival2(Integer rival2) {
        this.rival2 = rival2;
    }

    @Override
    public String toString() {
        return rival1 + " ---- " + rival2;
    }
}

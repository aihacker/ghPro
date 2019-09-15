package wxgh.param.pub.score;

/**
 * Created by Administrator on 2017/7/31.
 */
public enum ScoreGroup {

    USER(1), //工会积分
    TRIBE(2), //青年部落积分
    DI(3); //纪检积分

    private Integer group;

    ScoreGroup(Integer group) {
        this.group = group;
    }

    public Integer getGroup() {
        return group;
    }
}

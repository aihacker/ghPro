package wxgh.param.pub.score;

/**
 * Created by Administrator on 2017/7/31.
 */
public enum ScoreType {

    //工会积分
    USER_TRAN(4), //工会积分转移
    SPORT(2), //健步积分
    ACT(3), //活动
    GIVE(1), //场馆赠送积分
    PLACE(5), //场馆预约
    USER_EXCHANGE(6), //商品兑换

    //青年部落
    TRIBE_APPLY(1), //青年部落，首次加入积分
    TRIBE_ACT(2),  //青年部落活动

    //纪检积分
    DI_EXAM(3);//考试

    private Integer type;

    ScoreType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}

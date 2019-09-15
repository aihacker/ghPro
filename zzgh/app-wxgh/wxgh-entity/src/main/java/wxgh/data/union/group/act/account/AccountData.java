package wxgh.data.union.group.act.account;

/**
 * Created by Administrator on 2017/7/25.
 */
public class AccountData {

    private Integer id; //活动ID
    private Integer numb; //协会成员总数
    private Integer joinNumb; //活动参加人数
    private String actName; //活动名称
    private Integer account; //是否结算

    private Integer score;
    private Integer leaveScore;
    private Integer outScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public Integer getJoinNumb() {
        return joinNumb;
    }

    public void setJoinNumb(Integer joinNumb) {
        this.joinNumb = joinNumb;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLeaveScore() {
        return leaveScore;
    }

    public void setLeaveScore(Integer leaveScore) {
        this.leaveScore = leaveScore;
    }

    public Integer getOutScore() {
        return outScore;
    }

    public void setOutScore(Integer outScore) {
        this.outScore = outScore;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }
}

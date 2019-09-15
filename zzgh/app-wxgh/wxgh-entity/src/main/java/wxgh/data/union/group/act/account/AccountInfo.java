package wxgh.data.union.group.act.account;

/**
 * Created by Administrator on 2017/7/26.
 */
public class AccountInfo {

    private String actId;
    private String groupId;
    private Integer score;
    private Integer leaveScore;
    private Integer outScore;
    private Integer joinNumb; //参与人数

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public Integer getJoinNumb() {
        return joinNumb;
    }

    public void setJoinNumb(Integer joinNumb) {
        this.joinNumb = joinNumb;
    }
}

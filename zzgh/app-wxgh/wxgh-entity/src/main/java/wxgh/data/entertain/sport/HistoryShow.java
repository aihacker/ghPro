package wxgh.data.entertain.sport;

/**
 * Created by Administrator on 2017/9/8.
 */
public class HistoryShow {

    private Float avgStep; //平均步数
    private Integer todayStep; //今日步数
    private Float distance; //总里程km
    private Integer totalStep;//总步数
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private Integer rank;//总排名

    public Integer getTotalStep() {
        return totalStep;
    }

    public void setTotalStep(Integer totalStep) {
        this.totalStep = totalStep;
    }

    public Float getAvgStep() {
        return avgStep;
    }

    public void setAvgStep(Float avgStep) {
        this.avgStep = avgStep;
    }

    public Integer getTodayStep() {
        return todayStep;
    }

    public void setTodayStep(Integer todayStep) {
        this.todayStep = todayStep;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}

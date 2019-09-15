package wxgh.param.union.race;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/3/2.
 */
public class RaceQuery extends Page {

    private Integer status = 1;
    private String userid;

    private String today;
    private boolean endIs = false; //已结束
    private boolean ingIs = false; //正在进行中
    private boolean noEnd = false; //未结束，报名、进行中
    private boolean baoming = false; //报名中
    private Integer bianpaiIs; //是否编排

    private boolean hasJoin = false; //是否join连接

    public boolean isNoEnd() {
        return noEnd;
    }

    public void setNoEnd(boolean noEnd) {
        this.noEnd = noEnd;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public boolean isEndIs() {
        return endIs;
    }

    public void setEndIs(boolean endIs) {
        this.endIs = endIs;
    }

    public boolean isIngIs() {
        return ingIs;
    }

    public void setIngIs(boolean ingIs) {
        this.ingIs = ingIs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isBaoming() {
        return baoming;
    }

    public void setBaoming(boolean baoming) {
        this.baoming = baoming;
    }

    public Integer getBianpaiIs() {
        return bianpaiIs;
    }

    public void setBianpaiIs(Integer bianpaiIs) {
        this.bianpaiIs = bianpaiIs;
    }

    public boolean isHasJoin() {
        return hasJoin;
    }

    public void setHasJoin(boolean hasJoin) {
        this.hasJoin = hasJoin;
    }

    @Override
    public String toString() {
        return "RaceQuery{" +
                "status=" + status +
                ", userid='" + userid + '\'' +
                ", today='" + today + '\'' +
                ", endIs=" + endIs +
                ", ingIs=" + ingIs +
                ", noEnd=" + noEnd +
                ", baoming=" + baoming +
                ", bianpaiIs=" + bianpaiIs +
                ", hasJoin=" + hasJoin +
                '}';
    }
}

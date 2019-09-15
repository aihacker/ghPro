package wxgh.param.union.race;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/3/2.
 */
public class RaceJoinQuery extends Page {

    private String userid;
    private Integer raceId;
    private Integer status = 1;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

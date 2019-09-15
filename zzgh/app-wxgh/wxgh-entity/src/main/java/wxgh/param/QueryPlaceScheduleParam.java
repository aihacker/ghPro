package wxgh.param;

import java.io.Serializable;

/**
 * Created by WIN on 2016/9/11.
 */
public class QueryPlaceScheduleParam implements Serializable {

    private Integer pid;
    private String week;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}

package wxgh.param.union.woman;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14.
 */
public class YuyueParam implements Serializable {

    private Integer id; //对应mom_id
    private String start;
    private String end;
    private Integer week;
    private Integer dateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }
}

package wxgh.data.entertain.place.yuyue;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */
public class WeekCell {

    private String week;
    private Integer weekInt;
    private List<TimeCell> times;

    public Integer getWeekInt() {
        return weekInt;
    }

    public void setWeekInt(Integer weekInt) {
        this.weekInt = weekInt;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<TimeCell> getTimes() {
        return times;
    }

    public void setTimes(List<TimeCell> times) {
        this.times = times;
    }
}

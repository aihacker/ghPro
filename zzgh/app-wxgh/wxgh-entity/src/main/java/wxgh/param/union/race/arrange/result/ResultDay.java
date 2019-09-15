package wxgh.param.union.race.arrange.result;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */
public class ResultDay {

    private String day;
    private List<Time> times;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }
}

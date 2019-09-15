package wxgh.data.entertain.place;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
public class TimeData {

    private String time;
    private String week;
    private Integer weekInt;
    private Boolean isToday = false;
    private Integer dateId;
    private Integer isSingle;  // 是否为单周

    public Boolean getToday() {
        return isToday;
    }

    public void setToday(Boolean today) {
        isToday = today;
    }

    public Integer getWeekInt() {
        return weekInt;
    }

    public void setWeekInt(Integer weekInt) {
        this.weekInt = weekInt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Integer isSingle) {
        this.isSingle = isSingle;
    }
}

package wxgh.data.entertain.place;

/**
 * @Author xlkai
 * @Version 2017/2/22
 */
public class SelectTime {

    private Integer timeId;
    private String startTime;
    private String endTime;
    private Integer status;
    private Integer week;
    private Integer type;

    public SelectTime() {
    }

    public SelectTime(Integer status) {
        this.status = status;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

package wxgh.param.union.woman;

import pub.dao.page.Page;

/**
 * Created by cby on 2017/9/19.
 */
public class MomYuyueParam extends Page{
    private Integer id;
    private String name;
    private String startTime;
    private String endTime;
    private String week;
    private Integer date;
    private String currentDate;
    private Integer firstweekDay;
    private Integer lastweekDay;

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getFirstweekDay() {
        return firstweekDay;
    }

    public void setFirstweekDay(Integer firstweekDay) {
        this.firstweekDay = firstweekDay;
    }

    public Integer getLastweekDay() {
        return lastweekDay;
    }

    public void setLastweekDay(Integer lastweekDay) {
        this.lastweekDay = lastweekDay;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentData(String currentDate) {
        this.currentDate = currentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}

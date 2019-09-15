package wxgh.entity.union.woman;

import java.util.Date;
import java.util.List;

/**
 * Created by 蔡炳炎 on 2017/9/18.
 */
public class WomanMomData {
    private Integer id;
    private String name;
    private String info;
    private String cover;
    private Date addTime;
    private String week;
    private Integer status;
    List<WomanMomTime> times;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WomanMomTime> getTimes() {
        return times;
    }

    public void setTimes(List<WomanMomTime> times) {
        this.times = times;
    }
}

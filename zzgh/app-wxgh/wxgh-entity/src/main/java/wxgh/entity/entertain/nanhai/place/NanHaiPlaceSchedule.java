package wxgh.entity.entertain.nanhai.place;

import wxgh.entity.entertain.place.Place;
import wxgh.entity.entertain.place.PlaceBook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by WIN on 2016/9/8.
 */
@Entity
@Table(name = "t_nanhai_place_schedule")
public class NanHaiPlaceSchedule implements Serializable {

    private Integer id;
    private Integer pid;
    private String startTime;
    private String endTime;
    private String adminId;
    private Place place;
    private String week;
    private Integer userNumb; //上限人数
    private List<PlaceBook> placeBooks;

    private String timeStr;
    private Boolean isUse; //是否已被排班

    public List<PlaceBook> getPlaceBooks() {
        return placeBooks;
    }

    public void setPlaceBooks(List<PlaceBook> placeBooks) {
        this.placeBooks = placeBooks;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Column(name = "week")
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }


    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }


    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    @Column(name = "admin_id")
    public String getAdminId() {
        return adminId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Column(name = "user_numb")
    public Integer getUserNumb() {
        return userNumb;
    }

    public void setUserNumb(Integer userNumb) {
        this.userNumb = userNumb;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }
}

package wxgh.data.party.tribe;

import wxgh.data.common.FileData;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/16.
 */
public class TribeActInfo extends FileData {

    private Integer id;
    private String theme;
    private Date startTime;
    private Date endTime;
    private String address;
    private Double lng;
    private Double lat;
    private Integer joinNumb;
    private Integer totalNumber;
    private String remark;
    private String content;
    private Integer joinIs; //用户是否报名
    private String timeStr;
    private String commNumb; //评论总数

    private String btnStr;

    private String userid;
    private String username;

    public String getBtnStr() {
        return btnStr;
    }

    public void setBtnStr(String btnStr) {
        this.btnStr = btnStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getJoinNumb() {
        return joinNumb;
    }

    public void setJoinNumb(Integer joinNumb) {
        this.joinNumb = joinNumb;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getJoinIs() {
        return joinIs;
    }

    public void setJoinIs(Integer joinIs) {
        this.joinIs = joinIs;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getCommNumb() {
        return commNumb;
    }

    public void setCommNumb(String commNumb) {
        this.commNumb = commNumb;
    }
}

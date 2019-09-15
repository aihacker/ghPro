package wxgh.data.entertain.place.yuyue;


import com.libs.common.data.DateUtils;

/**
 * Created by Administrator on 2017/3/24.
 */
public class YuyueDetail {

    private Integer id;
    private Integer timeId;
    private String placeName;
    private String cateName;
    private String siteName;
    private String startTime;
    private String endTime;
    private Integer week;
    private String userid;
    private Integer payType;
    private Float payPrice;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Float payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(placeName + "-");
        builder.append(cateName + "-");
        builder.append(siteName + "-");
        builder.append("\n");
        builder.append("时间：" + DateUtils.getWeekName(week) + " " + startTime + "~" + endTime);
        builder.append("\n");
        builder.append("预约已被管理员取消");
        if (payType == 1) {
            builder.append("，系统回退积分：" + payPrice + "分。");
        }
        return builder.toString();
    }
}

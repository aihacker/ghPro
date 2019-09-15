package wxgh.data.entertain.act;

import wxgh.data.common.FileData;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
public class ActInfo extends FileData {

    private Integer id;
    private String actId;
    private String name;
    private String info;
    private String time;
    private String address;
    private Double lat;
    private Double lng;
    private Integer hasScore;
    private Float money;
    private Integer regular;
    private Integer signIs;
    private Integer actNumb;
    private Integer dateid;

    public Integer getActNumb() {
        return actNumb;
    }

    public void setActNumb(Integer actNumb) {
        this.actNumb = actNumb;
    }

    private String userid;
    private String username;
    private Integer joinNumb; //参与人数
    private Integer leaveNumb; //请假人数
    private Integer joinType; //用于判断用户是否报名
    private Integer status; //活动的状态
    private Integer resultCount; //活动成果数量
    private List<String> covers;  // 封面轮播图
    private String remark;//地址备注

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getHasScore() {
        return hasScore;
    }

    public void setHasScore(Integer hasScore) {
        this.hasScore = hasScore;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJoinNumb() {
        return joinNumb;
    }

    public void setJoinNumb(Integer joinNumb) {
        this.joinNumb = joinNumb;
    }

    public Integer getLeaveNumb() {
        return leaveNumb;
    }

    public void setLeaveNumb(Integer leaveNumb) {
        this.leaveNumb = leaveNumb;
    }

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSignIs() {
        return signIs;
    }

    public void setSignIs(Integer signIs) {
        this.signIs = signIs;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }

    @Override
    public String toString() {
        return "ActInfo{" +
                "id=" + id +
                ", actId='" + actId + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", hasScore=" + hasScore +
                ", money=" + money +
                ", regular=" + regular +
                ", signIs=" + signIs +
                ", actNumb=" + actNumb +
                ", dateid=" + dateid +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", joinNumb=" + joinNumb +
                ", leaveNumb=" + leaveNumb +
                ", joinType=" + joinType +
                ", status=" + status +
                ", resultCount=" + resultCount +
                ", covers=" + covers +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

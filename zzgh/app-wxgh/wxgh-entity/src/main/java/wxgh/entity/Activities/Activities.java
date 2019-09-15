package wxgh.entity.Activities;

import wxgh.entity.entertain.act.ScoreRule;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Entity
@Table(name = "t_activities")
public class Activities implements Serializable {

    public static final Integer TYPE_BIG = 1; //大型活动
    public static final Integer TYPE_DEPT = 2; //部门群聊活动
    public static final Integer TYPE_GROUP = 3; //兴趣组群聊活动
    public static final Integer TYPE_TEAM = 4; //团队招募活动
    public static final Integer TYPE_BENBU = 5; //本部活动
    public static final Integer TYPE_QUFENGONGSI = 6; //区分公司
    public static final Integer TYPE_FENGONGSI = 7; //分公司

    private Integer id;
    private String name;
    private String info;
    private Date addTime;
    private String userid;
    private Date startTime;
    private Date endTime;
    private String phone;
    private String headImg;
    private Integer deptid;
    private String address;
    private Double lat;
    private Double lng;
    private Integer actType; //活动类型，1部门活动，2兴趣组协会活动
    private Integer status;
    private Integer integral = 0; //积分规则
    private Float cost; //每人费用
    private String username; //姓名
    private String addrTitle; //地址标题

    private List<ActJoinData> actJoins;
    private ScoreRule scoreRule;
    private String deptname; //部门名称
    private String statusStr; //活动状态名称
    private String timeStr;
    private String addtimeStr;
    private Integer isGivePoints;

    private String addressRemark;
    private Integer isRegular;
    private String weeks;

    private String start;
    private String end;
    private Integer week;
    private String weekStr;

    private Float remind;

    private String mobile;//电话号码
    private String adminApply;//管理员审核内容
    private Date adminApplyTime;//管理员审核后的时间

    private String aduitAdmin;

    private Integer timing;//1未开始， 2进行中， 3已结束

    private Float totalCost;

    private Integer totalNumber;

    //
    private Integer addrType; //活动类型
    private Integer yuyueId; //对应预约表Id


    private Integer isCount;

    @Column(name = "is_count")
    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    @Column(name = "total_cost")
    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    @Column(name = "total_number")
    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    @Column(name = "aduit_admin")
    public String getAduitAdmin() {
        return aduitAdmin;
    }

    public void setAduitAdmin(String aduitAdmin) {
        this.aduitAdmin = aduitAdmin;
    }

    @Column(name = "admin_apply_time")
    public Date getAdminApplyTime() {
        return adminApplyTime;
    }

    public void setAdminApplyTime(Date adminApplyTime) {
        this.adminApplyTime = adminApplyTime;
    }

    @Column(name = "admin_apply")
    public String getAdminApply() {
        return adminApply;
    }

    public void setAdminApply(String adminApply) {
        this.adminApply = adminApply;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "remind")
    public Float getRemind() {
        return remind;
    }

    public void setRemind(Float remind) {
        this.remind = remind;
    }

    public String getWeekStr() {
        return weekStr;
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    @Column(name = "is_regular")
    public Integer getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(Integer isRegular) {
        this.isRegular = isRegular;
    }

    @Column(name = "address_remark")
    public String getAddressRemark() {
        return addressRemark;
    }

    public void setAddressRemark(String addressRemark) {
        this.addressRemark = addressRemark;
    }

    @Column(name = "is_give_points")
    public Integer getIsGivePoints() {
        return isGivePoints;
    }

    public void setIsGivePoints(Integer isGivePoints) {
        this.isGivePoints = isGivePoints;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "head_img")
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Column(name = "act_type")
    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ActJoinData> getActJoins() {
        return actJoins;
    }

    public void setActJoins(List<ActJoinData> actJoins) {
        this.actJoins = actJoins;
    }

    @Column(name = "integral")
    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    @Column(name = "cost")
    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "addr_title")
    public String getAddrTitle() {
        return addrTitle;
    }

    public void setAddrTitle(String addrTitle) {
        this.addrTitle = addrTitle;
    }

    public ScoreRule getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(ScoreRule scoreRule) {
        this.scoreRule = scoreRule;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getAddtimeStr() {
        return addtimeStr;
    }

    public void setAddtimeStr(String addtimeStr) {
        this.addtimeStr = addtimeStr;
    }

    @Column(name = "addr_type")
    public Integer getAddrType() {
        return addrType;
    }

    public void setAddrType(Integer addrType) {
        this.addrType = addrType;
    }

    @Column(name = "yuyue_id")
    public Integer getYuyueId() {
        return yuyueId;
    }

    public void setYuyueId(Integer yuyueId) {
        this.yuyueId = yuyueId;
    }
}


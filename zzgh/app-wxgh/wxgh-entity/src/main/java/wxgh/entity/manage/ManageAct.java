package wxgh.entity.manage;

import wxgh.entity.tribe.TribeActComment;
import wxgh.entity.tribe.TribeActJoin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-03
 **/
@Table(name = "t_manage_act")
@Entity
public class ManageAct implements Serializable {

    public static final Integer STATUS_YG = 1; //活动预告
    public static final Integer STATUS_ZC = 2; //正在众筹
    public static final Integer STATUS_LS = 3; //历史活动

    private Integer id;
    private String theme;
    private Date startTime;
    private Date endTime;
    private String address;
    private Double lng;
    private Double lat;
    private String leader;
    private String linkman;
    private String phone;
    private Integer totalNumber;
    private String coverImg;
    private String remark;
    private String content;
    private Integer status;
    private Date addTime;
    private Integer deptid;
    private Integer crossPoint;

    /*plus*/
    private List<TribeActJoin> tribeActJoins;
    private TribeActComment tribeActComment;
    private String leadername;
    private String linkmanname;
    private String deptname;
    private String avatar;

    private String username;

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String mobile;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getLeadername() {
        return leadername;
    }

    public void setLeadername(String leadername) {
        this.leadername = leadername;
    }

    public String getLinkmanname() {
        return linkmanname;
    }

    public void setLinkmanname(String linkmanname) {
        this.linkmanname = linkmanname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "theme")
    public String getTheme() {
        return theme;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    @Column(name = "leader")
    public String getLeader() {
        return leader;
    }

    @Column(name = "linkman")
    public String getLinkman() {
        return linkman;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "total_number")
    public Integer getTotalNumber() {
        return totalNumber;
    }


    @Column(name = "cover_img")
    public String getCoverImg() {
        return coverImg;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    @Column(name = "cross_point")
    public Integer getCrossPoint() {
        return crossPoint;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setCrossPoint(Integer crossPoint) {
        this.crossPoint = crossPoint;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


    public List<TribeActJoin> getTribeActJoins() {
        return tribeActJoins;
    }

    public void setTribeActJoins(List<TribeActJoin> tribeActJoins) {
        this.tribeActJoins = tribeActJoins;
    }

    public TribeActComment getTribeActComment() {
        return tribeActComment;
    }

    public void setTribeActComment(TribeActComment tribeActComment) {
        this.tribeActComment = tribeActComment;
    }
}


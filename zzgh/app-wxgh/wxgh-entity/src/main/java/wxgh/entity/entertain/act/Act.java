package wxgh.entity.entertain.act;

import wxgh.data.common.FileInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
@Entity
@Table
public class Act implements Serializable {

    public static final Integer ACT_TYPE_GROUP = 1; //协会活动
    public static final Integer ACT_TYPE_TEAM = 2; //团队活动
    public static final Integer ACT_TYPE_BIG = 3; //大型活动：本部
    public static final Integer ACT_TYPE_REGION = 4; //大型活动：区分公司
    public static final Integer ACT_TYPE_DEPT = 5; //大型活动：分公司
    public static final Integer ACT_TYPE_CANTEEN=6;//饭堂活动


    private Integer id;
    private String actId;
    private String groupId;
    private String name;
    private String info;
    private Date addTime;
    private String userid;
    private Date startTime;
    private Date endTime;
    private String phone;
    private String imgId;
    private String imageIds;
    private String address;
    private Double lat;
    private Double lng;
    private String addrTitle;
    private String addrRemark;
    private Integer status;
    private Integer actType;
    private Integer hasScore;
    private String scoreRule;
    private Float money;
    private Integer regular;
    private Float remind;
    private Integer allIs; //是否要求部门全部成员参加
    private Float realMoney; //实际费用
    private Integer account;
    private Integer signIs;
    private String time;
    private String fileIds;

    private List<FileInfo> fileList;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String userName;
    private String groupName;

    private Integer actNumb;

    private String link;//外链活动的链接

    @Column
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column
    public Integer getActNumb() {
        return actNumb;
    }

    public void setActNumb(Integer actNumb) {
        this.actNumb = actNumb;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //
    private String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column
    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    @Column
    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    @Column
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Column
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Column
    public String getAddrTitle() {
        return addrTitle;
    }

    public void setAddrTitle(String addrTitle) {
        this.addrTitle = addrTitle;
    }

    @Column
    public String getAddrRemark() {
        return addrRemark;
    }

    public void setAddrRemark(String addrRemark) {
        this.addrRemark = addrRemark;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    @Column
    public Integer getHasScore() {
        return hasScore;
    }

    public void setHasScore(Integer hasScore) {
        this.hasScore = hasScore;
    }

    @Column
    public String getScoreRule() {
        return scoreRule;
    }

    public void setScoreRule(String scoreRule) {
        this.scoreRule = scoreRule;
    }

    @Column
    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    @Column
    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    @Column
    public Float getRemind() {
        return remind;
    }

    public void setRemind(Float remind) {
        this.remind = remind;
    }

    @Column
    public Integer getAllIs() {
        return allIs;
    }

    public void setAllIs(Integer all) {
        this.allIs = all;
    }

    @Column
    public Float getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Float realMoney) {
        this.realMoney = realMoney;
    }

    @Column
    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    @Column
    public Integer getSignIs() {
        return signIs;
    }

    public void setSignIs(Integer signIs) {
        this.signIs = signIs;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInfo> fileList) {
        this.fileList = fileList;
    }

    @Column
    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    @Override
    public String toString() {
        return "Act{" +
                "id=" + id +
                ", actId='" + actId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", addTime=" + addTime +
                ", userid='" + userid + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", phone='" + phone + '\'' +
                ", imgId='" + imgId + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", addrTitle='" + addrTitle + '\'' +
                ", addrRemark='" + addrRemark + '\'' +
                ", status=" + status +
                ", actType=" + actType +
                ", hasScore=" + hasScore +
                ", scoreRule='" + scoreRule + '\'' +
                ", money=" + money +
                ", regular=" + regular +
                ", remind=" + remind +
                ", allIs=" + allIs +
                ", realMoney=" + realMoney +
                ", account=" + account +
                ", signIs=" + signIs +
                ", time='" + time + '\'' +
                ", fileIds='" + fileIds + '\'' +
                ", fileList=" + fileList +
                ", userName='" + userName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", actNumb=" + actNumb +
                ", link='" + link + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}

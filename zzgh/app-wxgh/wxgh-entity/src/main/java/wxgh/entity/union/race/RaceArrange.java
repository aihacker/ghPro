package wxgh.entity.union.race;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/6.
 */
@Entity
@Table(name = "t_race_arrange")
public class RaceArrange implements Serializable {

    public static final Integer TYPE_PERSON = 1; //个人
    public static final Integer TYPE_TEAM = 2; //团队

    public static final Integer STATUS_NO = 0; //未打
    public static final Integer STATUS_ING = 1; //比赛进行中
    public static final Integer STATUS_END = 2; //已打

    public static final Integer ARRANGE_TYPE_XUNHUAN = 1; //单循环
    public static final Integer ARRANGE_TYPE_GROUP = 2; //分组循环
    public static final Integer ARRANGE_TYPE_RANDOM = 3; //随机选人

    private Integer id;
    private String rival1;
    private String rival2;
    private String remark;
    private Integer raceId;
    private Integer orderNum;
    private Integer type;
    private Integer lunNum;
    private Date raceTime;
    private String address;
    private Integer status;
    private String name1;
    private String name2;
    private Integer lunkong;
    private Integer arrangeType;

    @Column(name = "lunkong")
    public Integer getLunkong() {
        return lunkong;
    }

    public void setLunkong(Integer lunkong) {
        this.lunkong = lunkong;
    }

    @Column(name = "name1")
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    @Column(name = "name2")
    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
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

    @Column(name = "rival1")
    public String getRival1() {
        return rival1;
    }

    public void setRival1(String rival1) {
        this.rival1 = rival1;
    }

    @Column(name = "rival2")
    public String getRival2() {
        return rival2;
    }

    public void setRival2(String rival2) {
        this.rival2 = rival2;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "raceId")
    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    @Column(name = "order_num")
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "lun_num")
    public Integer getLunNum() {
        return lunNum;
    }

    public void setLunNum(Integer lunNum) {
        this.lunNum = lunNum;
    }

    @Column(name = "race_time")
    public Date getRaceTime() {
        return raceTime;
    }

    public void setRaceTime(Date raceTime) {
        this.raceTime = raceTime;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "arrange_type")
    public Integer getArrangeType() {
        return arrangeType;
    }

    public void setArrangeType(Integer arrangeType) {
        this.arrangeType = arrangeType;
    }

    @Override
    public String toString() {
        return "RaceArrange{" +
                "id=" + id +
                ", rival1='" + rival1 + '\'' +
                ", rival2='" + rival2 + '\'' +
                ", remark='" + remark + '\'' +
                ", raceId=" + raceId +
                ", orderNum=" + orderNum +
                ", type=" + type +
                ", lunNum=" + lunNum +
                ", raceTime=" + raceTime +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", lunkong=" + lunkong +
                ", arrangeType=" + arrangeType +
                '}';
    }
}


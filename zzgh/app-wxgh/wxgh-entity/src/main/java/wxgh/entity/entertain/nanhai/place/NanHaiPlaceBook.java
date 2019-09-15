package wxgh.entity.entertain.nanhai.place;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/9/8.
 */
@Entity
@Table(name = "t_nanhai_place_book")
public class NanHaiPlaceBook implements Serializable{

    private Integer id;
    private Integer pid;
    private Integer psid;
    private String uid;
    private Date startTime;
    private Date endTime;
    private Integer status;

    private String userName;
    private String mobile;
    private String typeName;
    private Integer placeType;
    private String title;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Column(name = "psid")
    public Integer getPsid() {
        return psid;
    }

    @Column(name = "uid")
    public String getUid() {
        return uid;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setPsid(Integer psid) {
        this.psid = psid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

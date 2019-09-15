package wxgh.entity.party.match;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ✔ on 2017/4/20.
 */
@Entity
@Table(name = "t_sheying_act")
public class SheyingAct implements Serializable {

    private Integer id;
    private String cover;
    private String name;
    private String linkman;
    private String tel;
    private String address;
    private String content;
    private String remark;
    private Date startTime;
    private Date endTime;

    /**
     * @Author: ✔
     * @Description: plus as
     * @Date: 9:26, 2017/4/21
     */
    private String linkmanname;
    private String statusStr;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
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

    @Column(name = "cover")
    public String getCover() {
        return cover;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "linkman")
    public String getLinkman() {
        return linkman;
    }

    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

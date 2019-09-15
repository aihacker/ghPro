package wxgh.data.entertain.act;

import wxgh.data.common.FileData;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24.
 */
public class ActList extends FileData {

    private Integer id;
    private String name;
    private String address;
    private String time;
    private String phone;
    private Float money;
    private Integer regular;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private String info;
    private Integer actNumb;
    private String imgId;
    private String actId;
    private String link;
    private String linkIs;

    public String getLinkIs() {
        return linkIs;
    }

    public void setLinkIs(String linkIs) {
        this.linkIs = linkIs;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getActNumb() {
        return actNumb;
    }

    public void setActNumb(Integer actNumb) {
        this.actNumb = actNumb;
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

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}

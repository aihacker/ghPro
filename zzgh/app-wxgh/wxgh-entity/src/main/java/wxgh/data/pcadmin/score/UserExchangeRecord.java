package wxgh.data.pcadmin.score;


import java.io.Serializable;
import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-11-02  10:54
 * --------------------------------------------------------- *
 */
public class UserExchangeRecord implements Serializable{

    private Integer id;
    private String userid;
    private String mobile;
    private String username;
    private String name;
    private Integer goodsId;
    private Date addTime;
    private Float score;
    private Integer type;
    private Integer confrim;
    private Integer status;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getConfrim() {
        return confrim;
    }

    public void setConfrim(Integer confrim) {
        this.confrim = confrim;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

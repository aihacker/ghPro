package wxgh.entity.tribe;

import pub.bean.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-04
 **/
@Table(name = "t_tribe_act_comment")
@Entity
public class TribeActComment extends Page implements Serializable {

    private Integer id;
    private String userid;
    private Integer actId;
    private Date addTime;
    private String content;
    private String img;
    private List<String> imgList;

    /*plus*/
    private String avatar;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "act_id")
    public Integer getActId() {
        return actId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

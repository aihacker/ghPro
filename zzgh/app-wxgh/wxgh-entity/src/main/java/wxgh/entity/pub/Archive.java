package wxgh.entity.pub;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-01
 **/
@Entity
@Table(name = "t_archive")
public class Archive implements Serializable {

    public static final Integer STATUS_OK = 1; //正常
    public static final Integer STATUS_DELETE = 2; //已删除

    private Integer id;
    private String name;
    private Date addTime;
    private String imgIds;
    private String userid;
    private Integer status;

    private List<String> imgIdList;
    private String imgList;

    @Column(name = "img_list")
    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        this.imgList = imgList;
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

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "img_ids")
    public String getImgIds() {
        return imgIds;
    }

    public void setImgIds(String imgIds) {
        this.imgIds = imgIds;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getImgIdList() {
        return imgIdList;
    }

    public void setImgIdList(List<String> imgIdList) {
        this.imgIdList = imgIdList;
    }
}


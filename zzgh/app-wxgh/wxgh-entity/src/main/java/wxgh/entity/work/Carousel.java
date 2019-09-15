package wxgh.entity.work;

import pub.dao.page.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hhl
 * @create 2017-09-11
 **/
@Entity
@Table(name = "t_work_carousel")
public class Carousel extends Page implements Serializable{

    private Integer id;
    private Integer display;
    private Integer top;
    private String briefInfo;
    private String img;
    private String path;
    private Integer sortId;
    private Integer type;

    public Carousel(){}

    public Carousel(Integer display, String briefInfo, String img, Integer sortId, Integer type, Date addTime) {
        this.display = display;
        this.briefInfo = briefInfo;
        this.img = img;
        this.sortId = sortId;
        this.type = type;
        this.addTime = addTime;
    }

    @Column(name="sort_id")
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Column(name="add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    private Date addTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="display")
    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    @Column(name="top")
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Column(name="brief_info")
    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    @Column(name="img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

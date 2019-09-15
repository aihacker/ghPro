package wxgh.entity.tribe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hhl
 * @create 2017-08-07
 **/
@Entity
@Table(name = "t_tribe_result")
public class TribeResult implements Serializable {

    private Integer id;
    private String title;
    private String content;
    private Date addDate;
    private Integer addId;
    private String remark;
    private String briefInfo;
    private String author;
    private String link;
    private String coverImg;

    @Column(name = "coverImg")
    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    @Column(name = "brief_info")
    public String getBriefInfo() {
        return briefInfo;
    }


    @Column(name = "author")
    public String getAuthor() {
        return author;
    }

    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }


    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "add_date")
    public Date getAddDate() {
        return addDate;
    }

    @Column(name = "add_id")
    public Integer getAddId() {
        return addId;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }
}


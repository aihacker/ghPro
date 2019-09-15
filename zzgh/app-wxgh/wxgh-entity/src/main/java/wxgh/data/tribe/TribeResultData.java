package wxgh.data.tribe;


import wxgh.data.common.FileData;

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
 * @Date 2017-12-13  14:10
 * --------------------------------------------------------- *
 */
public class TribeResultData extends FileData {

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
    private Integer seeNumb;
    private Integer zanNumb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getSeeNumb() {
        return seeNumb;
    }

    public void setSeeNumb(Integer seeNumb) {
        this.seeNumb = seeNumb;
    }

    public Integer getZanNumb() {
        return zanNumb;
    }

    public void setZanNumb(Integer zanNumb) {
        this.zanNumb = zanNumb;
    }
}

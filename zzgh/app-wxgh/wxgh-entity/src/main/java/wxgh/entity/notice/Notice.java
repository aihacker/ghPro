package wxgh.entity.notice;

import pub.dao.page.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-04 15:39
 *----------------------------------------------------------
 */
@Entity
@Table
public class Notice extends Page{

    // 公告类型
    public static final Integer TYPE_Activity = 1;
    public static final Integer TYPE_Recruit = 2;

    // 主体类型
    public static final Integer SUBJECT_Group = 1;
    public static final Integer SUBJECT_Team = 2;

    private Integer id;
    private String title;
    private String content;
    private Date addTime;
    private String img;
    private Integer type;    // 公告类型:  1为活动公告，2为招募公告
    private Integer subjectType; // 主体类型: 1 为协会  2 为团队
    private Integer pid;     // 关联到的协会id, 或者团队id, 或者其他等等等
    private Integer deptid;
    private Date endTime;
    private String imageId;

    /**
     * @Author: ✔
     * @Description: 附加参数
     * @Date: 9:15, 2017/3/27
     */
    private Integer exceptDeptid;

    public Integer getExceptDeptid() {
        return exceptDeptid;
    }

    public void setExceptDeptid(Integer exceptDeptid) {
        this.exceptDeptid = exceptDeptid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "img")
    public String getImg() {
        return img;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "image_id")
    public String getImageId() {
        return imageId;
    }

    @Column(name = "subject_type")
    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
}


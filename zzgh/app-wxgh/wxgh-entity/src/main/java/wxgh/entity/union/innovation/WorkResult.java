package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
@Entity
@Table(name = "t_work_result")
public class WorkResult implements Serializable {

    private Integer id;
    private String content; //创新结果内容
    private String type; //结果展示类型
    private Date addTime; //添加时间
    private Integer workId; //创新需求ID
    private String adminId; //添加管理员
    private String headImg; //图片地址
    private String name;
    private Integer workType;
    private Integer orderid;
    private String imageIds;
    private String txt;

    /*plus*/
    private String workTypeStr;
    private String itemName;
    private String teamName;
    private String imgAvatar;
    private Integer status;
    private String auditIdea;
    private Integer applyId;

    private Integer miId;

    public Integer getMiId() {
        return miId;
    }

    public void setMiId(Integer miId) {
        this.miId = miId;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWorkTypeStr() {
        return workTypeStr;
    }

    public void setWorkTypeStr(String workTypeStr) {
        this.workTypeStr = workTypeStr;
    }

    /*plus*/
    private String innovateName;

    public String getInnovateName() {
        return innovateName;
    }

    public void setInnovateName(String innovateName) {
        this.innovateName = innovateName;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private WorkInnovate workInnovate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "work_id")
    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    @Column(name = "admin_id")
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Column(name = "head_img")
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public WorkInnovate getWorkInnovate() {
        return workInnovate;
    }

    public void setWorkInnovate(WorkInnovate workInnovate) {
        this.workInnovate = workInnovate;
    }

    @Column(name = "work_type")
    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    @Column(name = "orderid")
    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    @Column(name = "image_ids")
    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    @Column(name = "txt")
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}

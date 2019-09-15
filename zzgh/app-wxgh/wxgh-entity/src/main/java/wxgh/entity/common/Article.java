package wxgh.entity.common;

import wxgh.data.common.FileData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 10:10
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_article")
public class Article implements Serializable {
    //1.文章id
    private Integer atlId;
    //2.标题
    private String atlName;
    //3.内容
    private String atlContent;
    //4.创建时间
    private Date createTime;
    private String createFormatTime;
    //5.图片id
    private Integer photoFileId;
    private String photoFileContent;
    private byte[] photoFileBytes;
    //6.用户id
    private String userid;
    //7.是否删除
    private Integer isdel;
    //8.状态：“普通”：“热点”
    private Integer status;
    //9.类型
    private Integer type;
    //10.评论数量
    private Integer commNum;

    private Integer seeNum;
    private Integer zanNum;

    private Integer deptid;

    private String fileIds;

    private List<FileData> fileList;

    private String userName;
    private String avatar;
    private String deptname;

    private String createFormatDate;

    public String getCreateFormatDate() {
        return createFormatDate;
    }

    public void setCreateFormatDate(String createFormatDate) {
        this.createFormatDate = createFormatDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    @Column(name = "atl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getAtlId() {
        return atlId;
    }

    public void setAtlId(Integer atlId) {
        this.atlId = atlId;
    }

    @Column(name = "atl_name")
    public String getAtlName() {
        return atlName;
    }

    public void setAtlName(String atlName) {
        this.atlName = atlName;
    }

    @Column(name = "atl_content")
    public String getAtlContent() {
        return atlContent;
    }

    public void setAtlContent(String atlContent) {
        this.atlContent = atlContent;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "user_id")
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

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateFormatTime() {
        if (createTime != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createFormatTime = format.format(createTime);
        } else {
            createFormatTime = "";
        }
        return createFormatTime;
    }

    public void setCreateFormatTime(String createFormatTime) {
        this.createFormatTime = createFormatTime;
    }

    @Column(name = "photo_file_id")
    public Integer getPhotoFileId() {
        return photoFileId;
    }

    public void setPhotoFileId(Integer photoFileId) {
        this.photoFileId = photoFileId;
    }

    public String getPhotoFileContent() {
        return photoFileContent;
    }

    public void setPhotoFileContent(String photoFileContent) {
        this.photoFileContent = photoFileContent;
    }

    public byte[] getPhotoFileBytes() {
        return photoFileBytes;
    }

    public void setPhotoFileBytes(byte[] photoFileBytes) {
        this.photoFileBytes = photoFileBytes;
    }

    @Column(name = "isdel")
    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    @Column(name = "comm_num")
    public Integer getCommNum() {
        return commNum;
    }

    public void setCommNum(Integer commNum) {
        this.commNum = commNum;
    }

    @Column(name = "file_ids")
    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public List<FileData> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileData> fileList) {
        this.fileList = fileList;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name = "see_num")
    public Integer getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(Integer seeNum) {
        this.seeNum = seeNum;
    }

    @Column(name = "zan_num")
    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}


package wxgh.entity.party.match;

import wxgh.entity.party.beauty.WorkFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ✔ on 2017/4/21.
 */
@Entity
@Table(name = "t_sheying_match_join")
public class SheyingMatchJoin implements Serializable {

    private Integer id;
    private Integer mid;
    private String userid;
    private Date addTime;
    private Integer status;
    private String name;
    private String remark;
    private String works;
    private Integer type;
    private String previewImage;

    private Integer isVote;
    private List<WorkFile> files;
    private List<String> imgList;
    private String imgLast;
    private String imgFirst;
    private Integer voteCount;

    public List<WorkFile> getFiles() {
        return files;
    }

    public void setFiles(List<WorkFile> files) {
        this.files = files;
    }

    @Column(name = "preview_image")
    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getImgLast() {
        return imgLast;
    }

    public void setImgLast(String imgLast) {
        this.imgLast = imgLast;
    }

    public String getImgFirst() {
        return imgFirst;
    }

    public void setImgFirst(String imgFirst) {
        this.imgFirst = imgFirst;
    }

    @Column(name = "works")
    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public Integer getIsVote() {
        return isVote;
    }

    public void setIsVote(Integer isVote) {
        this.isVote = isVote;
    }

    /**
     * @Author: ✔
     * @Description: plus as
     * @Date: 11:00, 2017/4/21
     */
    private String username;
    private String avatar;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    @Column(name = "mid")
    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

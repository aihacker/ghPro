package wxgh.entity.party.beauty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_work")
public class Work implements Serializable {

    private Integer id; //作品ID
    private String userId; //用户Id
    private Integer actId; //作品比赛活动ID
    private String name; //作品名称
    private Integer type; //作品类型
    private String files; // 图片/视频
    private String fileIds;
    private Date AddTime; //创建时间
    private String remark; //作品说明
    private Integer status;
    private String previewImage;

    private String username;
    private String avatar;

    private List<WorkFile> workFiles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "preview_image")
    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    @Column(name = "act_id")
    public Integer getActId() {
        return actId;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "files")
    public String getFiles() {
        return files;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return AddTime;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name = "file_ids")
    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<WorkFile> getWorkFiles() {
        return workFiles;
    }

    public void setWorkFiles(List<WorkFile> workFiles) {
        this.workFiles = workFiles;
    }
}

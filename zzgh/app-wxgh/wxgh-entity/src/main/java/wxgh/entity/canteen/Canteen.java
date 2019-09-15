package wxgh.entity.canteen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class Canteen {
    private Integer id;
    private String groupId;
    private String name;
    private String info;
    private Date addTime;
    private Integer status;
    private String userid;
    private String avatarId;
    private String userName;
    private String path;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", addTime=" + addTime +
                ", status=" + status +
                ", userid='" + userid + '\'' +
                ", avatarId='" + avatarId + '\'' +
                ", userName='" + userName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

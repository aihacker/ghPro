package wxgh.entity.party.beauty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_work_comment")
public class WorkComment implements Serializable {

    private Integer id; //评论ID
    private String userId; //用户Id
    private Integer workId; //作品ID
    private Date AddTime; //创建时间
    private String content;//评论内容

    private String username;
    private String avatar;

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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    @Column(name = "work_id")
    public Integer getWorkId() {
        return workId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return AddTime;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

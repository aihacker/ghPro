package wxgh.data.entertain.sport.apply;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/9
 * time：11:25
 * version：V1.0
 * Description：
 */
public class SportApplyList {

    private Integer id;
    private String userid;
    private String avatar;
    private String username;
    private Date addTime;
    private Integer dateid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }
}

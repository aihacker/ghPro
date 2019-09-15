package wxgh.entity.pub.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2017/12/28
 * time：14:25
 * version：V1.0
 * Description：
 */
public class ApiUser {

    private String userid;
    private String name;
    @JsonIgnore
    private String mobile;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

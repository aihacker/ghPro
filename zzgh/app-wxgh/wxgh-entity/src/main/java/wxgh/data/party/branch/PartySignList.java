package wxgh.data.party.branch;

import java.util.Date;


public class PartySignList {

    private String userid;
    private String avatar;
    private String username;

    private Date addTime;
    private String address;
    private Float distan;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getDistan() {
        return distan;
    }

    public void setDistan(Float distan) {
        this.distan = distan;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

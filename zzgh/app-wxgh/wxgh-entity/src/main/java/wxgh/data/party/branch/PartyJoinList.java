package wxgh.data.party.branch;

import wxgh.data.pub.user.SimpleUser;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/22.
 */
public class PartyJoinList extends SimpleUser {

    private Integer status;
    private Date addTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

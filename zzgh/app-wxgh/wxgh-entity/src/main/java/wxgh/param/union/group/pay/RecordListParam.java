package wxgh.param.union.group.pay;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/21.
 */
public class RecordListParam extends Page {

    private Integer groupId;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}

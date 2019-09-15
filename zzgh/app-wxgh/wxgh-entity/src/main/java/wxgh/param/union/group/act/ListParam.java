package wxgh.param.union.group.act;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/24.
 */
public class ListParam extends Page {

    private String userid;
    private Integer groupId;

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

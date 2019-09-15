package wxgh.param.party.di.notice;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/21.
 */
public class ListParam extends Page {

    private Integer groupId;
    private Integer type;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

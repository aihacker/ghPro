package wxgh.param.party.di;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/28.
 */
public class ListParam extends Page {

    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}

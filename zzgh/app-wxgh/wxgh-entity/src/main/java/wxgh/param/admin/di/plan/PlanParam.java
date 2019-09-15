package wxgh.param.admin.di.plan;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/21.
 */
public class PlanParam extends Page {

    private Integer groupId;
    private Integer type;

    private Integer partyId;

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

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}

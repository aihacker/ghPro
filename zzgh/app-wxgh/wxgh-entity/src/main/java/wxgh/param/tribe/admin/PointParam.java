package wxgh.param.tribe.admin;

import pub.dao.page.Page;

/**
 * Created by cby on 2017/8/21.
 */

public class PointParam extends Page {

    private Integer actId;
    private Integer group;
    private Integer type;

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }
}

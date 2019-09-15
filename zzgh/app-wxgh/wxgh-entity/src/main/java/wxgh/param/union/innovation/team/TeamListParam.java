package wxgh.param.union.innovation.team;

import pub.dao.page.Page;

/**
 * Created by Ape on >_2017/8/21.
 */
public class TeamListParam extends Page {

    private Integer status;
    private Integer groupId;
    private String name;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

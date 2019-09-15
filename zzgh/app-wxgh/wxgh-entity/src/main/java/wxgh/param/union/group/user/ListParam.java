package wxgh.param.union.group.user;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/21.
 */
public class ListParam extends Page {

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

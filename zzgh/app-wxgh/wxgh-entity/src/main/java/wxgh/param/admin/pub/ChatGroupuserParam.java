package wxgh.param.admin.pub;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/18.
 */
public class ChatGroupuserParam extends Page {

    private Integer type;
    private Integer status;
    private Integer groupId;
    private Integer order;  // 1 按部门排序升序, 2 按部门降序 3 按加入时间升序， 4 按加入时间降序

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}

package wxgh.data.pcadmin.pub;

import wxgh.data.pub.user.SimpleUser;

/**
 * Created by Administrator on 2017/8/18.
 */
public class ChatGroupuser extends SimpleUser {

    private Integer id;
    private Integer status;
    private Integer type;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

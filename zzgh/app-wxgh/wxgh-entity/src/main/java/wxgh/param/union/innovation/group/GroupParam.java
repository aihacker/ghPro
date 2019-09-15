package wxgh.param.union.innovation.group;

import java.io.Serializable;

/**
 * Created by ✔ on 2016/10/9.
 */
public class GroupParam implements Serializable {
    private Integer id;
    private Integer start;
    private Integer num;
    private Integer status;
    private Integer isFirst;
    private Integer groupOldestId;

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getGroupOldestId() {
        return groupOldestId;
    }

    public void setGroupOldestId(Integer groupOldestId) {
        this.groupOldestId = groupOldestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

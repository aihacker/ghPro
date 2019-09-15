package wxgh.param.union.innovation.work;


import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
public class WorkUserQuery extends Page implements Serializable {

    private Integer status = 1;
    private Integer workId; //岗位创新ID
    private Integer workType; //岗位创新类型
    private String userid;

    private Integer start;
    private Integer num;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public WorkUserQuery() {
    }

    public WorkUserQuery(Integer workId) {
        this.workId = workId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

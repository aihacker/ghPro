package wxgh.param.union.innovation.work;


import pub.dao.page.Page;
import wxgh.param.union.innovation.Query;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
public class WorkResultQuery extends Query implements Serializable {

    private Integer id;
    private Integer starts;
    private Integer num;
    private Integer workType;
    private Integer status;
    private String userid;

    private Integer deptid;
    private Integer nodeptid;

    private String content;

    private Integer workId;

    private boolean isMine = false;

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public Integer getNodeptid() {
        return nodeptid;
    }

    public void setNodeptid(Integer nodeptid) {
        this.nodeptid = nodeptid;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

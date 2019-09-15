package wxgh.param.union.innovation;


import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by ✔ on 2016/11/15.
 */
public class QueryInnovateAdvice extends Page implements Serializable {
    private Integer id;
    private String userid;
    private Integer showType;
    private Integer status;
    private Integer num;
    private Integer start;


    private String title;
    private String advice;
    private Integer deptId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QueryInnovateAdvice{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", showType=" + showType +
                ", status=" + status +
                ", num=" + num +
                ", start=" + start +
                ", title='" + title + '\'' +
                ", advice='" + advice + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}

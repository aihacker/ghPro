package wxgh.param.union.innovation.group;


import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/9/24.
 * <p>
 * Date： 2016/9/24
 */
public class GroupQuery extends Page implements Serializable {

    private Integer status;

    private String userid;
    private Integer groupId;
    private Integer deptid;

    private Integer start;
    private Integer num;
    private Integer type;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public GroupQuery() {
        super();
    }

    public GroupQuery(int totalCount, int numPerPage) {
        super(totalCount, numPerPage);
    }

    private String fuzzyName; //模糊搜索关键字

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userId) {
        this.userid = userId;
    }

    public String getFuzzyName() {
        return fuzzyName;
    }

    public void setFuzzyName(String fuzzyName) {
        this.fuzzyName = fuzzyName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}

package wxgh.param.common.suggest;

import java.io.Serializable;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 17:24
 *----------------------------------------------------------
 */
public class UserSuggestQuery implements Serializable {

    private Integer cateId;
    private Integer status;

    private Integer start;
    private Integer num;
    private Integer id;
    private Integer userSuggestOldestId;
    private Integer isFirst;
    private String userid;
    private Integer deptid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getUserSuggestOldestId() {
        return userSuggestOldestId;
    }

    public void setUserSuggestOldestId(Integer userSuggestOldestId) {
        this.userSuggestOldestId = userSuggestOldestId;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
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

    public UserSuggestQuery() {
    }

    public UserSuggestQuery(Integer cateId, Integer status, Integer deptid) {
        this.cateId = cateId;
        this.status = status;
        this.deptid = deptid;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}



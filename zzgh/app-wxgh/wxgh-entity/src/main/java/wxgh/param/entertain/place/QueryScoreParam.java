package wxgh.param.entertain.place;


import pub.bean.SimplePage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/9.
 */
public class QueryScoreParam extends SimplePage implements Serializable {

    private String userId;
    private Integer status;

    private String addType;
    private String groupStr;
    private Integer addId;

    private Integer id;
    private Integer isCount;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    private String theme;


    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getGroupStr() {
        return groupStr;
    }

    public void setGroupStr(String groupStr) {
        this.groupStr = groupStr;
    }

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }
}

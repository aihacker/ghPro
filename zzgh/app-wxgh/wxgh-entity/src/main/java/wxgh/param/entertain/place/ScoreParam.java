package wxgh.param.entertain.place;


import pub.dao.page.Page;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
public class ScoreParam extends Page {

    private String userId;
    private Integer status;
    private Integer scoreType;

    private String addType;
    private String groupStr;
    private Integer addId;
    private List<String> groupStrs;
    private List<String> notGroupStrs;

    public List<String> getGroupStrs() {
        return groupStrs;
    }

    public void setGroupStrs(List<String> groupStrs) {
        this.groupStrs = groupStrs;
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

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }

    public List<String> getNotGroupStrs() {
        return notGroupStrs;
    }

    public void setNotGroupStrs(List<String> notGroupStrs) {
        this.notGroupStrs = notGroupStrs;
    }
}

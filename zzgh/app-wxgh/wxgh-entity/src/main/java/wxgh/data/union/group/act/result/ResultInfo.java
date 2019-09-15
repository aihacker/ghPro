package wxgh.data.union.group.act.result;

import wxgh.data.common.FileData;
import wxgh.entity.union.group.ActResult;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
public class ResultInfo extends ActResult {

    private String avatar;
    private String username;
    private String timeStr;
    private Integer zanIs;

    private List<FileData> imgFiles;

    private Integer activityId;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getZanIs() {
        return zanIs;
    }

    public void setZanIs(Integer zanIs) {
        this.zanIs = zanIs;
    }

    public List<FileData> getImgFiles() {
        return imgFiles;
    }

    public void setImgFiles(List<FileData> imgFiles) {
        this.imgFiles = imgFiles;
    }
}

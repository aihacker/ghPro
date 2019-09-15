package wxgh.param.pub.apply;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/30.
 * <p>
 * Date： 2016/8/30
 */
public class ApplyResult implements Serializable {

    private Integer id; //申请ID
    private String title; //申请类型
    private String status; //申请状态文字描述
    private String applyTime; //申请时间
    private String userId; //申请用户ID
    private String content; //申请描述

    /**
     *@Author:  ✔
     *@Description:  zzj
     *@Date:  10:58, 2016/11/15
     */
    private String imgAvatar;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package wxgh.data.chat;

import wxgh.data.common.FileData;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
public class ChatGroupInfo extends FileData {

    private Integer id;
    private String groupId;
    private String name;
    private String info;
    private Date addTime;

    private List<String> avatars;

    private Integer userNum; //用户数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }
}

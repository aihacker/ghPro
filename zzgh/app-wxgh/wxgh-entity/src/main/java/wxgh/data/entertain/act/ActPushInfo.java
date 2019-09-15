package wxgh.data.entertain.act;

import wxgh.data.common.FileData;

/**
 * Created by Administrator on 2017/8/14.
 */
public class ActPushInfo extends FileData {

    private Integer id;
    private String name;
    private String info;
    private String time; //活动时间
    private String address; //活动地点
    private Integer regular; //是否为定期
    private Integer allIs; //是否要求全部人员参加
    private Integer actType;
    private String groupName;
    private String groupId;
    private String link;//是否有外链

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public Integer getAllIs() {
        return allIs;
    }

    public void setAllIs(Integer allIs) {
        this.allIs = allIs;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }
}

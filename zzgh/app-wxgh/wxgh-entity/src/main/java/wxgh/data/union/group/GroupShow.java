package wxgh.data.union.group;

import wxgh.entity.union.group.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class GroupShow extends Group {

    private String path; //协会头像路径
    private String thumb; //协会头像thumb路径

    private Integer applyCount; //申请列表数量
    private Integer userType; //当前用户类型
    private Boolean join = false; //是否为群成员

    private String username; //协会名片
    private Integer userNumb; //协会成员总数
    private List<String> avatars;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserNumb() {
        return userNumb;
    }

    public void setUserNumb(Integer userNumb) {
        this.userNumb = userNumb;
    }

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public Boolean getJoin() {
        return join;
    }

    public void setJoin(Boolean join) {
        this.join = join;
    }
}

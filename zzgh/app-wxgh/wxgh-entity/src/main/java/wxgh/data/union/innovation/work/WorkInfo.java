package wxgh.data.union.innovation.work;

import java.io.Serializable;
import java.util.List;

/**
 * 工作坊
 *
 * @Author xlkai
 * @Version 2016/12/27
 */
public class WorkInfo extends BaseInfo implements Serializable {

    private Integer id;
    private String teamName; //团队名称
    private String itemName; //项目名称
    private String address; //项目场地
    private Integer shopType; //工作坊类型，1创新工作坊，2劳模工作坊
    private String teamLeader; //项目带头人

    private List<UserInfo> userInfos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }
}

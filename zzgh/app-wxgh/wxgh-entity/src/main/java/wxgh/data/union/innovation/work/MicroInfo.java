package wxgh.data.union.innovation.work;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/12/27
 */
public class MicroInfo extends BaseInfo implements Serializable {

    private Integer id;
    private String team; //团队名称
    private String cate; //申报类别
    private String point; //创新点
    private String behave; //创新举措

    private List<UserInfo> userInfos; //成员列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getBehave() {
        return behave;
    }

    public void setBehave(String behave) {
        this.behave = behave;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }


}

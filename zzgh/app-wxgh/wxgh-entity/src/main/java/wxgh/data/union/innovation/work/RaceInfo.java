package wxgh.data.union.innovation.work;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/12/27
 */
public class RaceInfo extends BaseInfo implements Serializable {

    private Integer id;
    private Integer raceType;
    private String raceName;

    private List<UserInfo> userInfos;

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Integer getRaceType() {
        return raceType;
    }

    public void setRaceType(Integer raceType) {
        this.raceType = raceType;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

package wxgh.data.entertain.sport.save;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/20
 */
public class UserSport {

    private List<SportData> sports; //23时23分59秒数据
    private SportData sport; //今日数据

    public SportData getSport() {
        return sport;
    }

    public void setSport(SportData sport) {
        this.sport = sport;
    }

    public List<SportData> getSports() {
        return sports;
    }

    public void setSports(List<SportData> sports) {
        this.sports = sports;
    }
}

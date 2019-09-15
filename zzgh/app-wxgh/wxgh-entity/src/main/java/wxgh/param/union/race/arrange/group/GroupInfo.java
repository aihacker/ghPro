package wxgh.param.union.race.arrange.group;


import wxgh.entity.union.race.RaceGroup;
import wxgh.entity.union.race.RaceGroupDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
public class GroupInfo {

    private RaceGroup group;
    private List<RaceGroupDetail> details;

    public RaceGroup getGroup() {
        return group;
    }

    public void setGroup(RaceGroup group) {
        this.group = group;
    }

    public List<RaceGroupDetail> getDetails() {
        return details;
    }

    public void setDetails(List<RaceGroupDetail> details) {
        this.details = details;
    }
}

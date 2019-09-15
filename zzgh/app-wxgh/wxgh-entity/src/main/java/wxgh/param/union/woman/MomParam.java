package wxgh.param.union.woman;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/9/14.
 */
public class MomParam extends Page {

    private Integer week;
    private Integer status;

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

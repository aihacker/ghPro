package wxgh.param.party.branch.collect;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/9/19.
 */
public class UserParam extends Page {

    private Integer id;
    private Integer type;
    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

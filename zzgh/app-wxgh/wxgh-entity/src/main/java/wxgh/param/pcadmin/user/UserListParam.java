package wxgh.param.pcadmin.user;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/9/25.
 */
public class UserListParam extends Page {

    private Integer deptid;
    private String search;

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

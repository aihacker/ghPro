package wxgh.param.party.member;


import pub.dao.page.Page;

/**
 * Created by Sheng on 2017/9/5.
 */
public class PartyParam extends Page {
    private Integer deptid;
    private String search;
    private String userid;
    private String worker;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}

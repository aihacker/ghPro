package wxgh.param.four;

import java.io.Serializable;

/**
 * Created by ✔ on 2016/11/16.
 */
public class QueryMarketingParam implements Serializable {

    private Integer id;
    private Integer deptid;
    private Integer deptidP;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeptidP() {
        return deptidP;
    }

    public void setDeptidP(Integer deptidP) {
        this.deptidP = deptidP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}

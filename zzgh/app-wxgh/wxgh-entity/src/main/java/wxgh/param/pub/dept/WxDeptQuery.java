package wxgh.param.pub.dept;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-20 11:14
 *----------------------------------------------------------
 */
public class WxDeptQuery {

    private Integer deptype;
    private Integer deptid;
    private Integer parentid;

    private String orderStr; //排序语句

    private String deptname;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public WxDeptQuery() {
    }

    public WxDeptQuery(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getDeptype() {
        return deptype;
    }

    public void setDeptype(Integer deptype) {
        this.deptype = deptype;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
    
}


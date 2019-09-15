package wxgh.data.wxadmin.user;

/**
 * Created by Administrator on 2017/9/25.
 */
public class DeptList {

    private Integer id;
    private Integer deptid;
    private String name;
    private Integer parentid;
    private Integer hasChild;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getHasChild() {
        return hasChild;
    }

    public void setHasChild(Integer hasChild) {
        this.hasChild = hasChild;
    }

    @Override
    public String toString() {
        return "DeptList{" +
                "id=" + id +
                ", deptid=" + deptid +
                ", name='" + name + '\'' +
                ", parentid=" + parentid +
                ", hasChild=" + hasChild +
                '}';
    }
}

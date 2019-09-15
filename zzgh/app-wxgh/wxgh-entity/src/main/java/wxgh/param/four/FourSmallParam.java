package wxgh.param.four;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
public class FourSmallParam implements Serializable {

    private Integer deptId;

    private List<Integer> depts;

    private Integer id;

    public FourSmallParam() {
    }

    public FourSmallParam(List<Integer> depts) {
        this.depts = depts;
    }

    public FourSmallParam(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<Integer> getDepts() {
        return depts;
    }

    public void setDepts(List<Integer> depts) {
        this.depts = depts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

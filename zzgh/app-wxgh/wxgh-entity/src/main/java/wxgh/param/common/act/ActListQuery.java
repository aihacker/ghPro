package wxgh.param.common.act;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/6/20.
 * <p>
 * Date： 2016/6/20
 */
public class ActListQuery implements Serializable {

    private Integer deptId; //部门ID

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}

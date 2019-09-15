package wxgh.entity.pub;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 部门用户
 * Created by Administrator on 2017/7/13.
 */
@Entity
@Table
public class DeptUser implements Serializable {

    private Integer id;
    private String userid;
    private Integer deptid;

    public DeptUser() {
    }

    public DeptUser(String userid, Integer deptid) {
        this.userid = userid;
        this.deptid = deptid;
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Override
    public boolean equals(Object obj) {
        DeptUser deptUser = (DeptUser) obj;
        if (deptid.equals(deptUser.getDeptid())) {
            return true;
        }
        return false;
    }
}

package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
@Entity
@Table(name = "t_work_user")
public class WorkUser implements Serializable {

    public static final Integer TYPE_NORMAL = 1; //普通成员
    public static final Integer TYPE_ZHUZHANG = 2; //组长
    public static final Integer TYPE_FZHUZHANG = 3; //副组长
    public static final Integer TYPE_MAIN = 4; //项目负责人

    private Integer id;
    private String name;  //用户姓名
    private String userid; //userid
    private Integer userType; //用户类型，1普通成员，2组长，3副组长，4项目带头人
    private String typeName;  //用户类型的名称
    private Integer status; //用户状态，1正常，2已删除等
    private Integer workId;  //岗位创新ID
    private Integer workType; //岗位创新类型

    private String userImg;

    private String wgName;

    private String deptname;

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getWgName() {
        return wgName;
    }

    public void setWgName(String wgName) {
        this.wgName = wgName;
    }

    @Column(name = "work_type")
    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "user_type")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "work_id")
    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}

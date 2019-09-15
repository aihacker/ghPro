package wxgh.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/9.
 */
@Entity
@Table
public class NewAdmin implements Serializable {

    private Integer id;
    private String adminId;
    private String name;
    private String password;
    private String cateId;
    private String remark;
    private String navId;
    private String extra;

    private String changeDate;

    //
    private String cateName;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
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
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column
    public String getNavId() {
        return navId;
    }

    public void setNavId(String navId) {
        this.navId = navId;
    }

    @Column
    public String getExtra() {
        return extra;
    }

    @Column
    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "NewAdmin{" +
                "id=" + id +
                ", adminId='" + adminId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", cateId='" + cateId + '\'' +
                ", remark='" + remark + '\'' +
                ", navId='" + navId + '\'' +
                ", extra='" + extra + '\'' +
                ", changeDate=" + changeDate +
                ", cateName='" + cateName + '\'' +
                '}';
    }
}

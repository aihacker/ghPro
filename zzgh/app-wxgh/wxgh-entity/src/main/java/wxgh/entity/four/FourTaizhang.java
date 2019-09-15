package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by WIN on 2016/8/29.
 */
@Entity
@Table(name = "t_four_taizhang")
public class FourTaizhang implements Serializable{

    private  Integer id;
    private  Integer fpid;
    private  String  fpname;
    private  Integer fpcid;
    private  String  fpcname;
    private  Integer numb;
    private  Integer status0;
    private  Integer status1;
    private  Integer status2;
    private  String  remark;

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "fpname")
    public String getFpname() {
        return fpname;
    }

    @Column(name = "fpcname")
    public String getFpcname() {
        return fpcname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "fp_id")
    public Integer getFpid() {
        return fpid;
    }

    @Column(name = "fpc_id")
    public Integer getFpcid() {
        return fpcid;
    }

    @Column(name = "numb")
    public Integer getNumb() {
        return numb;
    }

    @Column(name = "status0")
    public Integer getStatus0() {
        return status0;
    }

    @Column(name = "status1")
    public Integer getStatus1() {
        return status1;
    }

    @Column(name = "status2")
    public Integer getStatus2() {
        return status2;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFpid(Integer fpid) {
        this.fpid = fpid;
    }

    public void setFpcid(Integer fpcid) {
        this.fpcid = fpcid;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public void setStatus0(Integer status0) {
        this.status0 = status0;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public void setFpname(String fpname) {
        this.fpname = fpname;
    }

    public void setFpcname(String fpcname) {
        this.fpcname = fpcname;
    }
}

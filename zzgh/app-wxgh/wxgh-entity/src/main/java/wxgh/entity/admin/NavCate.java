package wxgh.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/9.
 */
@Entity
@Table
public class NavCate implements Serializable {

    private Integer id;
    private String name;
    private String info;
    private Integer status;
    private Integer cateOrder;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public Integer getCateOrder() {
        return cateOrder;
    }

    public void setCateOrder(Integer cateOrder) {
        this.cateOrder = cateOrder;
    }
}

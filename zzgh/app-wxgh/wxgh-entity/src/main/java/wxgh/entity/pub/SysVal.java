package wxgh.entity.pub;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */
@Entity
@Table
public class SysVal implements Serializable {

    public static final String VAL_TOKEN = "token";

    private Integer id;
    private String name;
    private String val;
    private Long addTime;

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
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Column
    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}

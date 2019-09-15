package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ✔ on 2016/11/16.
 */
@Entity
@Table(name = "t_marketing")
public class Marketing implements Serializable {

    private Integer id;
    private Integer deptid;
    private String name;
    private String avatar;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

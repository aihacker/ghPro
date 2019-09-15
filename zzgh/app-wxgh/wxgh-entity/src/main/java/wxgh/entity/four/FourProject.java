package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by WIN on 2016/8/26.
 */
@Entity
@Table(name = "t_four_project")
public class FourProject implements Serializable {

    private Integer id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

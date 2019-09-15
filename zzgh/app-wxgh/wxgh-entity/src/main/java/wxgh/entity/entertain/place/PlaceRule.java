package wxgh.entity.entertain.place;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2017/2/17
 */
@Entity
@Table(name = "t_place_rule")
public class PlaceRule implements Serializable {

    public static final Integer TYPE_NUMB = 1;
    public static final Integer TYPE_SITE = 2;

    private Integer id;
    private Integer type;
    private String name;
    private String info;
    private String typeName;
    private String rule;
    private Integer status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTypeName() {
        return typeName;
    }

    @Column(name = "type_name")
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "rule")
    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

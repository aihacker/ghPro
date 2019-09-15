package wxgh.entity.common.suggest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 16:49
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_suggest_cate")
public class SuggestCate implements Serializable {

    private Integer id;
    private String name;
    private Integer status;

    private List<UserSuggest> suggests;

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

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UserSuggest> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<UserSuggest> suggests) {
        this.suggests = suggests;
    }
}



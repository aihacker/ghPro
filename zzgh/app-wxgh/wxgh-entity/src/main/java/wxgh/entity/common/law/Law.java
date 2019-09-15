package wxgh.entity.common.law;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 14:32
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_law")
public class Law implements Serializable {

    private Integer id;
    private String name;
    private String content;
    private Date time;
    private String url;
    private Integer sortId;

    @Column(name = "sort_id")
    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column (name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column (name = "time")
    public Date getTime() {
        return time;
    }

    @Column (name = "url")
    public String getUrl() {
        return url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}



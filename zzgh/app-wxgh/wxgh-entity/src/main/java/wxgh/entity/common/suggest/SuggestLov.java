package wxgh.entity.common.suggest;

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
 * @Date 2017-07-28 09:52
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_suggest_lov")
public class SuggestLov implements Serializable {

    public static final Integer TYPE_SUG = 1; //建言池
    public static final Integer TYPE_COMM = 2; //评论

    private Integer id;
    private String userid;
    private Integer sugId;
    private Date addTime;
    private Integer status;
    private Integer sugType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "sug_id")
    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "sug_type")
    public Integer getSugType() {
        return sugType;
    }

    public void setSugType(Integer sugType) {
        this.sugType = sugType;
    }
}


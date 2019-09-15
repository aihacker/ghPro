package wxgh.entity.party;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/20.
 */
@Entity
@Table(name = "t_zan")
public class Zan implements Serializable {

    public static final Integer TYPE_PARTY = 1; //党建文章
    public static final Integer TYPE_ARTICLE = 2; //快乐分享文章
    public static final Integer TYPE_ARTICLE_COMM = 3; //快乐分享评论

    private Integer id;
    private String userid;
    private Date addTime;
    private Integer zanId;
    private Integer zanType;

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

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "zan_id")
    public Integer getZanId() {
        return zanId;
    }

    public void setZanId(Integer zanId) {
        this.zanId = zanId;
    }

    @Column(name = "zan_type")
    public Integer getZanType() {
        return zanType;
    }

    public void setZanType(Integer zanType) {
        this.zanType = zanType;
    }
}

package wxgh.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "t_zan")
public class Zan implements Serializable {

    public static final Integer TYPE_PARTY = 1; //党建文章
    public static final Integer TYPE_ARTICLE = 2; //热点论坛文章
    public static final Integer TYPE_ARTICLE_COMM = 3; //热点论坛评论,活动成果评论点赞
    public static final Integer TYPE_ACT_RESULT = 4; //活动成果点赞
    public static final Integer TYPE_ACT_RESULT_COMM = 5; //活动成果评论点赞

    public static final Integer TYPE_PARTY_ACT_RESULT = 6; // 党建支部活动成果点赞
    public static final Integer TYPE_PARTY_ACT_RESULT_COMM = 7; // 党建支部活动成果评论点赞

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



package wxgh.sys.dao.common.suggest;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 10:06
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_suggest_vote_join")
public class SuggestVoteJoin implements Serializable {

    private Integer id;
    //用户id
    private String userid;
    //投票时间
    private Date joinTime;
    private String joinFormat;
    //主题id
    private Integer sug_id;
    //状态
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    @Column(name = "sug_id")
    public Integer getSug_id() {
        return sug_id;
    }

    public void setSug_id(Integer sug_id) {
        this.sug_id = sug_id;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "join_time")
    public String getJoinFormat() {
        if (joinTime != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            joinFormat = format.format(joinTime);
        } else {
            joinFormat = "";
        }

        return joinFormat;
    }

    public void setJoinFormat(String joinFormat) {
        this.joinFormat = joinFormat;
    }
}



package wxgh.entity.common.suggest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
@Table(name = "t_suggest_vote")
public class SuggestVote implements Serializable {

    private Integer id;
    //建议id
    private Integer sugid;
    //同意方
    private Integer approve;
    //反对
    private Integer opposition;
    //中立
    private Integer neutral;
    //狀態
    private Integer status;
    //類型
    private Integer type;
    //列名
    private String columnName;

    private String userid;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "sug_id")
    public Integer getSugid() {
        return sugid;
    }

    public void setSugid(Integer sugid) {
        this.sugid = sugid;
    }

    @Column(name = "approve")
    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    @Column(name = "opposition")
    public Integer getOpposition() {
        return opposition;
    }

    public void setOpposition(Integer opposition) {
        this.opposition = opposition;
    }

    @Column(name = "neutral")
    public Integer getNeutral() {
        return neutral;
    }

    public void setNeutral(Integer neutral) {
        this.neutral = neutral;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}



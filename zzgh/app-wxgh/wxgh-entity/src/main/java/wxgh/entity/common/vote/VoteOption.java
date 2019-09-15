package wxgh.entity.common.vote;

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
 * Created by Admin on 2016/7/7.
 */
@Entity
@Table(name = "t_voted_option")
public class VoteOption implements Serializable {

    private Integer id;
    //主题id
    private Integer voteid;
    //选项
    private String options;
    //投票数量
    private int ticketNum;
    //时间
    private Date createTime;
    private String createFormatTime;
    //是否删除
    private Integer isdel;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "voteId")
    public Integer getVoteid() {
        return voteid;
    }

    public void setVoteid(Integer voteid) {
        this.voteid = voteid;
    }


    @Column(name = "options")
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Column(name = "ticketNum")
    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateFormatTime() {
        if (createTime != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            createFormatTime = format.format(createTime);
        } else {
            createFormatTime = "";
        }

        return createFormatTime;
    }

    public void setCreateFormatTime(String createFormatTime) {
        this.createFormatTime = createFormatTime;
    }

    @Column(name = "isdel")
    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}

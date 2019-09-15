package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_work_zan")
public class WorkZan implements Serializable {

    private Integer id; //点赞ID
    private String userId; //用户Id
    private Integer workId; //作品ID
    private Date AddTime; //创建时间


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    @Column(name = "work_id")
    public Integer getWorkId() {
        return workId;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return AddTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }
}

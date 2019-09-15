package wxgh.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by WIN on 2016/9/1.
 */
public class FAShenheData implements Serializable {

    private Integer id;
    private Integer status;
    private String auditIdea;
    private Timestamp applyTime;
    private Date auditTime;

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }
}

package wxgh.entity.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table
public class ChatUser implements Serializable {

    // 1创建人，2管理员，3成员
    public static final Integer TYPE_CREATEOR = 1;
    public static final Integer TYPE_MANAGER = 2;
    public static final Integer TYPE_MEMBER = 3;

    public static final Integer TYPE_ADMIN = 1; //管理员
    public static final Integer TYPE_USER = 2; //成员

    private Integer id;
    private String userid;
    private Integer type;
    private String groupId;
    private Date addTime;
    private Integer status; // 用户状态，默认0审核中，1审核通过，2未通过
    private String remark;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package wxgh.entity.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/11.
 */
@Entity
@Table
public class ChatGroup implements Serializable {

    public static final Integer TYPE_GROUP = 1; //协会
    public static final Integer TYPE_TEAM = 2; //团队招募
    public static final Integer TYPE_TRIBE = 3; //青年部落
    public static final Integer TYPE_DI = 4; //纪检
    public static final Integer TYPE_PARTY = 5; //党支部
    public static final Integer TYPE_PARTY_GROUP = 6; //党群体

    private Integer id;
    private String groupId;
    private String name;
    private String avatar;
    private String info;
    private Integer type;
    private Date addTime;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

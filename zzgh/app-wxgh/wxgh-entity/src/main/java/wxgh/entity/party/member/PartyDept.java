package wxgh.entity.party.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Sheng on 2017/9/5.
 */
@Entity
@Table(name = "t_party_dept")
public class PartyDept {
    private Integer id;
    private String name;
    private Integer parentid;
    private String groupId;
    private Integer isParty;  // 是否为党委

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "parentid")
    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    @Column(name = "group_id")
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column(name ="is_party")
    public Integer getIsParty() {
        return isParty;
    }

    public void setIsParty(Integer isParty) {
        this.isParty = isParty;
    }
}

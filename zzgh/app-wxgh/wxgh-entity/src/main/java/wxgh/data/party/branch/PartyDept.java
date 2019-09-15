package wxgh.data.party.branch;

import pub.dao.page.Page;

/**
 * Created by 蔡炳炎 on 2017/9/12.
 */
public class PartyDept extends Page {
    private Integer id;
    private String name;
    private String partyName;
    private Integer parentid;
    private boolean isBranch;
    private Integer devNum;
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getDevNum() {
        return devNum;
    }

    public void setDevNum(Integer devNum) {
        this.devNum = devNum;
    }

    public boolean isBranch() {
        return isBranch;
    }

    public void setBranch(boolean branch) {
        isBranch = branch;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

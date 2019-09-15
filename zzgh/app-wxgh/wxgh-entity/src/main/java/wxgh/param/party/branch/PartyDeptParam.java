package wxgh.param.party.branch;

import pub.dao.page.Page;

/**
 * Created by 蔡炳炎 on 2017/9/13.
 */
public class PartyDeptParam extends Page{
    private Integer party_id;
    private Integer branch_id;
    private Integer sex_age;

    public Integer getParty_id() {
        return party_id;
    }

    public void setParty_id(Integer party_id) {
        this.party_id = party_id;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getSex_age() {
        return sex_age;
    }

    public void setSex_age(Integer sex_age) {
        this.sex_age = sex_age;
    }
}

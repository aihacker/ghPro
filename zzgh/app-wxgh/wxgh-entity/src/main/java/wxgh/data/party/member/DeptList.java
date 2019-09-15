package wxgh.data.party.member;

public class DeptList {
    private Integer id;
    private Integer branch_id;
    private Integer party_id;

    public DeptList(Integer id, Integer branch_id, Integer party_id) {
        this.id = id;
        this.branch_id = branch_id;
        this.party_id = party_id;
    }

    public DeptList() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }

    public Integer getParty_id() {
        return party_id;
    }

    public void setParty_id(Integer party_id) {
        this.party_id = party_id;
    }
}

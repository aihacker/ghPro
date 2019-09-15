package wxgh.param.party.manage;


import pub.dao.page.Page;

public class PartyParam extends Page{
    private Integer branchid;
    private Integer partyid;

    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getPartyid() {
        return partyid;
    }

    public void setPartyid(Integer partyid) {
        this.partyid = partyid;
    }
}

package wxgh.param.party.exam;

import pub.dao.page.Page;

/**
 * Created by Sheng on 2017/8/22.
 */
public class ExamJoinBranchParam extends Page {
    private Integer eid;
    private Integer exam;
    private Integer partyId;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}

package wxgh.param.party.di.exam;

import pub.dao.page.Page;

import java.util.Date;

/**
 * Created by Sheng on 2017/8/22.
 */
public class ExamJoinParam extends Page {
    private Integer eid;
    private Integer exam;

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
}

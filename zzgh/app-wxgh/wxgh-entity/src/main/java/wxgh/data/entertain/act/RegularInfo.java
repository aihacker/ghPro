package wxgh.data.entertain.act;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/22.
 */
public class RegularInfo {

    private String actId;
    private String weeks;
    private Date startTime;
    private Date endTime;
    private Integer regular;
    private Integer actType;
    private Integer actNumb;

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public Integer getRegular() {
        return regular;
    }

    public void setRegular(Integer regular) {
        this.regular = regular;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Integer getActNumb() {
        return actNumb;
    }

    public void setActNumb(Integer actNumb) {
        this.actNumb = actNumb;
    }
}

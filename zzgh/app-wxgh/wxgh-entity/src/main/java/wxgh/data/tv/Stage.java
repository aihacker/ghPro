package wxgh.data.tv;

/**
 * Created by Administrator on 2017/6/6.
 */
public class Stage {

    private Integer id;
    private Integer eventId;
    private Integer prevStageId;
    private Integer no;
    private Integer competitionRuleId;
    private Integer courtCount;
    private Long startDate;
    private Integer amStartTime;
    private Integer pmStartTime;
    private Integer groupCount;
    private Integer teamCount;
    private Integer matchCount;
    private Integer reqCourtCount;
    private Float requCourtHours;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getPrevStageId() {
        return prevStageId;
    }

    public void setPrevStageId(Integer prevStageId) {
        this.prevStageId = prevStageId;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public Integer getCompetitionRuleId() {
        return competitionRuleId;
    }

    public void setCompetitionRuleId(Integer competitionRuleId) {
        this.competitionRuleId = competitionRuleId;
    }

    public Integer getCourtCount() {
        return courtCount;
    }

    public void setCourtCount(Integer courtCount) {
        this.courtCount = courtCount;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Integer getAmStartTime() {
        return amStartTime;
    }

    public void setAmStartTime(Integer amStartTime) {
        this.amStartTime = amStartTime;
    }

    public Integer getPmStartTime() {
        return pmStartTime;
    }

    public void setPmStartTime(Integer pmStartTime) {
        this.pmStartTime = pmStartTime;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public Integer getReqCourtCount() {
        return reqCourtCount;
    }

    public void setReqCourtCount(Integer reqCourtCount) {
        this.reqCourtCount = reqCourtCount;
    }

    public Float getRequCourtHours() {
        return requCourtHours;
    }

    public void setRequCourtHours(Float requCourtHours) {
        this.requCourtHours = requCourtHours;
    }
}

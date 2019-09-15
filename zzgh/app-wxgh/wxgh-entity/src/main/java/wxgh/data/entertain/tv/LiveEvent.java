package wxgh.data.entertain.tv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveEvent {

    private Integer code;
    @JsonProperty("team_a")
    private String teamA;
    @JsonProperty("team_b")
    private String teamB;
    private String spacename;
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("sub_match_no")
    private Integer subMatchNo;
    @JsonProperty("event_name")
    private String eventName;
    @JsonProperty("stage_name")
    private String stageName;

    private String timeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getSpacename() {
        return spacename;
    }

    public void setSpacename(String spacename) {
        this.spacename = spacename;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getSubMatchNo() {
        return subMatchNo;
    }

    public void setSubMatchNo(Integer subMatchNo) {
        this.subMatchNo = subMatchNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

}

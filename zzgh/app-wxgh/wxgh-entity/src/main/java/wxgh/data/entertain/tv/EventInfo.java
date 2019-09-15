package wxgh.data.entertain.tv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventInfo {

    private Integer id;
    private String name;
    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("team_name_a")
    private String teamNameA;
    @JsonProperty("team_name_b")
    private String teamNameB;
    @JsonProperty("a_name")
    private String aName;
    @JsonProperty("b_name")
    private String bName;
    private String spaces;
    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("project_name")
    private String projectName;
    private String stage;
    @JsonProperty("match_id")
    private Integer matchId;
    @JsonProperty("stage_id")
    private Integer stageId;
    @JsonProperty("event_id")
    private Integer eventId;
    @JsonProperty("point_system")
    private Integer pointSystem;
    @JsonProperty("a_player")
    private String aPlayer;
    @JsonProperty("b_player")
    private String bPlayer;

    private String timeStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTeamNameA() {
        return teamNameA;
    }

    public void setTeamNameA(String teamNameA) {
        this.teamNameA = teamNameA;
    }

    public String getTeamNameB() {
        return teamNameB;
    }

    public void setTeamNameB(String teamNameB) {
        this.teamNameB = teamNameB;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getSpaces() {
        return spaces;
    }

    public void setSpaces(String spaces) {
        this.spaces = spaces;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getStageId() {
        return stageId;
    }

    public void setStageId(Integer stageId) {
        this.stageId = stageId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getPointSystem() {
        return pointSystem;
    }

    public void setPointSystem(Integer pointSystem) {
        this.pointSystem = pointSystem;
    }

    public String getaPlayer() {
        return aPlayer;
    }

    public void setaPlayer(String aPlayer) {
        this.aPlayer = aPlayer;
    }

    public String getbPlayer() {
        return bPlayer;
    }

    public void setbPlayer(String bPlayer) {
        this.bPlayer = bPlayer;
    }
}

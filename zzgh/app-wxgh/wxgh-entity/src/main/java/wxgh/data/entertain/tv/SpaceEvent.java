package wxgh.data.entertain.tv;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpaceEvent {

    @JsonProperty("eventname")
    private String eventName;
    @JsonProperty("stagename")
    private String stageName;
    @JsonProperty("matchtype")
    private String matchType;
    @JsonProperty("spacename")
    private String spaceName;
    @JsonProperty("team_a")
    private String teamA;
    @JsonProperty("team_b")
    private String teamB;
    @JsonProperty("project_name")
    private String projectName;
    @JsonProperty("start_time")
    private Long startTime;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("game_over")
    private Integer gameOver;

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

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getGameOver() {
        return gameOver;
    }

    public void setGameOver(Integer gameOver) {
        this.gameOver = gameOver;
    }
}

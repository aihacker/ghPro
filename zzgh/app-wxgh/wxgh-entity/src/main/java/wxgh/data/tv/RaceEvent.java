package wxgh.data.tv;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/6/6.
 */
public class RaceEvent {

    private Integer id;
    private String name; //赛事名称
    @JsonProperty("simple_name")
    private String simpleName;
    private Integer type;
    @JsonProperty("type_text")
    private String typeText;
    @JsonProperty("start_date")
    private Long startDate;
    private String remark;
    @JsonProperty("create_time")
    private Long createTime;

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

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RaceEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", simpleName='" + simpleName + '\'' +
                ", type=" + type +
                ", typeText='" + typeText + '\'' +
                ", startDate=" + startDate +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

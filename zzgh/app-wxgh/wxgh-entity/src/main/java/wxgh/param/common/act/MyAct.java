package wxgh.param.common.act;

/**
 * Created by Administrator on 2016/7/20.
 */
public class MyAct {
    private Integer id;
    private String name;
    private String startTime;
    private String createBy;
    private Integer actHead;
    private Integer status;
    private Integer type;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Integer getActHead() {
        return actHead;
    }

    public void setActHead(Integer actHead) {
        this.actHead = actHead;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

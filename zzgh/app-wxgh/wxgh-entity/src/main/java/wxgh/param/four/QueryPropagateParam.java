package wxgh.param.four;

import java.io.Serializable;

/**
 * Created by WIN on 2016/8/25.
 */
public class QueryPropagateParam implements Serializable {

    private String name;
    private String content;
    private Integer status;
    private Integer step;
    private Integer start;
    private Integer num;

    private Integer isFirst;
    private Integer fourOldestId;
    private Integer id;
    private String applyType;
    private Integer jbId;

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Integer getJbId() {
        return jbId;
    }

    public void setJbId(Integer jbId) {
        this.jbId = jbId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getFourOldestId() {
        return fourOldestId;
    }

    public void setFourOldestId(Integer fourOldestId) {
        this.fourOldestId = fourOldestId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

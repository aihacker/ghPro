package wxgh.param.union.innovation.work;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
public class WorkQuery implements Serializable {

    private Integer status = 1; //默认获取正常工作坊
    private Integer id;
    private String name; //模糊查询
    private Integer start;
    private Integer num;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    private String userid;

    private Integer innovateId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getInnovateId() {
        return innovateId;
    }

    public void setInnovateId(Integer innovateId) {
        this.innovateId = innovateId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

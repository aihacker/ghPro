package wxgh.data.union.innovation;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/24.
 */
public class ResultShowData implements Serializable {

    private Integer id;
    private String name;
    private String content;
    private Date time;
    private String type;
    private String userid;
    private String imgUrl;
    private Integer innovateId;
    private Integer workId;

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer getInnovateId() {
        return innovateId;
    }

    public void setInnovateId(Integer innovateId) {
        this.innovateId = innovateId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}

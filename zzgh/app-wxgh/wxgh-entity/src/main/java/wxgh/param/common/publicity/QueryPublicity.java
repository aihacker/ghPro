package wxgh.param.common.publicity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/25.
 */
public class QueryPublicity implements Serializable {

    private String name;
    private String content;
    private  Integer start;
    private Integer num;
    private Integer status;

    private Integer isFirst;
    private Integer publicityOldestId;
    private String type;
    private Integer id;


    private Date time;
    private String picture;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getPublicityOldestId() {
        return publicityOldestId;
    }

    public void setPublicityOldestId(Integer publicityOldestId) {
        this.publicityOldestId = publicityOldestId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

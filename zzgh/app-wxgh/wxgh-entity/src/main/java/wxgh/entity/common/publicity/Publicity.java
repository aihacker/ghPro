package wxgh.entity.common.publicity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/24.
 */
@Entity
@Table(name = "t_publicity")
public class Publicity implements Serializable {

    public static final String TYPE_TXTIMG = "txtimg";
    public static final String TYPE_URL = "url";

    private Integer id;
    private String name;
    private String content;
    private Date time;
    private String type;
    private String userid;
    private Integer top;

    private String url;
    private String picture;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    @Column(name = "time")
    public Date getTime() {
        return time;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "top")
    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Publicity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", top=" + top +
                ", url='" + url + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}

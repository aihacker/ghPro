package wxgh.entity.common.female;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:21
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_female_lesson")
public class FemaleLesson implements Serializable {

    private Integer id;
    private String name;
    private String content;
    private String files;
    private Date startTime;
    private Date endTime;
    private Date addTime;
    private String uuid;
    private String cover;

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

    @Column(name = "files")
    public String getFiles() {
        return files;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    @Column(name = "cover")
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public void setFiles(String files) {
        this.files = files;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}


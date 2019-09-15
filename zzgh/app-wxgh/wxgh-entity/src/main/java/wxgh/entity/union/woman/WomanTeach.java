package wxgh.entity.union.woman;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/13.
 */
@Entity
@Table
public class WomanTeach implements Serializable {

    private Integer id;
    private String name;
    private String content;
    private Date startTime;
    private Date endTime;
    private Date addTime;
    private String cover;
    private String remark;
    private String files;
    private Integer JoinNum;

    @Column
    public Integer getJoinNum() {
        return JoinNum;
    }

    public void setJoinNum(Integer joinNum) {
        JoinNum = joinNum;
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Column
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column
    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}

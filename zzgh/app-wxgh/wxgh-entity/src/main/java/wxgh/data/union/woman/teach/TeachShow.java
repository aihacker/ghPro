package wxgh.data.union.woman.teach;

import wxgh.data.common.FileData;
import wxgh.data.common.FileInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
public class TeachShow extends FileData {

    private Integer id;
    private String name;
    private String content;
    private Date startTime;
    private Date endTime;
    private Date addTime;
    private String time;
    private String remark;
    private String files;
    private Integer joinIs;
    private List<FileInfo> fileList;
    private List<String> joins;
    private Integer joinNum;
    private Integer joinTotal;

    public Integer getJoinTotal() {
        return joinTotal;
    }

    public void setJoinTotal(Integer joinTotal) {
        this.joinTotal = joinTotal;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getJoinIs() {
        return joinIs;
    }

    public void setJoinIs(Integer joinIs) {
        this.joinIs = joinIs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInfo> fileList) {
        this.fileList = fileList;
    }

    public List<String> getJoins() {
        return joins;
    }

    public void setJoins(List<String> joins) {
        this.joins = joins;
    }

    public String toString() {
        return "TeachShow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", addTime=" + addTime +
                ", time='" + time + '\'' +
                ", remark='" + remark + '\'' +
                ", files='" + files + '\'' +
                ", joinIs=" + joinIs +
                ", fileList=" + fileList +
                ", joins=" + joins +
                ", joinNum=" + joinNum +
                ", joinTotal=" + joinTotal +
                '}';
    }
}

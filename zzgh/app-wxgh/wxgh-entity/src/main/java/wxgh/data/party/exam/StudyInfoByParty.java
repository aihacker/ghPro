package wxgh.data.party.exam;

import wxgh.data.common.FileData;
import wxgh.data.common.FileInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
public class StudyInfoByParty extends FileData {

    private Integer id;
    private String name;
    private String info;
    private Boolean joinIs; //是否报名
    private Integer exam; //是否考试
    private Date addTime;
    private Date endTime;
    private String files;
    private Boolean endIs; //考试是否已经结束

    private Integer partyId; // 党委id

    private List<FileInfo> fileInfos;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getJoinIs() {
        return joinIs;
    }

    public void setJoinIs(Boolean joinIs) {
        this.joinIs = joinIs;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Boolean getEndIs() {
        return endIs;
    }

    public void setEndIs(Boolean endIs) {
        this.endIs = endIs;
    }

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<FileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }
}

package wxgh.param.admin.di;

import com.fasterxml.jackson.annotation.JsonFormat;
import pub.dao.page.Page;

import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-31 10:02
 * ----------------------------------------------------------
 */
public class ExamParam extends Page {

    private Integer id;
    private String name;
    private String info;
    private String coverId;
    private Date addTime;
    private String briefInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;
    private String files;
    private Integer total;
    private String content;
    private String groupId;
    private Integer type;
    private String gname; //问卷面向的对象


    private Integer partyId;
    private Integer isParty; // 1 支部 2 党委

    private Integer planId;
    private List<QuestionParam> questions;

    private Integer status;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<QuestionParam> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionParam> questions) {
        this.questions = questions;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getIsParty() {
        return isParty;
    }

    public void setIsParty(Integer isParty) {
        this.isParty = isParty;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }
}


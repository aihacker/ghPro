package wxgh.param.party.vote;

import com.fasterxml.jackson.annotation.JsonFormat;
import wxgh.param.admin.di.QuestionParam;

import java.util.Date;
import java.util.List;

public class VoteParam {
    private Integer id;
    private String name;
    private String briefInfo;
    private Date addTime;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private List<QuestionParam> questions;

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

    public String getBriefInfo() {
        return briefInfo;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public List<QuestionParam> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionParam> questions) {
        this.questions = questions;
    }
}

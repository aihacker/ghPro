package wxgh.data.party.di;

import wxgh.entity.party.di.ExamQuestion;

import java.util.List;

/**
 * Created by Administrator on 2017/7/29.
 */
public class QuestionInfo extends ExamQuestion {

    private String answerId; //上次答题ID
    private List<AnswerInfo> answers;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public List<AnswerInfo> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerInfo> answers) {
        this.answers = answers;
    }
}

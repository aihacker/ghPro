package wxgh.entity.party.di;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/29.
 */
@Entity
@Table(name = "di_exam_answer")
public class ExamAnswer implements Serializable {

    private Integer id;
    private String name;
    private Integer orderNumb;
    private Integer isAnswer;
    private Integer questionId;

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
    public Integer getOrderNumb() {
        return orderNumb;
    }

    public void setOrderNumb(Integer orderNumb) {
        this.orderNumb = orderNumb;
    }

    @Column
    public Integer getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
    }

    @Column
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}

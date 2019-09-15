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
@Table(name = "di_exam_question")
public class ExamQuestion implements Serializable {

    private Integer id;
    private String name;
    private Integer type;
    private Integer orderNumb;
    private Integer examId;

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
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column
    public Integer getOrderNumb() {
        return orderNumb;
    }

    public void setOrderNumb(Integer orderNumb) {
        this.orderNumb = orderNumb;
    }

    @Column
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }
}

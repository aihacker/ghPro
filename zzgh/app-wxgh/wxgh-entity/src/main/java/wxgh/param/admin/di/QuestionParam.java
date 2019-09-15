package wxgh.param.admin.di;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 问题
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-31 09:59
 *----------------------------------------------------------
 */
public class QuestionParam {

    private Integer id;
    private String name;
    private String type; // 问题类型，1单选，2多选
    private String orderNumb;
    private Integer examId;
    private List<OptionParam> answers;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderNumb() {
        return orderNumb;
    }

    public void setOrderNumb(String orderNumb) {
        this.orderNumb = orderNumb;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public List<OptionParam> getAnswers() {
        return answers;
    }

    public void setAnswers(List<OptionParam> answers) {
        this.answers = answers;
    }
}


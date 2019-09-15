package wxgh.data.union.innovation.work;

import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2016/12/27
 */
public class AdviceInfo extends BaseInfo implements Serializable {

    private Integer id;
    private String title;
    private Integer adviceType;
    private String advice;
    private Integer seeNumb;
    private Integer zanNumb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(Integer adviceType) {
        this.adviceType = adviceType;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getSeeNumb() {
        return seeNumb;
    }

    public void setSeeNumb(Integer seeNumb) {
        this.seeNumb = seeNumb;
    }

    public Integer getZanNumb() {
        return zanNumb;
    }

    public void setZanNumb(Integer zanNumb) {
        this.zanNumb = zanNumb;
    }
}

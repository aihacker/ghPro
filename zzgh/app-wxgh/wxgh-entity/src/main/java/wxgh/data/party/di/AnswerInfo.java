package wxgh.data.party.di;

/**
 * Created by Administrator on 2017/7/29.
 */
public class AnswerInfo {

    private Integer id;
    private String name;
    private Integer orderNumb;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Integer getOrderNumb() {
        return orderNumb;
    }

    public void setOrderNumb(Integer orderNumb) {
        this.orderNumb = orderNumb;
    }
}

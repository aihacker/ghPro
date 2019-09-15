package wxgh.entity.party.di;

/**
 * Created by cby on 2017/8/14.
 */
public class Topic {
    private Integer total;//总题数
    private Integer right;//对的题数

    public Topic(){}

    public Topic(Integer total, Integer right) {
        this.total = total;
        this.right = right;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }
}

package wxgh.query.score;

import pub.dao.page.Page;

import java.io.Serializable;

/**
 * @author hhl
 * @create 2017-09-04
 **/
public class QueryIntegralGoods extends Page implements Serializable {

    private Integer id;
    private Integer start;
    private Integer num;
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}


package pub.bean;

import java.io.Serializable;

/**
 * Created by ✔ on 2017/2/18.
 */
public class SimplePage implements Serializable {

    private Integer start = 0;//数据开始的位置，默认从第一条开始取
    private Integer num = 10;// 获取数据的行数，默认取10条数据
    private Boolean pageIs = false; //是否分页

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

    public Boolean getPageIs() {
        return pageIs;
    }

    public void setPageIs(Boolean pageIs) {
        this.pageIs = pageIs;
    }
}

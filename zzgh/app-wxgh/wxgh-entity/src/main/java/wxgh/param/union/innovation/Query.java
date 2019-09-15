package wxgh.param.union.innovation;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/30.
 * <p>
 * Date： 2016/8/30
 */
public class Query implements Serializable {

    private Integer start = 0;
    private Integer total = 5;
    private boolean isPage = false; //是否分页

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setIsPage(boolean isPage) {
        this.isPage = isPage;
    }
}

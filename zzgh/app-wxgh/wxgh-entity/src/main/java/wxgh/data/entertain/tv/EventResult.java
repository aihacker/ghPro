package wxgh.data.entertain.tv;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
public class EventResult<T> {

    private List<T> rows;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}

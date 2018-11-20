package pub.dao.query;

import java.io.Serializable;
import java.util.Collections;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-8-9
 */
@SuppressWarnings("unchecked")
public class PagedQueryResult<T> extends QueryResult<T> implements Serializable {

    public static final PagedQueryResult emptyResult;

    static {
        emptyResult = new PagedQueryResult();
        emptyResult.setRows(Collections.emptyList());
    }

    private static final long serialVersionUID = 1L;

    int pageSize;
    int pageNo;
    int rowCount;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    //
    public boolean getHasPrevPage() {
        return pageNo > 1;
    }

    public int getPrevPage() {
        int n = pageNo - 1;
        if (n < 1) n = 1;
        return n;
    }

    public boolean getHasNextPage() {
        return pageNo < getPageCount();
    }

    public int getNextPage() {
        return pageNo + 1;
    }

    public int getPageCount() {
        return (int) Math.ceil(1.0 * rowCount / pageSize);
    }
}

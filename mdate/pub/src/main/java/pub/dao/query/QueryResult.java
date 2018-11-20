package pub.dao.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pub.spring.web.mvc.model.ModelResult;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-9-3
 */
@SuppressWarnings("unchecked")
public class QueryResult<T> implements Serializable, ModelResult {

    private static final long serialVersionUID = 1L;

    public static final QueryResult emptyResult;

    static {
        emptyResult = new QueryResult();
        emptyResult.setRows(Collections.emptyList());
    }

    private Object result;
    private Object extraData;

    public void setValue(Object result) {
        this.result = result;
    }

    // for unique query
    @JsonIgnore
    public T getValue() {
        return (T) result;
    }

    // for list query
    public List<T> getRows() {
        return (List<T>) result;
    }

    public void setRows(List<T> rows) {
        this.result = rows;
    }

    public int getRowCount() {
        return getRows().size();
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    @Override
    @JsonIgnore
    public boolean isDirectResult() {
        return true;
    }

    @JsonIgnore
    public T getScalaValue() {
        List<T> list = (List<T>) result;
        return list == null ? null : list.size() == 0 ? null : list.get(0);
    }
}

package wxgh.param.common.female;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ✔ on 2017/5/4.
 */
public class QueryFemaleLesson implements Serializable {

    private Integer id;
    private Integer indexID;
    private boolean isFirst;
    private boolean isRefresh;
    private boolean isAll = false;
    private Date date;

    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndexID() {
        return indexID;
    }

    public void setIndexID(Integer indexID) {
        this.indexID = indexID;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

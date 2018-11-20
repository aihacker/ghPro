package pub.dao.query;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-28
 */
public class Sort implements Serializable {

    private String sort;
    private String dir;

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public boolean isDesc() {
        return "DESC".equalsIgnoreCase(dir);
    }

    public boolean isAsc() {
        return !isDesc();
    }

    public String getColumn() {
        return sort;
    }

    public void invertDir() {
        if(isAsc()) {
            dir = "DESC";
        }
        else {
            dir = "ASC";
        }
    }
}

package wxgh.data.union.group;

import wxgh.data.common.FileData;

/**
 * Created by Administrator on 2017/7/31.
 */
public class SearchList extends FileData {

    private Integer id;
    private String name;
    private String info;
    private Integer status;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

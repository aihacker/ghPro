package wxgh.data.union.group.account;

import wxgh.data.common.FileData;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ActList extends FileData {

    private Integer id;
    private String name;
    private String info;

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
}

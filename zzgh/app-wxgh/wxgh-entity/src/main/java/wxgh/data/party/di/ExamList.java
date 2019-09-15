package wxgh.data.party.di;

import wxgh.data.common.FileData;

/**
 * Created by Administrator on 2017/7/28.
 */
public class ExamList extends FileData {

    private Integer id;
    private String name;

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
}

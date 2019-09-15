package wxgh.data.party.news;

import wxgh.data.common.FileData;

import java.util.Date;

/**
 * Created by 蔡炳炎 on 2017/8/28.
 */
public class NewsList extends FileData {

    private Integer id;
    private String title;
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}

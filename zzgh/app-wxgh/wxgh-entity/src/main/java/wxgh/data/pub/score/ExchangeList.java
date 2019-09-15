package wxgh.data.pub.score;

import wxgh.data.common.FileData;

/**
 * Created by Administrator on 2017/9/20.
 */
public class ExchangeList extends FileData {

    private Integer id;
    private String name;
    private Integer points;
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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

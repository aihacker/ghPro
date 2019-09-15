package wxgh.data.entertain.place.yuyue;

/**
 * Created by Administrator on 2017/2/24.
 */
public class Site {

    private Integer id;
    private String name;

    public Site() {
    }

    public Site(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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

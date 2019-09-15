package wxgh.param.entertain.place;

import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by ✔ on 2017/1/18.
 */
public class PlaceCateParam extends Page implements Serializable {

    private Integer id;
    private String name;
    private String info;
    private Integer status;
    private String imgPath;

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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

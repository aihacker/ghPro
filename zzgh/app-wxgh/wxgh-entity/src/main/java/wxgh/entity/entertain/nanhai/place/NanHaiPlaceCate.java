package wxgh.entity.entertain.nanhai.place;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2016/11/21
 */
@Entity
@Table(name = "t_nanhai_place_cate")
public class NanHaiPlaceCate implements Serializable {

    private Integer id;
    private String name;
    private String info;
    private Integer status;
    private String imgPath;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "img_path")
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

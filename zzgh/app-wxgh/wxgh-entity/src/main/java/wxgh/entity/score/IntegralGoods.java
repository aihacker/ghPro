package wxgh.entity.score;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ✔ on 2016/10/12.
 */
@Entity
@Table(name = "t_integral_goods")
public class IntegralGoods implements Serializable {

    public static final Integer TYPE_100 = 1; //健步商品
    public static final Integer TYPE_USER = 2; //个人积分兑换商品

    private Integer id;
    private String name;
    private String img;
    private Float points;
    private String info;
    private Date addTime;
    private Integer type;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "img")
    public String getImg() {
        return img;
    }


    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "points")
    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}


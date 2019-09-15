package wxgh.entity.entertain.nanhai.place;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Entity
@Table(name = "t_nanhai_place_site")
public class NanHaiPlaceSite implements Serializable {

    private Integer id;
    private Integer placeId;
    private Integer cateId;
    private String name;
    private Integer numb;
    private Float price;
    private Float score;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "place_id")
    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    @Column(name = "cate_id")
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "numb")
    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    @Column(name = "price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "score")
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}

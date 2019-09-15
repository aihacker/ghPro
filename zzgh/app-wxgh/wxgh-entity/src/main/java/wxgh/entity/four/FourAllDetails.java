package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lyq on 2016/11/18.
 */

@Entity
@Table(name = "t_four_all_details")
public class FourAllDetails implements Serializable {

    private Integer id;
    private Integer fpId;
    private Integer mId;
    private String picture;
    private String contain;//容纳人数
    private String scale;//规格大小
    private String introduction;
    private String remark;

    private List<String> imgs;
    private String path;
    private String thumb;

    private String fpName;//四小项目名称

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "fp_id")
    public Integer getFpId() {
        return fpId;
    }

    @Column(name = "m_id")
    public Integer getmId() {
        return mId;
    }

    @Column(name = "contain")
    public String getContain() {
        return contain;
    }

    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    @Column(name = "scale")
    public String getScale() {
        return scale;
    }

    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}

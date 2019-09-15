package wxgh.param.entertain.place;

import java.io.Serializable;

/**
 * Created by ✔ on 2017/2/7.
 */
public class PlaceSiteParam implements Serializable{

    private Integer placeId;
    private Integer cateId;

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }
}

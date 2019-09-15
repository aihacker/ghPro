package wxgh.param.entertain.place;

import java.io.Serializable;

/**
 * Created by ✔ on 2017/2/7.
 */
public class PlaceImgParam implements Serializable{
    private Integer id;
    private Integer placeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
}

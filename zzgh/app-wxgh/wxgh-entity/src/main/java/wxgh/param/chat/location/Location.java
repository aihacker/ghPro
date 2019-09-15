package wxgh.param.chat.location;

/**
 * @Author xlkai
 * @Version 2016/11/3
 */
public class Location {

    private Double lng;
    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }
}

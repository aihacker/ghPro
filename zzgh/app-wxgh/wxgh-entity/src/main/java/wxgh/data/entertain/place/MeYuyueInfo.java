package wxgh.data.entertain.place;

/**
 * @Author xlkai
 * @Version 2017/1/14
 */
public class MeYuyueInfo extends MeYuyue {

    private Double lat;
    private Double lng;
    private String address;
    private Float price;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}

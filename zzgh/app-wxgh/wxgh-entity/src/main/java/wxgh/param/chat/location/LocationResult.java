package wxgh.param.chat.location;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/3
 */
public class LocationResult {

    private Location location;
    private String formatted_address;
    private String business;
    private List<Poi> pois;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }
}

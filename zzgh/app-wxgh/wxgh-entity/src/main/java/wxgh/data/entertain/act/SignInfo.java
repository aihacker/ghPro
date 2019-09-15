package wxgh.data.entertain.act;

/**
 * Created by Administrator on 2017/8/30.
 */
public class SignInfo {

    private String actId;
    private Integer joinType;
    private Double lat;
    private Double lng;
    private Integer signIs;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public Integer getJoinType() {
        return joinType;
    }

    public void setJoinType(Integer joinType) {
        this.joinType = joinType;
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

    public Integer getSignIs() {
        return signIs;
    }

    public void setSignIs(Integer signIs) {
        this.signIs = signIs;
    }
}

package wxgh.param.entertain.place;



import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/9/5.
 * <p>
 * Date： 2016/9/5
 */
public class PlaceParam extends Page implements Serializable {

    private String city;
    private String province;
    private Integer typeInt; //场地类型ID(羽毛球、篮球、乒乓球)
    private Integer placeType; //场地类型（内部，外部合作）
    private Integer isAd; //是否为广告
    private Integer status;
    private String geohash;
    private Integer id;
    private Integer start;
    private Integer num;

    private Boolean week = false; //是否为本周预约
    private Integer startWeek;
    private Integer endWeek;

    private Integer isThis; // 1位本周 2为下周

    public Integer getIsThis() {
        return isThis;
    }

    public void setIsThis(Integer isThis) {
        this.isThis = isThis;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsAd() {
        return isAd;
    }

    public void setIsAd(Integer isAd) {
        this.isAd = isAd;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(Integer typeInt) {
        this.typeInt = typeInt;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getWeek() {
        return week;
    }

    public void setWeek(Boolean week) {
        this.week = week;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Integer startWeek) {
        this.startWeek = startWeek;
    }

    public Integer getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Integer endWeek) {
        this.endWeek = endWeek;
    }
}

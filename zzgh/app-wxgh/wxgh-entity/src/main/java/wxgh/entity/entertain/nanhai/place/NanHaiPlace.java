package wxgh.entity.entertain.nanhai.place;

import wxgh.entity.entertain.place.PlaceSchedule;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2019/4/22.
 */
@Entity
@Table(name = "t_nanhai_place")
public class NanHaiPlace implements Serializable{
    public static final Integer PLACE_TYPE_DEPT = 1; //内部
    public static final Integer PLACE_TYPE_BUSINESS = 2; //商家

    private Integer id;
    private String title; //地址描述标题
    private Double lat; //纬度
    private Double lng; //经度
    private String url; //地址描述详情页面链接
    private String address; //详细地址
    private String province; //省
    private String city; //城市
    private String phone; //联系电话
    private String typeName; //场地类型名称
    private Integer placeType; //场地类型，1内部场地，2商家场地
    private String geohash; //geohash
    private Float price; //价格 元/h
    private String postcode;//邮政编码
    private String typeInt; //场地类型，对应表为t_place_cate，逗号隔开
    private Integer isAd; //是否为广告封面
    private Integer status; //默认1可用，0不可用，2已删除
    private String info;
    private String startTime;
    private String endTime;

    private String coverImgPath; //封面图片地址

    private List<PlaceSchedule> schedules; //地址排班表

    private String reason;
    private String date;
    private Integer week;

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer type;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoverImgPath() {
        return coverImgPath;
    }

    public void setCoverImgPath(String coverImgPath) {
        this.coverImgPath = coverImgPath;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Column(name = "is_ad")
    public Integer getIsAd() {
        return isAd;
    }

    public void setIsAd(Integer isAd) {
        this.isAd = isAd;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name = "place_type")
    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    @Column(name = "geohash")
    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    @Column(name = "price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "type_int")
    public String getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(String typeInt) {
        this.typeInt = typeInt;
    }

    public List<PlaceSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<PlaceSchedule> schedules) {
        this.schedules = schedules;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

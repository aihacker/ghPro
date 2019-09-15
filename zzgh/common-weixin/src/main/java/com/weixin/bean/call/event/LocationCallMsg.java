package com.weixin.bean.call.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 上报地理位置事件
 * Created by Administrator on 2017/7/13.
 */
public class LocationCallMsg extends EventCallMsg {

    @JsonProperty("Latitude")
    private Double lat;
    @JsonProperty("Longitude")
    private Double lng;
    @JsonProperty("Precision")
    private Double precision;

    public LocationCallMsg() {
        super("LOCATION");
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

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }
}

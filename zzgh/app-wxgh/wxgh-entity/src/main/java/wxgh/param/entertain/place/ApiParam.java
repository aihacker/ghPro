package wxgh.param.entertain.place;

/**
 * Created by Administrator on 2017/3/27.
 */
public class ApiParam {

    private Integer numbWeek;
    private Integer week;
    private Integer dateId;
    private Integer status;
    private Integer placeId;
    private Boolean guding; //是否包含固定场

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Boolean getGuding() {
        return guding;
    }

    public void setGuding(Boolean guding) {
        this.guding = guding;
    }

    public Integer getNumbWeek() {
        return numbWeek;
    }

    public void setNumbWeek(Integer numbWeek) {
        this.numbWeek = numbWeek;
    }
}

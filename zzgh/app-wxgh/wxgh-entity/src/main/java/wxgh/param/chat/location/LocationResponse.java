package wxgh.param.chat.location;

/**
 * @Author xlkai
 * @Version 2016/11/3
 */
public class LocationResponse {

    private Integer status;
    private LocationResult result;
    private String sematic_description;
    private Integer cityCode;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocationResult getResult() {
        return result;
    }

    public void setResult(LocationResult result) {
        this.result = result;
    }

    public String getSematic_description() {
        return sematic_description;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }
}

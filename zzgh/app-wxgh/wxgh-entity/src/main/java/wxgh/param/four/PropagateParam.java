package wxgh.param.four;

import pub.dao.page.Page;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/9/5.
 * <p>
 * Date： 2016/9/5
 */
public class PropagateParam extends Page implements Serializable {

    private String userid;
    private Integer status;
    private Integer deviceStatus;
    private Integer start;
    private Integer num;
    private Integer id;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}

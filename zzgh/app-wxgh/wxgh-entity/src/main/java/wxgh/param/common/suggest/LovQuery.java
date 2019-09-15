package wxgh.param.common.suggest;

import java.io.Serializable;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:55
 *----------------------------------------------------------
 */
public class LovQuery implements Serializable {

    private Integer status = 1;
    private String userid;
    private Integer sugId;
    private Integer type;

    public LovQuery() {
    }

    public LovQuery(Integer sugId, String userid, Integer type) {
        this.sugId = sugId;
        this.userid = userid;
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}



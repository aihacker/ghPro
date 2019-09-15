package wxgh.param.four;

import java.io.Serializable;


public class FourAllDetailsParam implements Serializable {

    private Integer mid;
    private Integer fpid;

    public FourAllDetailsParam(){

    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getFpid() {
        return fpid;
    }

    public void setFpid(Integer fpid) {
        this.fpid = fpid;
    }
}

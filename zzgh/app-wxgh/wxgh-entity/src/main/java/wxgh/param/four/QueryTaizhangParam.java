package wxgh.param.four;

import java.io.Serializable;

/**
 * Created by WIN on 2016/8/29.
 */
public class QueryTaizhangParam implements Serializable {

    private Integer fpid;
    private Integer fpcid;

    public Integer getFpid() {
        return fpid;
    }

    public void setFpid(Integer fpid) {
        this.fpid = fpid;
    }

    public Integer getFpcid() {
        return fpcid;
    }

    public void setFpcid(Integer fpcid) {
        this.fpcid = fpcid;
    }
}

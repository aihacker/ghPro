package wxgh.app.consts;

/**
 * Created by Administrator on 2017/7/19.
 */
public enum Status {

    APPLY(0), //审核中
    NORMAL(1), //审核通过
    FAILED(2); //审核失败

    private Integer status;

    Status(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}

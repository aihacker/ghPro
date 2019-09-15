package wxgh.data.tv;

/**
 * Created by Administrator on 2017/6/30.
 */
public class Result<T> {

    private Boolean ok;
    private String msg;
    private T data;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

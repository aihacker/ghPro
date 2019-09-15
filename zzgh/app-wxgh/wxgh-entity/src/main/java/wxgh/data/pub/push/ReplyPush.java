package wxgh.data.pub.push;

/**
 * Created by Administrator on 2017/8/10.
 */
public class ReplyPush extends WxPush {

    private String toUser;
    private Integer status;

    public ReplyPush(String toUser, Integer status) {
        this.toUser = toUser;
        this.status = status;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReplyPush{" +
                "toUser='" + toUser + '\'' +
                ", status=" + status +
                '}';
    }
}

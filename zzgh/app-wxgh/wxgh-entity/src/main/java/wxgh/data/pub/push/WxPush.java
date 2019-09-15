package wxgh.data.pub.push;

/**
 * Created by Administrator on 2017/8/10.
 */
public class WxPush {

    private Integer agentId = 0;
    private String host;
    private String msg;

    public WxPush() {
    }

    public WxPush(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

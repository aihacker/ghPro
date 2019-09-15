package wxgh.data.pub.push;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
public class Push {

    private Integer agentId;
    private List<String> tousers;
    private List<String> totags;
    private List<String> topartys;
    private Boolean all = false; //是否推送给全部用户

    public static Push user(List<String> users, Integer agentId) {
        Push push = new Push();
        push.setTousers(users);
        push.setAgentId(agentId);
        return push;
    }

    public static Push tag(List<String> tags, Integer agentId) {
        Push push = new Push();
        push.setTotags(tags);
        push.setAgentId(agentId);
        return push;
    }

    public static Push party(List<String> partys, Integer agentId) {
        Push push = new Push();
        push.setTopartys(partys);
        push.setAgentId(agentId);
        return push;
    }

    public static Push all(Integer agentId) {
        Push push = new Push();
        List<String> tousers = new ArrayList<>();
        tousers.add("@all");
        push.setTousers(tousers);
        push.setAgentId(agentId);
        return push;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public List<String> getTousers() {
        return tousers;
    }

    public void setTousers(List<String> tousers) {
        this.tousers = tousers;
    }

    public List<String> getTotags() {
        return totags;
    }

    public void setTotags(List<String> totags) {
        this.totags = totags;
    }

    public List<String> getTopartys() {
        return topartys;
    }

    public void setTopartys(List<String> topartys) {
        this.topartys = topartys;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }
}

package com.weixin.bean.token;

/**
 * Created by Administrator on 2017/8/1.
 */
public class TokenVal {

    public static final String VAL_TOKEN = "token";
    public static final String VAL_TICKET = "ticket";
    public static final String VAL_CONTACT_TICKET = "contact_ticket";

    private String name;
    private String val;
    private Integer agentid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    @Override
    public String toString() {
        return "TokenVal{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", agentid=" + agentid +
                '}';
    }
}

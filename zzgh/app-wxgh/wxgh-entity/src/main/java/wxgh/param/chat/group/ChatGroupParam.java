package wxgh.param.chat.group;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/21.
 */
public class ChatGroupParam extends Page{

    private Integer type;
    private String userid;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

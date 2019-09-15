package wxgh.param.chat;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ChatObject implements Serializable {

    private String id; //聊天群组ID
    private String userid; //用户ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return this.id + "-" + this.userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatObject chatObject = (ChatObject) o;

        if (!id.equals(chatObject.id)) return false;
        return userid.equals(chatObject.userid);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userid.hashCode();
        return result;
    }
}

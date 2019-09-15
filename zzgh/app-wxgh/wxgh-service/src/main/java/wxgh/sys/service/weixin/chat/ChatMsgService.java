package wxgh.sys.service.weixin.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.chat.msg.MsgList;
import wxgh.entity.chat.ChatMsg;
import wxgh.param.chat.chatmsg.ListParam;
import wxgh.sys.dao.chat.ChatMsgDao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Service
@Transactional(readOnly = true)
public class ChatMsgService {

    public List<MsgList> listMsg(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_chat_msg m")
                .field("m.id, m.chat_id, m.send_time, m.msg_type, m.content")
                .field("m.userid, u.name as username, u.avatar")
                .join("t_user u", "u.userid = m.userid")
                .order("m.send_time", Order.Type.DESC);

        if (param.getGroupId() != null) {
            sql.where("m.group_id = :groupId");
        }
        return pubDao.queryPage(sql, param, MsgList.class);
    }

    @Transactional
    public void save(ChatMsg chatMsg) {
        chatMsgDao.save(chatMsg);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ChatMsgDao chatMsgDao;

}

package wxgh.sys.service.weixin.chat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.entity.chat.ChatUser;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/3.
 */
@Service
@Transactional(readOnly = true)
public class ChatUserService {

    @Transactional
    public void addUser(ChatUser user) {
        String seSql = "select id from t_chat_user where userid = ? and group_id = ?";
        Integer id = pubDao.query(Integer.class, seSql, user.getUserid(), user.getGroupId());
        if (id != null) {
            throw new ValidationError("对不起，你已经在群组哦");
        }
        user.setAddTime(new Date());
        SQL sql = new SQL.SqlBuilder()
                .field("userid, group_id, add_time")
                .value(":userid, :groupId, :addTime")
                .insert("chat_user")
                .build();
        pubDao.executeBean(sql.sql(), user);
    }

    @Autowired
    private PubDao pubDao;
}

package wxgh.sys.service.weixin.pub;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.WxConverUser;
import wxgh.entity.pub.WxConversation;
import wxgh.sys.dao.pub.ConverUserDao;
import wxgh.sys.dao.pub.ConversationDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
@Transactional(readOnly = true)
public class ConversationService {

    @Transactional
    public void save(WxConversation conversation, List<String> userids) {

        String sql = "select id from t_wx_conversation where chatid=? limit 1";
        Integer id = pubDao.query(Integer.class, sql, conversation.getChatid());
        if (id == null) {
            conversationDao.save(conversation);
        }

        addUsers(conversation, userids);
    }

    private void addUsers(WxConversation conversation, List<String> userids) {
        String sql2 = "select id from t_wx_conver_user where userid=? and conver_id=? limit 1";
        for (String userid : userids) {
            Integer mid = pubDao.query(Integer.class, sql2, userid, conversation.getChatid());
            if (mid == null) {
                WxConverUser user = new WxConverUser();
                user.setUserid(userid);
                user.setAddTime(new Date());
                user.setConverId(conversation.getChatid());
                user.setType(1);
                converUserDao.save(user);
            }
        }
    }

    @Transactional
    public void update(WxConversation conversation, List<String> addUsers, List<String> delUsers) {
        if (!TypeUtils.empty(conversation.getName()) || !TypeUtils.empty(conversation.getOwner())) {
            conversationDao.update(conversation, "chatid");
        }

        addUsers(conversation, addUsers);

        String sql = "delete from t_wx_conver_user where userid=?";
        pubDao.executeBatch(sql, delUsers);
//        for (String userid : delUsers) {
//            pubDao.execute(sql, userid);
//        }
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private ConverUserDao converUserDao;

}

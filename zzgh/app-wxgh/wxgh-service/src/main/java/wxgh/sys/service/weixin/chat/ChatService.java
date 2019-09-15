package wxgh.sys.service.weixin.chat;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.chat.ChatInfo;
import wxgh.data.chat.ChatList;
import wxgh.data.chat.ChatModelInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class ChatService {

    /**
     * 获取群组及功能列表
     *
     * @param id
     * @return
     */
    public ChatInfo getGroup(Integer id, Integer type) {
        SQL.SqlBuilder groupSql = new SQL.SqlBuilder()
                .field("id, group_id, name")
                .where("id = ?");
        if (type != null && type.intValue() == 1) {
            groupSql.field("1 as type")
                    .table("group");
        } else {
            groupSql.field("type")
                    .table("chat_group");
        }
        ChatInfo chatInfo = pubDao.query(ChatInfo.class, groupSql.build().sql(), id);

        if (chatInfo != null) {
            SQL sql = new SQL.SqlBuilder()
                    .field("m.name, m.url")
                    .sys_file("m.icon")
                    .where("m.type = ?")
                    .select("chat_group_model m").build();
            List<ChatModelInfo> models = pubDao.queryList(ChatModelInfo.class, sql.sql(), chatInfo.getType());

            chatInfo.setModels(models);
        }
        return chatInfo;
    }

    public Map<Integer, List<ChatList>> listGroup(int[] types, String userid) {
        String type = "";
        for (int i = 0, len = types.length; i < len; i++) {
            if (i > 0) type += ",";
            type += types[i];
        }
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group g")
                .field("g.*")
                .sys_file("g.avatar")
                .join("chat_user u", "g.group_id = u.group_id")
                .where("g.type in (" + type + ")")
                .where("u.userid = ?")
                .build();
        List<ChatList> groups = pubDao.queryList(ChatList.class, sql.sql(), userid);

        if (!TypeUtils.empty(groups)) {
            Map<Integer, List<ChatList>> map = new HashMap<>();
            for (ChatList g : groups) {
                List<ChatList> tmp = map.getOrDefault(g.getType(), null);
                if (tmp == null) {
                    tmp = new ArrayList<>();
                    tmp.add(g);
                    map.put(g.getType(), tmp);
                } else {
                    map.get(g.getType()).add(g);
                }
            }
            return map;
        }
        return null;
    }

    @Autowired
    private PubDao pubDao;

}

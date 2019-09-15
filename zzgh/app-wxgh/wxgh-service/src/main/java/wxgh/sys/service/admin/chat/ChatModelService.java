package wxgh.sys.service.admin.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.chat.ChatModelInfo;
import wxgh.entity.chat.ChatGroupModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
@Service
@Transactional(readOnly = true)
public class ChatModelService {

    public List<ChatModelInfo> listModel(Integer type) {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group_model m")
                .field("m.id, m.name, m.info, m.url")
                .sys_file("m.icon")
                .where("m.type = ?")
                .build();
        return pubDao.queryList(ChatModelInfo.class, sql.sql(), type);
    }

    @Transactional
    public void addModel(ChatGroupModel model) {
        pubDao.executeBean(JdbcSQL.save(ChatGroupModel.class), model);
    }

    @Transactional
    public void delModel(String id) {
        pubDao.execute(SQL.deleteByIds("chat_group_model", id));
    }

    @Transactional
    public void updateModel(ChatGroupModel model) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group_model");
        if (model.getName() != null) {
            sql.set("name = :name");
        }
        if (model.getInfo() != null) {
            sql.set("info = :info");
        }
        if (model.getIcon() != null) {
            sql.set("icon = :icon");
        }
        if (model.getUrl() != null) {
            sql.set("url = :url");
        }
        sql.where("id = :id");
        pubDao.executeBean(sql.build().sql(), model);
    }

    @Autowired
    private PubDao pubDao;

}

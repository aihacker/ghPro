package wxgh.sys.service.admin.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.pub.NameValue;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.di.GroupUser;
import wxgh.entity.party.di.Notice;
import wxgh.param.party.di.notice.NoticeParam;

import java.util.List;

/**
 * Created by Sheng on 2017/8/17.
 */
@Service
public class NoticeService {

    @Autowired
    private PubDao pubDao;

    public List<NameValue> getGroupList() {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group")
                .field("group_id as value, name")
                .where("type = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), ChatGroup.TYPE_DI);
    }

    public List<NameValue> getExamList(){
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id as value, e.name")
//                .join("di_plan p", "p.id = e.plan_id")
                .join("chat_group g", "g.group_id = e.group_id")
//                .where("p.type = ?")
                .where("g.type = ?")
                .select()
                .build();
//        DiPlan.Type.DI.getValue(),
        return pubDao.queryList(NameValue.class, sql.sql(),  ChatGroup.TYPE_DI);
    }

    public List<NameValue> getPartyGroupList() {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group")
                .field("group_id as value, name")
                .where("type = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), ChatGroup.TYPE_PARTY);
    }

    public List<GroupUser> getGroupUserList(Integer groupId) {
        SQL sql = new SQL.SqlBuilder()
                .select("t_di_group_user tgu")
                .where("tgu.group_id = ?")
                .build();
        return pubDao.queryList(GroupUser.class, sql.sql(), groupId);
    }

    public void addNotice(Notice notice) {
        String sql = "insert into t_di_notice(title,content,image,group_id,add_time,author,link) " +
                "value(:title,:content,:image,:groupId,:addTime,:author,:link)";
        pubDao.executeBean(sql, notice);
    }

    public Integer getNoticeCount() {
        SQL sql = new SQL.SqlBuilder()
                .select("t_di_notice")
                .count("*")
                .build();
        return pubDao.queryInt(sql.sql());
    }

    public List<NoticeParam> getNoticeList(NoticeParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_notice t")
                .field("t.id,t.title,t.content,t.image,t.group_id,t.add_time,t.author");
        return pubDao.queryPage(sql, param, NoticeParam.class);

    }
}

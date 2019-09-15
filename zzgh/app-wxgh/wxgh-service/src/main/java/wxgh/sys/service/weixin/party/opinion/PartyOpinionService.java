package wxgh.sys.service.weixin.party.opinion;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Where;
import pub.dao.page.Page;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.opinion.OpinionData;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.party.opinion.PartyOpinion;

import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2018-01-23  19:07
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class PartyOpinionService {

    @Autowired
    private PubDao pubDao;

    public OpinionData get(Integer id){
        String sql = "select * from t_party_opinion where id = ?";
        OpinionData partyOpinion = pubDao.query(OpinionData.class, sql, id);

        // 全体党员
        if(partyOpinion.getType().equals(1)){
            partyOpinion.setObject("全体党员");
        }else if(partyOpinion.getType().equals(2)){  // 特定群

            if(!TypeUtils.empty(partyOpinion.getGroupIds())){
                String gSql = new SQL.SqlBuilder()
                        .table("chat_group")
                        .field("name")
                        .where("id in ("+partyOpinion.getGroupIds()+")")
                        .select().build().sql();
                List<String> list = pubDao.queryList(String.class, gSql);
                partyOpinion.setObject(TypeUtils.listToStr(list));
            }
        }

        return partyOpinion;
    }

    public List<PartyOpinion> list(Page page){

        String userid = UserSession.getUserid();

        String sqlUser = "select userid from t_party_dept_user where userid = ?";
        String u = pubDao.query(String.class, sqlUser, userid);

        String sqlGroup = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("g.id")
                .join("chat_group g", "g.group_id = cu.group_id")
                .where("cu.userid = ?")
                .where("g.type = ?")
                .groupBy("g.group_id")
                .select()
                .build().sql();
        List<Integer> gId = pubDao.queryList(Integer.class, sqlGroup, userid, ChatGroup.TYPE_PARTY_GROUP);

        if(u == null && gId.size() == 0)
            return null;

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_opinion op")
                .field("*");
        if(u != null)
            sql.where("type = 1", Where.Logic.OR);

        for (Integer id : gId)
            sql.where("(type = 2 and find_in_set("+id+", group_ids))", Where.Logic.OR);
        return pubDao.queryPage(sql, page, PartyOpinion.class);
    }

    /**
     * 验证是否有权限发布  （全体党员或者指定群体）
     * @param id
     * @return
     */
    public boolean v(Integer id){
        PartyOpinion partyOpinion = pubDao.query(PartyOpinion.class, "select * from t_party_opinion where id = ?", id);
        if(partyOpinion == null)
            return false;
        String userid = UserSession.getUserid();
        if(partyOpinion.getType() != null)
            // 全体党员
            if(partyOpinion.getType().equals(1)){

                String sqlUser = "select userid from t_party_dept_user where userid = ?";
                String u = pubDao.query(String.class, sqlUser, userid);
                if(u != null)
                    return true;

            }else if(partyOpinion.getType().equals(2)){

                if(TypeUtils.empty(partyOpinion.getGroupIds()))
                    return false;

                String sqlGroup = new SQL.SqlBuilder()
                        .table("chat_user cu")
                        .field("g.id")
                        .join("chat_group g", "g.group_id = cu.group_id")
                        .where("cu.userid = ?")
                        .where("g.type = ?")
                        .where("g.id in ("+partyOpinion.getGroupIds()+")")
                        .groupBy("g.group_id")
                        .select()
                        .build().sql();
                List<Integer> gId = pubDao.queryList(Integer.class, sqlGroup, userid, ChatGroup.TYPE_PARTY_GROUP);
                if(gId.size() != 0)
                    return true;

            }
        return false;
    }

}

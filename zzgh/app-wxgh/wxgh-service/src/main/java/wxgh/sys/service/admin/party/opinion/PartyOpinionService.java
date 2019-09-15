package wxgh.sys.service.admin.party.opinion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.Page;
import wxgh.data.pub.NameValue;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.party.opinion.PartyOpinion;

import java.util.Date;
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
 * @Date 2018-01-23  16:33
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class PartyOpinionService {

    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer save(PartyOpinion partyOpinion){

        if(partyOpinion.getId() == null){
            partyOpinion.setAddTime(new Date());
            partyOpinion.setTotal(0);
            String sql = new SQL.SqlBuilder()
                    .table("party_opinion")
                    .field("title, info, total, add_time, type, group_ids")
                    .value(":title, :info, :total, :addTime, :type, :groupIds")
                    .insert().build().sql();
            return pubDao.insertAndGetKey(sql, partyOpinion);
        }else{
            String sql = new SQL.SqlBuilder()
                    .table("party_opinion")
                    .set("title = :title")
                    .set("info = :info")
                    .where("id = :id")
                    .update()
                    .build().sql();
            pubDao.executeBean(sql, partyOpinion);
        }
        return null;
    }

    public List<PartyOpinion> list(Page page){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_opinion")
                .field("*")
                .select();
        return pubDao.queryPage(sql, page, PartyOpinion.class);
    }

    public List<NameValue> listGroup(){
        String sql = new SQL.SqlBuilder()
                .table("chat_group")
                .field("id as value, name")
                .where("type = ?")
                .select()
                .build().sql();
        return pubDao.queryList(NameValue.class, sql, ChatGroup.TYPE_PARTY_GROUP);
    }

}

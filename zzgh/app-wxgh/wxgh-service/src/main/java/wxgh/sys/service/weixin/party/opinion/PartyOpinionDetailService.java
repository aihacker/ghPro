package wxgh.sys.service.weixin.party.opinion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.web.ServletUtils;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.opinion.OpinionDetailData;
import wxgh.entity.party.opinion.PartyOpinionDetail;
import wxgh.param.party.opinion.OpinionListParam;

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
 * @Date 2018-01-23  19:36
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class PartyOpinionDetailService {

    @Autowired
    private PubDao pubDao;

    public List<PartyOpinionDetail> list(OpinionListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_opinion_detail")
                .field("*")
                .where("opinion_id = :id")
                .select();
        return pubDao.queryPage(sql, param, PartyOpinionDetail.class);
    }

    public OpinionDetailData get(Integer id){
        String sql = new SQL.SqlBuilder()
                .table("party_opinion_detail od")
                .join("party_opinion op", "op.id = od.opinion_id")
                .join("user u", "u.userid = od.userid")
                .field("od.*, op.title as opinionTitle")
                .field("u.name as username")
                .where("od.id = ?")
                .select().build().sql();
        return pubDao.query(OpinionDetailData.class, sql, id);
    }

    @Transactional
    public void save(PartyOpinionDetail partyOpinionDetail){
        if(partyOpinionDetail.getUserid() == null)
            partyOpinionDetail.setUserid(UserSession.getUserid());
        partyOpinionDetail.setAddTime(new Date());
        partyOpinionDetail.setView(0);
        String sql = new SQL.SqlBuilder()
                .table("party_opinion_detail")
                .field("opinion_id, userid, title, content, view, add_time")
                .value(":opinionId, :userid, :title, :content, :view, :addTime")
                .insert().build().sql();
        pubDao.executeBean(sql, partyOpinionDetail);

        String uSql = "update t_party_opinion set total=total+1 where id = ?";
        pubDao.execute(uSql, partyOpinionDetail.getOpinionId());
    }

    @Transactional
    public void view(Integer id){
        //更新浏览记录
        Boolean isSee = (Boolean) ServletUtils.getSession().getAttribute("party_opinion_see_session_" + id);
        if (isSee == null || !isSee) {
            updateNum(id);
            ServletUtils.getSession().setAttribute("party_opinion_see_session_" + id, true);
        }
    }

    @Transactional
    private void updateNum(Integer id){
        String sql = "update t_party_opinion_detail set view=view+1 where id = ?";
        pubDao.execute(sql, id);
    }

}

package wxgh.sys.service.weixin.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.party.ArticleList;
import wxgh.entity.party.PartyArticle;
import wxgh.param.party.ArticleParam;
import wxgh.param.party.PartyArticleParam;
import wxgh.param.party.PartyParam;
import wxgh.sys.dao.party.PartyArticleDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/30.
 * <p>
 * Date： 2016/8/30
 */
@Service
@Transactional(readOnly = true)
public class PartyArticleService {

    @Autowired
    private PartyArticleDao partyArticleDao;

    @Autowired
    private PubDao generalDao;

    public List<ArticleList> getPartys(ArticleParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_article p")
                .field("p.id, p.title, p.brief_info as info, p.content, p.add_time")
                .sys_file("p.img")
                .order("p.add_time", Order.Type.DESC)
                .where("p.type = :type");
        return generalDao.queryPage(sql, param, ArticleList.class);
    }

    public PartyArticle getParty(PartyParam query) {
        return partyArticleDao.getParty(query);
    }

    public List<PartyArticle> reFresh(PartyParam query) {
        return partyArticleDao.reFresh(query);
    }

    public List<PartyArticle> getMore(PartyParam query) {
        return partyArticleDao.getMore(query);
    }

    public Integer getCount(Integer type) {
        String sql = "select count(*) from t_party_article where type=?";
        return generalDao.query(Integer.class, sql, type);
    }

    public PartyArticle getdata(PartyArticleParam partyArticleQuery) {
        return partyArticleDao.getdata(partyArticleQuery);
    }

    @Transactional
    public Integer updateData(PartyArticleParam partyArticleQuery) {
        return partyArticleDao.updateData(partyArticleQuery);
    }

    @Transactional
    public void save(PartyArticle partyArticle) {
        this.partyArticleDao.save(partyArticle);
    }

    @Transactional
    public void delete(PartyArticle partyArticle) {
        partyArticleDao.delete(partyArticle);
    }

    public String getPartyContent(Integer id) {
        String sql = "select content from t_party_article where id = ?";
        return generalDao.query(String.class, sql, id);
    }

    @Transactional
    public void updateContent(Integer id, String content) {
        String sql = "update t_party_article set content=? where id=?";
        generalDao.execute(sql, content, id);
    }

    public PartyArticle get(Integer id) {
        return partyArticleDao.get(id);
    }

    @Transactional
    public void updateSeeNumb(Integer id) {
        String sql = "update t_party_article set see_numb=see_numb+1 where id=?";
        generalDao.execute(sql, id);
    }

    @Transactional
    public void updateZanNumb(Integer id, String type) {
        String sql;
        if (type.equals("add")) {
            sql = "update t_party_article set zan_numb=zan_numb+1 where id=?";
        } else {
            sql = "update t_party_article set zan_numb=zan_numb-1 where id=?";
        }
        generalDao.execute(sql, id);
    }
}

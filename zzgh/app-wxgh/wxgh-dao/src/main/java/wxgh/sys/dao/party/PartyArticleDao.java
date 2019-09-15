package wxgh.sys.dao.party;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.PartyArticle;
import wxgh.param.party.PartyArticleParam;
import wxgh.param.party.PartyParam;

import java.util.List;

/**
 * Created by WIN on 2016/8/24.
 */
@Repository
public class PartyArticleDao extends MyBatisDao<PartyArticle>{

    public List<PartyArticle> getPartys(PartyParam query) {
        return selectList("getPartys",query);
    }

    public PartyArticle getParty(PartyParam query) {
        return selectOne("getParty",query);
    }

    public List<PartyArticle> reFresh(PartyParam query) {
        return selectList("refresh",query);
    }

    public List<PartyArticle> getMore(PartyParam query) {
        return selectList("getMore",query);
    }

    public PartyArticle getdata(PartyArticleParam partyArticleQuery) {
        return selectOne("getdata", partyArticleQuery);
    }

    public Integer updateData(PartyArticleParam partyArticleQuery) {
        return getSqlSession().update("wxgh.entity.party.PartyArticle.updateData", partyArticleQuery);
    }

    public Integer updateArticle(PartyArticle partyArticle) {
        return getSqlSession().update("wxgh.entity.party.PartyArticle.partyArticle", partyArticle);
    }
}

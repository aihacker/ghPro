package wxgh.sys.dao.common.suggest;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.suggest.UserSuggest;
import wxgh.param.common.suggest.UserSuggestQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 17:05
 *----------------------------------------------------------
 */
@Repository
public class UserSuggestDao extends MyBatisDao<UserSuggest> {

    
    public List<UserSuggest> getSuggest(UserSuggestQuery query) {
        return selectList("xlkai_getSuggests", query);
    }

    
    public UserSuggest getSuggest(Integer id) {
        return selectOne("xlkai_getOneSuggest", id);
    }

    
    public List<UserSuggest> getSuggests(UserSuggestQuery query) {
        return selectList("getSuggests", query);
    }

    
    public Integer getCount(UserSuggestQuery userSuggestQuery) {
        return selectOne("getCount", userSuggestQuery);
    }

    
    public Integer shenhe(UserSuggestQuery userSuggestQuery) {
        return getSqlSession().update("shenhe", userSuggestQuery);
    }

    
    public UserSuggest getOneSuggest(Integer id) {
        return selectOne("getOneSuggest", id);
    }

    
    public Integer del(Integer id) {
        return getSqlSession().delete("del", id);
    }

    
    public List<UserSuggest> applyListRefresh(UserSuggestQuery query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<UserSuggest> applyListMore(UserSuggestQuery query) {
        return selectList("applyListMore", query);
    }
    
}


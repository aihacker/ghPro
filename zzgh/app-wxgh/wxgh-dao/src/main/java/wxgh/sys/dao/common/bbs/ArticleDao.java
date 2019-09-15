package wxgh.sys.dao.common.bbs;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.Article;
import wxgh.param.common.bbs.ArticleParam;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 14:33
 *----------------------------------------------------------
 */
@Repository
public class ArticleDao extends MyBatisDao<Article> {

    public List<Article> getArticles(ArticleParam query) {
        return selectList("xlkai_listArticles", query);
    }

    public Integer countArticles(ArticleParam query) {
        Integer count = selectOne("xlkai_countArticles", query);
        return count == null ? 0 : count;
    }
    
}


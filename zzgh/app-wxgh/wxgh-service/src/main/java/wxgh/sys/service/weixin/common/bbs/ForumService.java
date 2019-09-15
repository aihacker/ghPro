package wxgh.sys.service.weixin.common.bbs;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.utils.StrUtils;
import wxgh.entity.common.Article;
import wxgh.param.common.bbs.ArticleParam;

import java.util.ArrayList;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 09:44
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class ForumService {

    @Autowired
    private PubDao pubDao;

    public List<Article> getAreaList(String atlName, Integer deptid) {
        String atlNamer = atlName.replace("'", "");

//        String where = "AND INSTR(a.atl_name,'" + atlNamer + "')";

        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_article a")
                .field("a.*, (select COUNT(*) from t_comment c WHERE c.atl_id = a.atl_id)")
                .where("a.isdel = '1' AND FIND_IN_SET(deptid, query_dept_child(query_dept_company_id(" + deptid + "))) > 0 ")
                .order("status DESC,create_time DESC");

        if (atlNamer != null || atlNamer != "") {
            builder.where("INSTR(a.atl_name,'" + atlNamer + "')");
        }

        /*
        String sql = "SELECT a.*,(select COUNT(*) from t_comment c " +
                "WHERE c.atl_id = a.atl_id)" +
                " as commNum from t_article a " +
                "WHERE a.isdel = '1' AND " +
                "FIND_IN_SET(deptid, query_dept_child(query_dept_company_id(" + deptid + "))) > 0 ";
        if (atlNamer != null || atlNamer != "") {
            sql += where + " ORDER BY status DESC,create_time DESC;";
        } else {
            sql += " ORDER BY status DESC,create_time DESC;";
        }
        */

        List<Article> atlList = pubDao.queryList(Article.class, builder.build().sql());
        return atlList;
    }

    private List<Article> getAll(ArticleParam articleParam){

//        SQL.SqlBuilder builder = new SQL.SqlBuilder()
//                .table("t_article a")
//                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, a.file_ids)) paths")
//                .field("a.*,(select COUNT(*) from t_comment c,t_user u WHERE c.user_id = u.userid AND  c.isdel = '1' AND c.atl_id = a.atl_id) as commNum")
//                .where("a.isdel = '1'")
//                .where("FIND_IN_SET(a.deptid, query_dept_child(query_dept_company_id(:deptid))) > 0")
//                .order("a.status, a.create_time", Order.Type.DESC);

        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_article a")
                .field("a.*,(select COUNT(*) from t_comment c,t_user u WHERE c.user_id = u.userid AND  c.isdel = '1' AND c.atl_id = a.atl_id) as commNum")
                .where("a.isdel = '1'")
                .where("FIND_IN_SET(a.deptid, query_dept_child(query_dept_company_id2( :deptid ))) > 0")
                .order("a.status", Order.Type.DESC)
                .order("a.create_time", Order.Type.DESC);

//        SELECT a.*,(select COUNT(*) from t_comment c,t_user u WHERE c.user_id = u.userid AND  c.isdel = '1' AND c.atl_id = a.atl_id) as commNum from t_article a
//        WHERE a.isdel = '1' AND FIND_IN_SET(a.deptid, query_dept_child(query_dept_company_id2(#{deptid}))) > 0
//        ORDER BY a.status DESC,a.create_time DESC;

        if(!TypeUtils.empty(articleParam.getSearchKey()))
            builder.where("a.atl_name like '%"+articleParam.getSearchKey()+"%'");

        return pubDao.queryPage(builder, articleParam, Article.class);
    }

    public List<Article> getAllArticle(ArticleParam articleParam) {
        List<Article> articles = getAll(articleParam);

        if (articles != null && articles.size() > 0) {
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                if (article != null && !StrUtils.empty(article.getFileIds())) {
                    List<String> fileIds = new ArrayList<>();
                    String[] ids = article.getFileIds().split(",");
                    for (String id : ids) {
                        fileIds.add(id.trim());
                    }
//                    articles.get(i).setFileList(fileIds);
                }
            }
        }
        return articles;
    }
    
}


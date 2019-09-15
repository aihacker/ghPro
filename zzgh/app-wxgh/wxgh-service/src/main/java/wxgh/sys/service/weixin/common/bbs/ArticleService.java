package wxgh.sys.service.weixin.common.bbs;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.common.FileData;
import wxgh.data.common.bbs.BbsList;
import wxgh.entity.common.Article;
import wxgh.entity.common.Zan;
import wxgh.param.common.bbs.ArticleParam;
import wxgh.sys.dao.common.bbs.ArticleDao;

import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-26 10:56
 * ----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class ArticleService {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ArticleDao articleDao;

    public List<BbsList> listBbs(ArticleParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("article a")
                .field("a.atl_id as id, a.atl_name as name, a.atl_content as content, a.create_time")
                .field("(if(ISNULL(a.file_ids), '', (select file_path from t_sys_file where FIND_IN_SET(file_id,a.file_ids) limit 1))) as image")
                .order("a.create_time", Order.Type.DESC);
        if (param.getStatus() != null) {
            sql.where("a.status = :status");
        }
        if (param.getSearchKey() != null) {
            sql.where("a.atl_name like concat('%', :searchKey , '%') or a.atl_content like concat('%', :searchKey , '%')");
//            sql.where("(a.atl_name LIKE '%" + param.getSearchKey() + "%' OR a.atl_content LIKE '%" + param.getSearchKey() + "%')");
        }
        return pubDao.queryPage(sql, param, BbsList.class);
    }

    public List<String> countApply() {
        String countSql = "select CONCAT(count(*),',',(select count(*) from t_article where status = 1), ',', (select count(*) from t_article where status = 2), ',', (select count(*) from t_article where status = 3)) from t_article where `status` = 0";
        String counts = pubDao.query(String.class, countSql);
        return TypeUtils.strToList(counts);
    }

    @Transactional
    public void delete(Integer id) {
        articleDao.delete(id);
    }

    @Transactional
    public void save(Article article) {
        article.setCreateTime(new Date());
        article.setCommNum(0);
        articleDao.save(article);
    }

    public Article get(Integer id) {
        String sql = "select a.*, u.`name` as userName,u.avatar,d.name as deptname from t_article a\n" +
                "join t_user u on a.user_id = u.userid\n" +
                "left join t_dept d on a.deptid = d.deptid where atl_id=?";
        Article article = pubDao.query(Article.class, sql, id);
        if (article != null && !TypeUtils.empty(article.getFileIds())) {
            SQL fileSql = new SQL.SqlBuilder()
                    .table("sys_file")
                    .field("file_path as path, thumb_path as thumb")
                    .where("find_in_set(file_id, ?)")
                    .build();
            List<FileData> files = pubDao.queryList(FileData.class, fileSql.sql(), article.getFileIds());
            article.setFileList(files);
        }
        return article;
    }

    public Integer getNum(Integer id, String colum) {
        String sql = "select " + colum + " from t_article where atl_id=?";
        Integer num = pubDao.query(Integer.class, sql, id);
        return num == null ? 0 : num;
    }

    public List<Article> getArticles(ArticleParam query) {
        return articleDao.getArticles(query);
    }

    public Integer countArticles(ArticleParam query) {
        return articleDao.countArticles(query);
    }

    public Integer isZan(Integer id, String userid) {
        String sql = "select id from t_zan where zan_id=? and userid=? and zan_type=?";
        Integer zanId = pubDao.query(Integer.class, sql, id, userid, Zan.TYPE_ARTICLE);
        return zanId;
    }

    @Transactional
    public void updateCommNum(Integer id) {
        String sql = "update t_article set comm_num=comm_num+1 where atl_id=?";
        pubDao.execute(sql, id);
    }

    @Transactional
    public void updateSeeNum(Integer id) {
        String sql = "update t_article set see_num = see_num+1 where atl_id=?";
        pubDao.execute(sql, id);
    }

    @Transactional
    public Integer updateZanNum(Integer id, String userid) {

        Integer type = 1;

        Integer zanId = isZan(id, userid);
        String sql;

        if (zanId == null) {
            sql = "update t_article set zan_num = zan_num+1 where atl_id=?";
        } else {
            //删除zan记录
            sql = "delete from t_zan where id=?";
            pubDao.execute(sql, zanId);

            //更新文章赞数量
            sql = "update t_article set zan_num = zan_num-1 where atl_id=?";
            type = 0;
        }
        pubDao.execute(sql, id);
        return type;
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_article set status = ? where atl_id = ?";
        pubDao.execute(sql, status, id);
    }

    public String getArticleUserid(Integer id) {
        String sql = "select user_id from t_article where atl_id = ?";
        return pubDao.query(String.class, sql, id);
    }
}


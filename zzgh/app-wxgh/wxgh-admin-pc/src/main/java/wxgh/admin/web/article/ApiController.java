package wxgh.admin.web.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.entity.common.Article;
import wxgh.entity.common.Comment;
import wxgh.param.common.bbs.ArticleParam;
import wxgh.sys.service.weixin.common.bbs.ArticleService;
import wxgh.sys.service.weixin.pub.SysFileService;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-21
 **/
@Controller
public class ApiController {
    @RequestMapping
    public ActionResult artlist(ArticleParam param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);

        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("article s").join("user u", "s.user_id = u.userid")
                .field("s.*,u.userid, u.`name` as username, u.mobile");

        if (param.getStatus() != null) {
            sql.where("s.status = :status and isdel != 0");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Article> list =  pubDao.queryList(sql.select().build().sql(), param, Article.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult artchange(Integer atlId,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("article")
                .set("status = ?")
                .where("atl_id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, atlId);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult artdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            String sql = new SQL.SqlBuilder()
                    .table("article")
                    .set("isdel = 0")
                    .where("atl_id = ?")
                    .update()
                    .build().sql();
            pubDao.execute(sql,i);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult artget(Integer id){
        Article actApply = articleService.get(id);

        String sql = "select * from t_comment where atl_id = ?";
        List<Comment> commParamList = pubDao.queryList(Comment.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", actApply);
        refData.put("comm",commParamList);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    private ActionResult list(ArticleParam param){
        param.setStatus(1);
        param.setPageIs(true);
        param.setRowsPerPage(7);

        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("article s").join("user u", "s.user_id = u.userid")
                .field("s.*,u.userid, u.`name` as username, u.mobile");

        if (param.getStatus() != null) {
            sql.where("s.status = :status and isdel != 0");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Article> list =  pubDao.queryList(sql.select().build().sql(), param, Article.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SysFileService sysFileService;
}

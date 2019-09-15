package wxgh.admin.web.bid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.entity.common.ActApply;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.sys.service.weixin.common.act.ActApplyService;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-18
 **/
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult actlist(ActApplyQuery param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("act_apply s").join("user u", "s.userid = u.userid")
                .field("s.*,u.userid, u.`name` as username, u.mobile");

        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<ActApply> list =  pubDao.queryList(sql.select().build().sql(), param, ActApply.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult actchange(Integer id,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("act_apply")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult actdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("act_apply",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult applyget(Integer id){
        String sql = new SQL.SqlBuilder().table("act_apply").where("id =?").select()
                .build().sql();
        ActApply actApply = pubDao.query(ActApply.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", actApply);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult fralist(FraternityApply param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("fraternity_apply f").join("user u","f.user_id = u.userid").field("f.*");

        if (param.getStatus() != null) {
            sql.where("f.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<FraternityApply> list =  pubDao.queryList(sql.select().build().sql(), param, FraternityApply.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult fradel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("fraternity_apply",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult frachange(Integer id,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("fraternity_apply")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult fraget(Integer id){
        String sql = new SQL.SqlBuilder().table("fraternity_apply").where("id =?").select()
                .build().sql();
        FraternityApply apply = pubDao.query(FraternityApply.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", apply);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult dealist(DiseaseApply param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("disease_apply d").join("user u","d.userid = u.userid").field("d.*");

        if (param.getStatus() != null) {
            sql.where("d.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<DiseaseApply> list =  pubDao.queryList(sql.select().build().sql(), param, DiseaseApply.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult deadel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("disease_apply",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult deachange(Integer id,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("disease_apply")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult deaget(Integer id){
        String sql = new SQL.SqlBuilder().table("disease_apply").where("id =?").select()
                .build().sql();
        DiseaseApply apply = pubDao.query(DiseaseApply.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", apply);
        return ActionResult.ok(null,refData);
    }


    @Autowired
    private ActApplyService actApplyService;

    @Autowired
    private PubDao pubDao;
}



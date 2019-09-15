package wxgh.admin.web.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.data.chat.ChatModelInfo;
import wxgh.entity.common.ActApply;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.group.MemberList;
import wxgh.entity.pub.User;
import wxgh.entity.union.group.Group;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.param.union.innovation.group.GroupParam;
import wxgh.param.union.innovation.group.GroupQuery;
import wxgh.query.group.MenberQuery;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult grouplist(GroupQuery param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("group s").join("user u", "s.userid = u.userid")
                .field("s.*,u.userid, u.`name` as username, u.mobile").sys_file("s.avatar_id");

        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Group> list =  pubDao.queryList(sql.select().build().sql(), param, Group.class);


        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult groupchange(Integer id,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("group")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult groupdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("group",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult groupget(Integer id){
        String sql = new SQL.SqlBuilder().table("group s").join("group_user r","s.group_id=r.group_id")
                .join("user u", "r.userid = u.userid")
                .field("u.`name`").where("s.id = ? and r.status = 1").build().sql();
        List<User> user = pubDao.queryList(User.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", user);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult actlist(ActApplyQuery param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("act a").join("group g", "a.group_id = g.group_id")
                .join("user u","a.userid = u.userid").field("a.*, u.`name` as username");

        if (param.getStatus() != null) {
            sql.where("a.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Act> list =  pubDao.queryList(sql.select().build().sql(), param, Act.class);


        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult actchange(Integer id,Integer status){
        String sql = new SQL.SqlBuilder()
                .table("act")
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
            pubDao.execute(SQL.deleteByIds("act",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private  ActionResult actget(Integer id){
        String sql = new SQL.SqlBuilder().table("act a")
                .join("group g","a.group_id = g.group_id")
                .join("user u","a.userid =  u.userid").sys_file("a.img_id")
                .where("a.id = ?")
                .field("a.*,g.`name` as groupName,u.`name`as userName")
                .build().sql();
        List<Act> act = pubDao.queryList(Act.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", act);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult userlist(MenberQuery query) {
        query.setPageIs(true);
        query.setRowsPerPage(5);

        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("group_user g")
                .join("user u","g.userid = u.userid")
                .join("group p","g.group_id = p.group_id")
                .field("g.*,u.name as name,u.avatar as avatar,g.money as cost,u.mobile as phone")
                .where("g.status = :status and g.group_id = :groupId ");

        if (query.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), query);
            query.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");

        }
        List<MemberList> members = pubDao.queryList(sql.select().build().sql(), query, MemberList.class);
        System.out.print(sql.select().build().sql());
        System.out.print(members);
        RefData refData = new RefData();
        refData.put("datas", members);
        refData.put("page", new PageBen(query));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult actshowlist(ActApplyQuery param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("act a").join("group g", "a.group_id = g.group_id")
                .join("user u","a.userid = u.userid").field("a.*, u.`name` as username")
                .where("a.status = :status and g.group_id = :groupId");


        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Act> list =  pubDao.queryList(sql.select().build().sql(), param, Act.class);


        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult userapply(String userid,Integer status){
        String sql = "update t_group_user set status = ? where userid = ?";
        pubDao.execute(sql,status,userid);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult actapply(Integer id,Integer status){
        String sql = "update t_act set status = ? where id = ?";
        pubDao.execute(sql,status,id);
        return ActionResult.ok();
    }


    @Autowired
    private PubDao pubDao;
}

package wxgh.admin.web.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.app.sys.task.PushAsync;
import wxgh.data.entertain.act.ActList;
import wxgh.entity.entertain.act.Act;
import wxgh.param.entertain.act.ActParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-21
 **/
@Controller
public class ApiController {
    @RequestMapping
    public ActionResult actlist(ActParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(5);
        if(param.getActType()==null){
            param.setActType(Act.ACT_TYPE_BIG);
        }
        if(param.getLimit()==null){
            param.setLimit(true);
        }
        param.setRegular(0);


        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.*")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, a.image_ids)) path")
                .order("a.status")
                .order("a.add_time", Order.Type.DESC)
                ;
        if (param.getActType() != null) {
            sql.where("a.act_type = :actType");
        }
        if (param.getRegular() != null) {
            sql.where("a.regular = :regular");
        }
        if (param.getRegular() != null) {
            if (param.getRegular() == 1) { //如果为定期活动
                sql.field("(select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id)as time")
                        .where("a.regular = :regular");
            } else if (param.getRegular() == 0) {
                sql.field("CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i')) as time")
                        .where("a.regular = :regular");
            } else {
                sql.field("if(a.regular = 1, (select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id), CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i'))) as time");
            }
        }

        List<ActList> acts = new ArrayList<ActList>();
        if(!param.getLimit()){
            acts = pubDao.queryList(sql.select().build().sql(), param, ActList.class);
        }else{
            acts = pubDao.queryPage(sql, param, ActList.class);
        }


        RefData refData = new RefData();
        refData.put("datas", acts);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status){
//        String sql = new SQL.SqlBuilder()
//                .table("act")
//                .set("status = ?")
//                .where("id = ?")
//                .update()
//                .build().sql();
//        pubDao.execute(sql, status, id);
//        if(status == 1){
//            pushAsync.sendActBig(id);
//        }
        actService.apply(id,status);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult del(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("act",i));
        }
        return ActionResult.ok();
    }



    @Autowired
    private PubDao pubDao;

    @Autowired
    private wxgh.sys.service.wxadmin.act.ActService actService;

    @Autowired
    private PushAsync pushAsync;
}

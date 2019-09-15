package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.param.union.innovation.work.WorkResultParam;
import wxgh.param.union.innovation.work.WorkResultQuery;
import wxgh.sys.dao.union.innovation.WorkResultDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
@Service
@Transactional(readOnly = true)
public class WorkResultService {

    @Autowired
    private WorkResultDao workResultDao;

    @Autowired
    private PubDao pubDao;

    
    public List<WorkResult> getResults(WorkResultQuery query) {
        return workResultDao.getResults(query);
    }

    
    public WorkResult getResult(WorkResultQuery query) {
        return workResultDao.getResult(query);
    }

    
    @Transactional
    public Integer save(WorkResult workResult) {
        workResultDao.save(workResult);
        return workResult.getId();
    }

    
    public List<WorkResult> resultList(WorkResultQuery workResultQuery) {
        SeUser user = UserSession.getUser();
        if (user != null) {

//            Integer deptId = weixinDeptService.getCompanyId(user.getDeptid());
//            if (deptId != AppConsts.DEPTID) {
//                workResultQuery.setNodeptid(AppConsts.DEPTID);
//                deptId = 1;
//            }
//            if (!workResultQuery.isMine()){
//                workResultQuery.setDeptid(deptId);
//            }else {
//                workResultQuery.setDeptid(null);
//            }
        }
        return workResultDao.resultList(workResultQuery);
    }

    public List<WorkResult> listResult(WorkResultParam workResultParam) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_work_result wr");

        if (workResultParam.getWorkType() != null && workResultParam.getWorkType() == 1) {

            sql.field("wr.*, ir.race_name AS itemName, ia.`status` AS status, ia.`audit_idea` AS auditIdea, ia.`id` AS applyId")
                    .join("t_innovate_race ir", "ir.id = wr.work_id")
                    .join("t_innovate_apply ia", "ia.id = ir.apply_id");

        } else if (workResultParam.getWorkType() != null && workResultParam.getWorkType() == 2) {

            sql.field("wr.*, ins.`item_name` AS itemName, ins.`team_name` AS teamName, ia.`status` AS status , ia.`audit_idea`  AS auditIdea, ia.`id` AS applyId")
                    .join("t_innovate_shop ins", "ins.id = wr.work_id")
                    .join("t_innovate_apply ia", "ia.id = ins.apply_id");
            sql.order("wr.`orderid`", Order.Type.ASC);
        } else {

            sql.field("wr.*, im.`name` AS itemName, im.`team` AS teamName, ia.`status` AS status , ia.`audit_idea` AS auditIdea,\n" +
                    "            ia.`id` AS applyId, im.id AS miId")
                    .join("t_innovate_apply ia", "ia.id = wr.work_id")
                    .join("t_innovate_micro im", "im.pid = ia.id");

        }

        sql.where("ia.`apply_step` = 1");
        sql.order("wr.add_time", Order.Type.DESC);

        if (workResultParam.getId() != null)
            sql.where("wr.id = :id");
        if (workResultParam.getWorkType() != null)
            sql.where("wr.work_type = :workType");
        if (workResultParam.getStatus() != null)
            sql.where("ia.status = :status");
        if (workResultParam.getUserid() != null)
            sql.where("ia.userid = :userid");
        if (workResultParam.getNodeptid() != null)
            sql.where("ia.deptid != :nodeptid");
        if (workResultParam.getDeptid() != null)
            sql.where("FIND_IN_SET(ia.deptid, query_dept_child( :deptid )) > 0");

        workResultParam.setPageIs(true);
        workResultParam.setRowsPerPage(7);
        return pubDao.queryPage(sql, workResultParam, WorkResult.class);
    }

    public List<WorkResult> resultList(WorkResultParam workResultParam) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_work_result wr");

        if(workResultParam.getWorkType() != null && workResultParam.getWorkType() == 1){

            sql.field("wr.*, ir.race_name AS itemName, ia.`status` AS status, ia.`audit_idea` AS auditIdea, ia.`id` AS applyId")
                    .join("t_innovate_race ir", "ir.id = wr.work_id")
                    .join("t_innovate_apply ia", "ia.id = ir.apply_id");

        }else if(workResultParam.getWorkType() != null && workResultParam.getWorkType() == 2){

            sql.field("wr.*, ins.`item_name` AS itemName, ins.`team_name` AS teamName, ia.`status` AS status , ia.`audit_idea`  AS auditIdea, ia.`id` AS applyId")
                    .join("t_innovate_shop ins", "ins.id = wr.work_id")
                    .join("t_innovate_apply ia", "ia.id = ins.apply_id");
            sql.order("wr.`orderid`", Order.Type.ASC);
        }else{

            sql.field("wr.*, im.`name` AS itemName, im.`team` AS teamName, ia.`status` AS status , ia.`audit_idea` AS auditIdea,\n" +
                    "            ia.`id` AS applyId, im.id AS miId")
                    .join("t_innovate_apply ia", "ia.id = wr.work_id")
                    .join("t_innovate_micro im", "im.pid = ia.id");

        }

        sql.where("ia.`apply_step` = 1");
        sql.order("wr.add_time", Order.Type.DESC);

        if(workResultParam.getId() != null)
            sql.where("wr.id = :id");
        if(workResultParam.getWorkType() != null)
            sql.where("wr.work_type = :workType");
        if(workResultParam.getStatus() != null)
            sql.where("ia.status = :status");
        if(workResultParam.getUserid() != null)
            sql.where("ia.userid = :userid");
        if(workResultParam.getNodeptid() != null)
            sql.where("ia.deptid != :nodeptid");
        if(workResultParam.getDeptid() != null)
            sql.where("FIND_IN_SET(ia.deptid, query_dept_child( :deptid )) > 0");


        return pubDao.queryPage(sql, workResultParam, WorkResult.class);
    }
    
    public List<WorkResult> resultList2(WorkResultQuery workResultQuery) {
        return workResultDao.resultList(workResultQuery);
    }

    
    public WorkResult result(WorkResultQuery workResultQuery) {
        return workResultDao.result(workResultQuery);
    }

    @Transactional
    public void changeWork(Integer id, Integer status) {
        WorkResultParam workResultParam = new WorkResultParam();
        workResultParam.setWorkType(2);
        workResultParam.setId(id);

        List<WorkResult> workResultList = listResult(workResultParam);


        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_innovate_apply ia")
                .set("ia.status = :status")
                .where("ia.id = :applyId");
        for (WorkResult w : workResultList) {
            w.setStatus(status);
            pubDao.executeBean(sql.update().build().sql(), w);
        }
    }
}

package wxgh.sys.service.admin.di.exam;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.error.ValidationError;
import wxgh.data.party.di.exam.ExamExportResult;
import wxgh.data.party.di.exam.ExamJoinList;
import wxgh.data.party.exam.ExamCountByBranch;
import wxgh.data.party.exam.ExamJoinListByParty;
import wxgh.data.pcadmin.party.di.ExportBranchExam;
import wxgh.data.pcadmin.party.di.ExportExam;
import wxgh.data.pcadmin.party.di.ExportNoExam;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.party.di.DiPlan;
import wxgh.entity.party.di.Exam;
import wxgh.param.party.di.exam.ExamJoinParam;
import wxgh.param.party.di.exam.ExamParam;
import wxgh.param.party.di.exam.ExamResultParam;
import wxgh.param.party.exam.ExamJoinBranchParam;
import wxgh.param.pcadmin.party.di.ExportExamParam;
import wxgh.param.pcadmin.party.exam.ExportExamPartyParam;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheng on 2017/8/17.
 */
@Service
public class ExamService {

    @Autowired
    private PubDao pubDao;

    public List<ExamParam> getExamList(ExamParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_exam t_e")
                .join("t_sys_file t_f", "t_e.cover_id = t_f.file_id", Join.Type.LEFT)
                .field("t_e.id,t_e.name,t_e.info,t_f.file_path as coverPath,t_e.files," +
                        "t_e.add_time,t_e.end_time,t_e.total,t_e.group_id,t_e.content, t_e.party_id");
        if (param.getGroupId() != null) {
            sql.where("t_e.group_id = :groupId");
        }
        // 考试计划面向的类型
        if(param.getType() != null)
            sql.join("t_di_plan p", "p.id = t_e.plan_id", Join.Type.LEFT).where("p.type = :type");


        // 所有下级的考试
        // sql.where("find_in_set(t_e.group_id, (select group_concat(group_id) from t_party_dept where group_id is not null and FIND_IN_SET(id, get_party_dept_child(:partyId)) > 0) )");
        // 查找党委的考试
        // find_in_set(t_e.party_id, select_all_parentid_party_dept(:partyId))

        // 党建, 支部 包含 党委级别的考试
        if(param.getPartyId() != null)
            sql.where("find_in_set(t_e.group_id, (select group_concat(group_id) from t_party_dept where group_id is not null and FIND_IN_SET(id, get_party_dept_child(:partyId)) > 0) ) or find_in_set(t_e.party_id, select_all_parentid_party_dept(:partyId))");

        return pubDao.queryPage(sql, param, ExamParam.class);
    }

    public ExamParam getExamListById(Integer id) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_exam t_e")
                .field("t_e.id,t_e.name,t_e.info,t_e.content,t_e.add_time,t_e.end_time")
                .where("id=?");
        return pubDao.query(ExamParam.class, sql.build().sql(), id);
    }

    public void deleteExam(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的考试！");
        }
//        pubDao.execute(SQL.deleteByIds("t_di_exam", id));

        String[] ids = id.split(",");
        String sql;

        sql = "DELETE from t_di_exam_record WHERE exam_id = ?";
        pubDao.batch(sql, ids);

        for (int i = 0; i < ids.length; i++) {
            sql = "SELECT id FROM t_di_exam_question Where exam_id = ? ";
            List<Integer> questionIds = pubDao.queryList(Integer.class, sql, ids[i]);

            for (int j = 0; j < questionIds.size(); j++) {
                sql = "DELETE from t_di_exam_answer WHERE id = ?";
                pubDao.execute(sql, questionIds.get(j));
            }
        }

        sql = "DELETE from t_di_exam_question WHERE exam_id = ?";
        pubDao.batch(sql, ids);

        sql = "DELETE from t_di_exam_join WHERE eid = ?";
        pubDao.batch(sql, ids);

        sql = "delete from t_di_exam where id = ?";
        pubDao.batch(sql, ids);
    }

    public List<ExamJoinList> getExamJoinList(ExamJoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_exam_join j")
                .join("t_user u", "u.userid = j.userid", Join.Type.LEFT)
                .join("t_di_exam e", "e.id = j.eid", Join.Type.LEFT)
                .field("j.id,e.name as examName,j.userid,u.name as examinee,j.eid,j.add_time as addTime,j.exam");
        if (param.getExam() == 1) {
            sql.field("(select count(*) FROM t_di_exam_question q WHERE q.exam_id=:eid) AS allNum");
            sql.field("(select count(*) from t_di_exam_record r JOIN t_di_exam_question q ON r.question_id = q.id where r.userid=u.userid and  r.right_is =1 and q.exam_id=:eid) AS rightNum");
            sql.where("j.eid = :eid and j.exam = :exam");
        }
        if (param.getExam() == 0) {
            sql.where("j.eid = :eid");
        }

        List<ExamJoinList> list = pubDao.queryPage(sql, param, ExamJoinList.class);
        if (param.getExam() == 1 && list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                NumberFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(1);
                Integer allNum = list.get(i).getAllNum();
                Integer rightNum = list.get(i).getRightNum();
                if (allNum == 0 || allNum == null || rightNum == null)
                    list.get(i).setScore(0.0);
                else if (allNum != 0 || allNum != null)
                    list.get(i).setScore(Double.parseDouble(formater.format(Double.valueOf(rightNum) / Double.valueOf(allNum) * 100)));
            }
        }
        return list;
    }


    // 党委, 增加支部名称字段
    public List<ExamJoinListByParty> getExamJoinListByParty(ExamJoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_exam_join j")
                .join("t_user u", "u.userid = j.userid", Join.Type.LEFT)
                .join("t_di_exam e", "e.id = j.eid", Join.Type.LEFT)
                .join("t_party_dept_user du", "du.userid = j.userid", Join.Type.LEFT)
                .join("t_party_dept d", "d.id = du.party_id", Join.Type.LEFT)
                .field("d.name as branchName, d.id as branchId")
                .field("j.id,e.name as examName,j.userid,u.name as examinee,j.eid,j.add_time as addTime,j.exam");
        if (param.getExam() == 1) {
            sql.field("(select count(*) FROM t_di_exam_question q WHERE q.exam_id=:eid) AS allNum");
            sql.field("(select count(*) from t_di_exam_record r JOIN t_di_exam_question q ON r.question_id = q.id where r.userid=u.userid and  r.right_is =1 and q.exam_id=:eid) AS rightNum");
            sql.where("j.eid = :eid and j.exam = :exam");
        }
        if (param.getExam() == 0) {
            sql.where("j.eid = :eid");
        }

        List<ExamJoinListByParty> list = pubDao.queryPage(sql, param, ExamJoinListByParty.class);
        if (param.getExam() == 1 && list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                NumberFormat formater = new DecimalFormat();
                formater.setMaximumFractionDigits(1);
                Integer allNum = list.get(i).getAllNum();
                Integer rightNum = list.get(i).getRightNum();
                if (allNum == 0 || allNum == null || rightNum == null)
                    list.get(i).setScore(0.0);
                else if (allNum != 0 || allNum != null)
                    list.get(i).setScore(Double.parseDouble(formater.format(Double.valueOf(rightNum) / Double.valueOf(allNum) * 100)));
            }
        }
        return list;
    }


    public void deleteExamJoin(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的考试记录！");
        }

//        pubDao.execute(SQL.deleteByIds("t_di_exam_join", id));

        String[] ids = id.split(",");
        String sql;
        for (int i = 0; i < ids.length; i++) {
            sql = "DELETE from t_di_exam_record WHERE exam_id = " +
                    "(SELECT j.eid FROM t_di_exam_join j WHERE j.id = ?) and userid = (SELECT j.userid FROM t_di_exam_join j WHERE j.id = ?)";
            pubDao.execute(sql, ids[i], ids[i]);
        }

        sql = "delete from t_di_exam_join where id = ?";
        pubDao.batch(sql, ids);
    }

    @Transactional
    public void deleteExamNotDo(String id){
        pubDao.execute(SQL.deleteByIds("t_di_exam_join", id));
    }

    public void update(ExamParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .update("t_di_exam");
        if (param.getName() != null) {
            sql.set("name=:name");
        }
        if (param.getInfo() != null) {
            sql.set("info=:info");
        }
        if (param.getContent() != null) {
            sql.set("content=:content");
        }
        if(param.getAddTime() !=null){
            sql.set("add_time=:addTime");
        }
        if(param.getEndTime() !=null){
            sql.set("end_time=:endTime");
        }
        sql.where("id=:id");
        pubDao.executeBean(sql.build().sql(), param);
    }

    public void update_exam(Integer id) {
        String sql = "update t_di_exam_join set exam=0 where id=?";
        pubDao.execute(sql, id);
    }

    //判断是否已经有积分了
    public boolean isExistScore(Integer by_id, String userid, Integer group, Integer type) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_score")
                .field("id")
                .where("by_id=? AND userid=? AND score_group=? AND score_type=?");
        List<Integer> ids = pubDao.queryList(Integer.class, sql.build().sql(), by_id, userid, group, type);
        if (ids.size() != 0)
            return true;//有积分
        else
            return false;//无积分
    }

    //删除某个用户积分信息
    public void deletScoreByuserid(Integer by_id, String userid, Integer group, Integer type) {
        String sql = "DELETE FROM t_score WHERE by_id=? AND userid=? AND score_group=? AND score_type=?";
        pubDao.execute(sql, by_id, userid, group, type);
    }


    /**
     * 获取考试结果列表
     *
     * @param examId
     * @return
     */
    public List<ExamExportResult> getExamResultList(Integer examId) {
        SQL sql = new SQL.SqlBuilder()
                .table("t_di_exam_record er")
                .field("u.name as username, u.mobile")
                .field("(select name from t_dept where FIND_IN_SET(id, u.department) and parentid = (select id from t_dept where parentid = 0) limit 1) as companyName")
                .field("(select name from t_dept where id = u.deptid) as deptName")
                .field("(select count(*) from t_di_exam_record e1 where e1.right_is = 1 and e1.exam_id = er.exam_id and e1.userid = u.userid) as rightTotal")
                .field("(select count(*) from t_di_exam_record e2 where e2.right_is = 0 and e2.exam_id = er.exam_id and e2.userid = u.userid) as errorTotal")
                .join("t_user u", "u.userid = er.userid")
                .where("er.exam_id = ?")
                .groupBy("er.userid")
                .select()
                .build();
        return pubDao.queryList(ExamExportResult.class, sql.sql(), examId);
    }

    public List<ExportExam> exportExams(ExportExamParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name")
                .field("u.name as username, u.mobile, g.`name` as groupName, g.id as groupId")
                .field("j.add_time, j.exam as status,cu.remark as remark")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as deptName")
                .field("(SELECT count(*) from t_di_exam_question where exam_id = e.id) as total")
                .field("(select count(*) from t_di_exam_record where right_is = 1 and exam_id = e.id and userid = j.userid) as rightTotal")
                .field("(select count(*) from t_di_exam_record where right_is = 0 and exam_id = e.id and userid = j.userid) as errorTotal")
                .join("chat_group g", "g.group_id = e.group_id")
                .join("chat_user cu", "cu.group_id = e.group_id")
                .join("user u", "u.userid = cu.userid")
                .join("di_exam_join j", "(j.eid = e.id and j.userid = cu.userid)", Join.Type.LEFT);
        // 单个
        if (ExportExamParam.TYPE_SING == param.getExportType()) {
            sql.where("e.id = ?");
            return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
        } else {
            if(ExportExamParam.TYPE_GROUP == param.getExportType()) {
                // 按照群体
                if (!"0".equals(param.getId())) {
                    sql.where("e.group_id in (?)");
                    return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
                    //                return pubDao.queryInList(sql.build().sql(), TypeUtils.strToList(param.getId()), ExportExam.class);
                }
                // 按照考试
            }else{
                if (!"0".equals(param.getId())) {
                    sql.where("e.id in (?)");
                    return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
                }
            }
            // 全部
            return pubDao.queryList(ExportExam.class, sql.build().sql());
        }
    }

    // 党委 , 使用 chat_user chat_group 与 di_exam 的 group_id 关联
    public List<ExportExam> exportExamsByParty(ExportExamParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name")
                .field("u.name as username, u.mobile, g.`name` as groupName, g.id as groupId")
                .field("j.add_time, j.exam as status")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as deptName")
                .field("(SELECT count(*) from t_di_exam_question where exam_id = e.id) as total")
                .field("(select count(*) from t_di_exam_record where right_is = 1 and exam_id = e.id and userid = j.userid) as rightTotal")
                .field("(select count(*) from t_di_exam_record where right_is = 0 and exam_id = e.id and userid = j.userid) as errorTotal")
                .join("chat_group g", "g.group_id = e.group_id")
                .join("chat_user cu", "cu.group_id = e.group_id")
                .join("di_plan p", "p.id = e.plan_id")
                .where("p.type = " + DiPlan.Type.PARTY.getValue())
                .join("user u", "u.userid = cu.userid")
                .join("di_exam_join j", "(j.eid = e.id and j.userid = cu.userid)", Join.Type.LEFT);
        // 按单个考试
        if(param.getExportType() == 4) {
            sql.where("e.id = ?");
            return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
            // 按照支部
        }else if (ExportExamParam.TYPE_SING == param.getExportType()) {
            if (!"0".equals(param.getId())) {
                sql.where("g.type = ?");
                sql.join("party_dept d", "d.group_id = g.group_id")
                .where("d.id in (?)");
                return pubDao.queryList(ExportExam.class, sql.build().sql(), ChatGroup.TYPE_PARTY, param.getId());
            }

        } else if(ExportExamParam.TYPE_GROUP == param.getExportType()) {
            // 按照党委
            if (!"0".equals(param.getId())) {
                sql.where("g.type = ?");
                List<String> where = new ArrayList<>();
                List<String> list = TypeUtils.strToList(param.getId());
                for (String s:list){
                    if(!TypeUtils.empty(s)){
                        where.add("find_in_set(dp.id, get_party_dept_child("+s+"))");
                    }
                }
                sql.join("party_dept d", "d.group_id = g.group_id")
                        .where("d.id in ( select id from t_party_dept dp where dp.is_party = 0 and ("+ TypeUtils.listToStr(where, " or ")+") )");
                return pubDao.queryList(ExportExam.class, sql.build().sql(), ChatGroup.TYPE_PARTY);
                //                return pubDao.queryInList(sql.build().sql(), TypeUtils.strToList(param.getId()), ExportExam.class);
            }
            // 按照考试
        } else{
                if (!"0".equals(param.getId())) {
                    sql.where("e.id in (?)");
                    return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
                }
            }
            //全部
        return pubDao.queryList(ExportExam.class, sql.build().sql());

    }

    // 党建 (党委考试导出, 使用 party_dept party_dept_user 表关联)
    public List<ExportExam> exportExamsByParty2(ExportExamPartyParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name")
                .field("u.name as username, u.mobile, d.`name` as groupName, d.id as groupId")
                .field("j.add_time, j.exam as status")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as deptName")
                .field("(SELECT count(*) from t_di_exam_question where exam_id = e.id) as total")
                .field("(select count(*) from t_di_exam_record where right_is = 1 and exam_id = e.id and userid = j.userid) as rightTotal")
                .field("(select count(*) from t_di_exam_record where right_is = 0 and exam_id = e.id and userid = j.userid) as errorTotal")
                .join("party_dept_user du", "1=1")
                .join("di_plan p", "p.id = e.plan_id")
                .where("p.type = " + DiPlan.Type.PARTY.getValue())
                .join("user u", "u.userid = du.userid")
                .join("party_dept d", "d.id = du.party_id", Join.Type.LEFT)
                .join("di_exam_join j", "(j.eid = e.id and j.userid = du.userid)", Join.Type.LEFT);
        // 按单个考试
        if (param.getExportType() == ExportExamPartyParam.TYPE_SING) {
            sql.where("find_in_set(du.party_id, get_party_dept_child(e.party_id))").where("e.id = ?");
            return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());

        // 按照考试
        }else{
            if (!"0".equals(param.getId())) {
                sql.where("e.id in (?)");
                return pubDao.queryList(ExportExam.class, sql.build().sql(), param.getId());
            }
        }
        //全部
        return pubDao.queryList(ExportExam.class, sql.build().sql());
    }

    /**
     * 检查id串是否属于同一种类型
     * @param id 考试id串
     * @return
     */
    public List<Exam> checkExportNoExam(String id){
        String sql = "select * from t_di_exam where id in ("+id+") GROUP BY party_id , group_id";
        return pubDao.queryList(Exam.class, sql);
    }

    public List<Exam> checkExportNoExamByParty(String id){
        String sql = "select * from t_di_exam where id in ("+id+") GROUP BY party_id";
        return pubDao.queryList(Exam.class, sql);
    }

    /**
     * 党建考试导出 未考试名单
     * @param id    考试id串
     * @param type  1党委 2支部
     * @return
     */
    public List<ExportNoExam> exportNoExamList(String id, Integer type){
        String sql = null;
        if(type.equals(1)){  // 党委
            sql = "select a.userid, d.name as branchName, u.name as username, u.mobile from\n" +
                    "(select userid,party_id from t_party_dept_user where userid != '') as a\n" +
                    "left join\n" +
                    "(select userid from t_di_exam_join where eid IN ("+id+") AND exam = 1 GROUP BY userid) as b\n" +
                    "on b.userid = a.userid\n" +
                    "join t_user u on u.userid = a.userid\n" +
                    "join t_party_dept d on d.id = a.party_id\n" +
                    "where b.userid is null";
        }else{   //  支部
            sql = new SQL.SqlBuilder()
                    .field("d.name as branchName, u.name as username, u.mobile")
                    .table("t_di_exam e")
                    .join("t_chat_user cu", "cu.group_id = e.group_id")
                    .join("t_user u", "u.userid = cu.userid")
                    .join("t_party_dept d", "d.group_id = e.group_id")
                    .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid IN ("+id+")  and j.exam = 1) = 0 " )
                    .groupBy("u.userid")
                    .select()
                    .build()
                    .sql();
        }
        return pubDao.queryList(ExportNoExam.class, sql);
    }

    /**
     * 获取考试结果列表
     *
     * @param param
     * @return
     */
    public List<ExamExportResult> getExamResultPageList(ExamResultParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_di_exam_record er")
                .field("u.name as username, u.mobile")
                .field("(select name from t_dept where FIND_IN_SET(id, u.department) and parentid = (select id from t_dept where parentid = 0) limit 1) as companyName")
                .field("(select name from t_dept where id = u.deptid) as deptName")
                .field("(select count(*) from t_di_exam_record e1 where e1.right_is = 1 and e1.exam_id = er.exam_id and e1.userid = u.userid) as rightTotal")
                .field("(select count(*) from t_di_exam_record e2 where e2.right_is = 0 and e2.exam_id = er.exam_id and e2.userid = u.userid) as errorTotal")
                .join("t_user u", "u.userid = er.userid")
                .where("er.exam_id = :id")
                .groupBy("er.userid")
                .select();
        return pubDao.queryPage(sql, param, ExamExportResult.class);
    }

    // 2017-11-21 新增
    // 党建考试按支部统计
    public List<ExamCountByBranch> getExamJoinListByBranch(ExamJoinBranchParam param) {

        Exam id = pubDao.query(Exam.class, "select * from t_di_exam where id = ?", param.getEid());
        param.setPartyId(id.getPartyId());

        String bSql = "";
        // 已完成考试
        if (param.getExam() == 1) {
        }
        // 已报名
        if (param.getExam() == 0) {
            bSql = " and j.exam = 1 ";
        }

//        SQL.SqlBuilder sql = new SQL.SqlBuilder()
//                .select("t_party_dept a")
//                .field("a.id as partyId, a.name as branchName, a.name as examName, IFNULL(b.sum, 0) as sum")
//                .join("(\n" +
//                        "select d.id, d.name, count(*) as sum from t_di_exam_join j\n" +
//                        "join t_party_dept_user du on du.userid = j.userid\n" +
//                        "left join t_party_dept d on d.id = du.party_id\n" +
//                        "join t_di_exam e on e.id = j.eid\n" +
//                        "where e.id = :eid " + bSql +
//                        "group by du.party_id\n" +
//                        ") b", "a.id = b.id", Join.Type.LEFT)

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_party_dept a left join (" +
                        "select d.id, count(*) as sum from t_di_exam_join j\n" +
                        "join t_party_dept_user du on du.userid = j.userid\n" +
                        "left join t_party_dept d on d.id = du.party_id\n" +
                        "join t_di_exam e on e.id = j.eid\n" +
                        "where e.id = :eid "  +
                        "group by du.party_id\n" +
                        ") b on a.id = b.id ")
                .field("a.id as partyId, a.name as branchName, a.name as examName, IFNULL(b.sum, 0) as sum")
                .where("a.is_party = 0 and find_in_set(a.id, get_party_dept_child(:partyId))");

        List<ExamCountByBranch> list = pubDao.queryPage(sql, param, ExamCountByBranch.class);
        for (ExamCountByBranch e : list)
            e.setExamName(id.getName());
        return list;
    }
    // end


    // 2017-12-18 新增 支部考试率导出
    public List<ExportBranchExam> exportBranchExamRateByFinish(String id){
        if(TypeUtils.empty(id))
            return  null;
        String fid = TypeUtils.strToList(id).get(0).toString();

        String s = "select party_id from t_di_exam where id = ?";
        Integer partyId = pubDao.query(Integer.class, s, fid);

        String sql = "select b.id, b.name, \n" +
                "COALESCE(b.count,0) as finishExamCount, \n" +
                "COALESCE(c.count,0) as userCount,\n" +
                "COALESCE(c.count,0) - COALESCE(b.count,0) as notJoinExamCount,\n" +
                "COALESCE(b.count,0) / COALESCE(c.count,0) as sort,\n" +
                "CONCAT(FORMAT(COALESCE(b.count,0) / COALESCE(c.count,0) * 100, 2), '%') as rate \n" +
                "from\n" +
                "(\n" +
                "select d.id, d.name, d.group_id, b.count from t_party_dept d left join\n" +
                "(\n" +
                "select du.party_id, count(distinct j.id) as count \n" +
                "from t_di_exam_join j,t_party_dept_user du\n" +
                "where du.userid = j.userid and j.exam = 1 and j.eid in ("+id+")\n" +
                "group by du.party_id\n" +
                ") b on b.party_id = d.id where d.is_party = 0\n" +
                ") b ,\n" +
                "(\n" +
                "select d.id, d.name,\n" +
                "count(distinct du.id) * (select count(*) from t_di_exam where id in ("+id+")) \n" +
                "as count \n" +
                "from t_party_dept d\n" +
                "left join t_party_dept_user du on d.id = du.party_id\n" +
                "left join t_chat_group g on d.group_id = g.group_id\n" +
                "where d.is_party = 0\n" +
                "group by d.id\n" +
                ") c\n" +
                "where c.id = b.id\n" +
                (partyId != null && partyId != 1 && partyId > 0 ? "and find_in_set(b.id, get_party_dept_child("+partyId+"))\n" : "") +
                "order by sort desc\n";

        return pubDao.queryList(ExportBranchExam.class, sql);
    }

}

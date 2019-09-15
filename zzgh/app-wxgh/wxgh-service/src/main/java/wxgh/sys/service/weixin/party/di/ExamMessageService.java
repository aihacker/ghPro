package wxgh.sys.service.weixin.party.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.data.party.di.exam.ExamNotDoList;
import wxgh.data.party.exam.ExamCountByBranch;
import wxgh.data.party.exam.ExamNotDoListByParty;
import wxgh.entity.party.di.Exam;
import wxgh.entity.party.di.Examiner;
import wxgh.entity.party.di.Topic;
import wxgh.param.party.di.exam.ExamResultParam;
import wxgh.param.party.di.exam.UserBranchListParam;
import wxgh.param.party.exam.ExamJoinBranchParam;
import wxgh.param.party.exam.JoinListParam;

import java.util.List;

/**
 * Created by cby on 2017/8/11.
 */
@Service
@Transactional(readOnly = true)
public class ExamMessageService {

    /**
     * 获取未考试未报名
     * @param
     * @return
     */
    public List<Examiner> getExamNotJoin(JoinListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_chat_user cu")
                .field("cu.userid")
                .field("u.name,u.avatar")
                .field("jj.add_time, if(jj.id > 0, 1, 0) as joinIn")
                .join("t_user u", "u.userid = cu.userid")
                .join("t_di_exam e", "cu.group_id = e.group_id")
                .join("t_di_exam_join jj", "jj.userid = cu.userid and jj.eid = e.id", Join.Type.LEFT)
                .where("cu.status = 1")
                .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = :id")
                .select();
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    /**
     * 获取未考试未报名
     * 党建
     * @param
     * @return
     */
    public List<Examiner> getExamNotJoinByParty(JoinListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_dept_user du")
                .field("du.userid")
                .field("u.name,u.avatar")
                .field("jj.add_time, if(jj.id > 0, 1, 0) as joinIn")
                .field("d.name as dept")
                .join("t_party_dept d","d.id = du.party_id")
                .join("t_user u", "u.userid = du.userid")
                .join("t_di_exam e", "1=1")
                .join("t_di_exam_join jj", "jj.userid = du.userid and jj.eid = e.id", Join.Type.LEFT)
                .where("(select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = :id")
                .where("find_in_set(du.party_id, get_party_dept_child(e.party_id))")
                .order("du.party_id")
                .select();
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    /**
     * 获取未考试未报名
     * @param param
     * @return
     */
    public List<ExamNotDoList> getExamNotJoin(ExamResultParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_chat_user cu")
                .field("cu.userid")
                .field("e.name as examName")
                .field("u.name as username,u.avatar")
                .field("jj.id as joinId, jj.add_time, if(jj.id > 0, 1, 0) as joinIn")
                .join("t_user u", "u.userid = cu.userid")
                .join("t_di_exam e", "cu.group_id = e.group_id")
                .join("t_di_exam_join jj", "jj.userid = cu.userid and jj.eid = e.id", Join.Type.LEFT)
                .where("cu.status = 1")
                .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = :id")
                .select();
        return pubDao.queryPage(sql, param, ExamNotDoList.class);
    }

    /**
     * 获取未考试未报名 (党委)
     * 11-21增加多支部名称字段
     * @param param
     * @return
     */
    public List<ExamNotDoListByParty> getExamNotJoinByParty(ExamResultParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_dept_user du")
                .field("du.userid")
                .field("e.name as examName")
                .field("u.name as username,u.avatar")
                .field("jj.id as joinId, jj.add_time, if(jj.id > 0, 1, 0) as joinIn")
                .field("d.name as branchName, d.id as branchId")
                .join("t_user u", "u.userid = du.userid")
                .join("t_di_exam e", "1=1")
                .join("t_di_exam_join jj", "jj.userid = du.userid and jj.eid = e.id", Join.Type.LEFT)
                .join("party_dept d", "d.id = du.party_id", Join.Type.LEFT)
                .where("(select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = :id")
                .where("find_in_set(du.party_id, get_party_dept_child(e.party_id))")
                .select();
        return pubDao.queryPage(sql, param, ExamNotDoListByParty.class);
    }

    /**
     * 党委， 按支部统计考试情况
     * @param param
     * @return
     */
    public List<ExamCountByBranch> getExamNotJoinByBranch(ExamJoinBranchParam param){
        Exam id = pubDao.query(Exam.class, "select * from t_di_exam where id = ?", param.getEid());
        if(id == null)
            return null;
        param.setPartyId(id.getPartyId());
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_party_dept a left join (" +
                        "select d.id, count(*) as sum from t_party_dept_user du\n" +
                        "join t_di_exam e on 1 = 1\n" +
                        "left join t_di_exam_join jj on jj.userid = du.userid and jj.eid = e.id\n" +
                        "left join t_party_dept d on d.id = du.party_id\n" +
                        "where (select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = e.id and j.exam = 1) = 0\n" +
                        "and e.id = :eid\n" +
                        "group by du.party_id" +
                        ") b on a.id = b.id ")
                .field("a.id as partyId, a.name as branchName, a.name as examName, IFNULL(b.sum, 0) as sum")
                .where("a.is_party = 0 and find_in_set(a.id, get_party_dept_child(:partyId))");

//                .field("a.id as partyId, a.name as branchName, a.name as examName, IFNULL(b.sum, 0) as sum")
//                .join("select d.id, count(*) as sum from t_party_dept_user du\n" +
//                        "join t_di_exam e on 1 = 1\n" +
//                        "left join t_di_exam_join jj on jj.userid = du.userid and jj.eid = e.id\n" +
//                        "left join t_party_dept d on d.id = du.party_id\n" +
//                        "where (select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = e.id and j.exam = 1) = 0\n" +
//                        "and e.id = :eid\n" +
//                        "group by du.party_id", "a.id = b.id", Join.Type.LEFT)
//                .where("a.is_party = 0 and find_in_set(a.id, get_party_dept_child(:partyId))");
        List<ExamCountByBranch> list = pubDao.queryPage(sql, param, ExamCountByBranch.class);
        for (ExamCountByBranch e : list)
            e.setExamName(id.getName());
        return list;
    }

    /**
     * 获取未考试未报名总数
     * @param id
     * @return
     */
    public Integer getExamNotJoinNumber(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_chat_user cu")
                .join("t_user u", "u.userid = cu.userid")
                .join("t_di_exam e", "cu.group_id = e.group_id")
                .where("cu.status = 1")
                .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = ?")
                .count()
                .build();
        return pubDao.query(Integer.class, sql.sql(), id);
    }

    /**
     * 获取未考试未报名总数
     * @param id
     * @return
     */
    public Integer getExamNotJoinNumberByParty(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("party_dept_user du")
                .join("t_user u", "u.userid = du.userid")
                .join("t_di_exam e", "1=1")
                .where("(select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = ?")
                .where("find_in_set(du.party_id, get_party_dept_child(e.party_id))")
                .count()
                .build();
        return pubDao.query(Integer.class, sql.sql(), id);
    }

    /**
     * 获取已报名考试没有考试的userid
     * @param id
     * @return
     */
    public List<String> getExamNotDo(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_chat_user cu")
                .field("cu.userid")
                .join("t_user u", "u.userid = cu.userid")
                .join("t_di_exam e", "cu.group_id = e.group_id")
                .join("t_di_exam_join jj", "jj.userid = cu.userid and jj.eid = e.id and jj.exam = 0")
                .where("cu.status = 1")
                .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid = e.id and j.exam = 1) = 0")
                .where("e.id = ?")
                .select()
                .build();
        return pubDao.queryList(String.class, sql.sql(), id);
    }

    /**
     * 获取未报名考试的userid
     * @param id
     * @return
     */
    public List<String> getExamNotJoinIn(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_chat_user cu")
                .field("cu.userid")
                .join("t_user u", "u.userid = cu.userid")
                .join("t_di_exam e", "cu.group_id = e.group_id")
                .where("cu.status = 1")
                .where("(select count(1) from t_di_exam_join j where j.userid = cu.userid and j.eid = e.id ) = 0")
                .where("e.id = ?")
                .select()
                .build();
        return pubDao.queryList(String.class, sql.sql(), id);
    }

    //获取所有报名者信息
    public List<Examiner> getExamJoin(JoinListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_di_exam_join j")
                .field("u.name,u.avatar,j.add_time")
                .join("t_user u","u.userid=j.userid")
                .where("j.eid=:id");
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    //获取报名人数
    public Integer getNumber(Integer id){
        SQL sql=new SQL.SqlBuilder()
                .table("t_di_exam_join j")
                .count()
                .where("j.eid=?")
                .build();
        return pubDao.query(Integer.class,sql.sql(),id);
    }

    //获取完成考试的人数
    public Integer getCompleteNumber(Integer id){
        SQL sql=new SQL.SqlBuilder()
                .table("t_di_exam_join j")
                .count()
                .where("j.eid=?")
                .where("j.exam=1")
                .build();
        return pubDao.query(Integer.class,sql.sql(),id);
    }

    //获取完成考试的人信息
    public List<Examiner> getExaminer(JoinListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_di_exam_join j")
                .field("j.userid,u.name,u.avatar")
                .join("t_user u","u.userid=j.userid")
                .where("j.eid=:id")
                .where("j.exam=1");
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    /**
     * 获取完成考试的人信息
     * 2017-12-4 新增 合并为一条sql查询 , 提高效率
     * @param param
     * @return
     */
    public List<Examiner> getExaminer2(JoinListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .tableCustom("(select count(*) as count from t_di_exam_question q where q.exam_id = :id) a," +
                        "(select j.userid,u.name,u.avatar from t_di_exam_join j " +
                        "join t_user u on u.userid=j.userid " +
                        "where j.eid=:id and j.exam=1 ) b," +
                        "( select count(if(r.right_is = 1,true,null)) as count, r.userid from t_di_exam_record r " +
                        "where r.exam_id = :id group by r.userid ) c")
                .field("round(100*c.count/a.count) as score,b.userid,b.name,b.avatar")
                .where("c.userid = b.userid");
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    //根据查询出总题数和对的题数
    public Topic getTopic(Integer id,String userid){
        //获取总题数
        SQL total=new SQL.SqlBuilder()
                .table("t_di_exam_question q")
                .count()
                .where("q.exam_id=?")
                .build();
        Integer t = pubDao.query(Integer.class, total.sql(), id);

        //获取对的题数
        SQL right=new SQL.SqlBuilder()
                .table("t_di_exam_record r")
                .count()
                .where("r.exam_id=?")
                .where("r.userid=?")
                .where("r.right_is=1")
                .build();
        Integer r = pubDao.query(Integer.class, right.sql(), id, userid);
        Topic topic = new Topic(t,r);
        return topic;
    }


    // 2017 12 15 新增  党委级别考试 按支部统计用户
    // 已完成考试   未考试   已报名


    // 已报名
    public List<Examiner> getJoinListByBranch(UserBranchListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_party_dept_user du")
                .field("du.userid,u.name,u.avatar,j.add_time")
                .join("t_di_exam_join j", "du.userid = j.userid")
                .join("t_user u", "u.userid = du.userid")
                .where("j.eid = :eid and du.party_id = :branchId")
                .select();
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    // 已完成考试
    public List<Examiner> getJoinExamListByBranch(UserBranchListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .tableCustom("(\n" +
                        "select count(*) as count from t_di_exam_question q where q.exam_id = :eid\n" +
                        ") a,\n" +
                        "(\n" +
                        "select j.userid,u.name,u.avatar \n" +
                        "  from t_party_dept_user du \n" +
                        "  join t_user u on u.userid= du.userid\n" +
                        "  join t_di_exam_join j on du.userid = j.userid and j.exam = 1\n" +
                        "  where j.eid = :eid and du.party_id = :branchId\n" +
                        ") b,\n" +
                        "( \n" +
                        "select count(if(r.right_is = 1,true,null)) as count, r.userid from t_di_exam_record r\n" +
                        "where r.exam_id = :eid group by r.userid \n" +
                        ") c ")
                .field("round(100*c.count/a.count) as score,b.userid,b.name,b.avatar")
                .where("c.userid = b.userid")
                .select();
        return pubDao.queryPage(sql, param, Examiner.class);
    }


    // 未考试
    public List<Examiner> getNotExamListByBranch(UserBranchListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_party_dept_user du")
                .field("du.userid,u.name,u.avatar,jj.add_time, if(jj.id > 0, 1, 0) as joinIn")
                .join("t_user u", "u.userid = du.userid")
                .join("t_di_exam_join jj","jj.userid = du.userid and jj.eid = :eid", Join.Type.LEFT)
                .where("(select count(1) from t_di_exam_join j where j.userid = du.userid and j.eid = :eid and j.exam = 1) = 0")
                .where("du.party_id = :branchId")
                .select();
        return pubDao.queryPage(sql, param, Examiner.class);
    }

    // end


    @Autowired
    private PubDao pubDao;
}

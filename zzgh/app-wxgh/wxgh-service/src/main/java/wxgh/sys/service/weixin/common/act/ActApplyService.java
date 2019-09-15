package wxgh.sys.service.weixin.common.act;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.union.SendInfo;
import wxgh.entity.common.ActApply;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.sys.dao.common.act.ActApplyDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:04
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class ActApplyService  {

    @Autowired
    private ActApplyDao actApplyDao;

    
    public List<ActApply> getApplys(ActApplyQuery query) {
        return actApplyDao.getApplys(query);
    }

    
    public ActApply getApply(ActApplyQuery query) {
        return actApplyDao.getApply(query);
    }

    
    @Transactional
    public Integer updateApply(ActApply actApply) {
        return actApplyDao.updateApply(actApply);
    }

    
    @Transactional
    public Integer addApply(ActApply actApply) {
        actApplyDao.save(actApply);
        return actApply.getId();
    }

    
    @Transactional
    public Integer apply(ActApply actApply) {
        return actApplyDao.updateApply(actApply);
    }

    
    @Transactional
    public Integer del(Integer id) {
        return actApplyDao.del(id);
    }

    
    public List<ActApply> applyListRefresh(ActApplyQuery query) {
        return actApplyDao.applyListRefresh(query);
    }

    
    public List<ActApply> applyListMore(ActApplyQuery query) {
        return actApplyDao.applyListMore(query);
    }

    public Integer checkStatus(Integer id) {
        String sql = "select status from t_act_apply where id=" + id;
        Integer status = 0;
        if (pubDao.query(Integer.class, sql) != null) {
            status = 1;
        }
        return status;
    }

    public String getUserid(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_act_apply")
                .field("userid")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }
    
    public List<ActApply> getApplysNotInTargetDeptId(ActApplyQuery query, List<Integer> ids) {
        String in = "";
        if (TypeUtils.empty(ids)) {
            for (Integer id : ids) {
                in += id + ",";
            }
            if(in.length() > 0)
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getActOldestId() != null) {
            and = " and a.id<" + query.getActOldestId() + " ";
        }
        String sql = "SELECT a.*, d.name AS deptName, u.name AS userName FROM t_act_apply a\n" +
                "      LEFT  JOIN t_dept d ON a.deptid = d.deptid JOIN t_user u ON u.userid = a.userid\n" +
                "WHERE a.deptid NOT IN (" + in + ") AND a.status=?\n" + and +
                "ORDER BY a.id DESC limit 0, 15";
        return pubDao.queryList(ActApply.class, sql, query.getStatus());
    }

    
    public SendInfo getSendInfoById(Integer id) {
        String sql = "select '活动举办申请' as sendType,\n" +
                "                s.id as itemId,\n" +
                "                u.name as username,u.userid as userId,\n" +
                "                d.name as deptName\n" +
                "                from t_act_apply s join t_user u on u.userid=s.userid\n" +
                "                join t_dept d on u.deptid=d.deptid \n" +
                "                where s.id=?;";
        return pubDao.query(SendInfo.class, sql, id);
    }

    @Autowired
    private PubDao pubDao;
}



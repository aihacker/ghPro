package wxgh.sys.service.weixin.union.innovation;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.union.QueryParam;
import wxgh.data.union.innovation.work.Apply;
import wxgh.param.union.innovation.work.ApplyQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */
@Service
@Transactional(readOnly = true)
public class WorkApplyService {


    public List<Apply> listApply(ApplyQuery query) {
        String sql = null;
        QueryParam queryParam = whereSql(query, "a");
        if (query.getType() == Apply.TYPE_SHOP) { //工作坊
            sql = "select a.*, " +
                    "s.team_name as name, s.id as mid from t_innovate_apply a " +
                    "join t_innovate_shop s on a.id = s.apply_id " + queryParam.getSql();
        } else if (query.getType() == Apply.TYPE_SUG) { //合理化建议
            sql = "select a.*, s.title as name, s.id as mid from t_innovate_apply a " +
                    "join t_innovate_advice s on a.id = s.pid " + queryParam.getSql();
        }
        return pubDao.queryList(Apply.class, sql, queryParam.getParams());
    }

    @Transactional
    public void delApply(Integer id) {
        String sql = "select apply_type from t_innovate_apply where id=?";
        Integer applyType = pubDao.query(Integer.class, sql, id);
        if (applyType != null) {
            sql = "delete from t_innovate_apply where id=?";
            pubDao.execute(sql, id);

            if (applyType == Apply.TYPE_SHOP) { //工作坊
                sql = "delete from t_innovate_shop where apply_id=?";
                pubDao.execute(sql, id);
            } else if (applyType == Apply.TYPE_SUG) { //创新建议
                sql = "delete from t_innovate_advice where pid=?";
                pubDao.execute(sql, id);
            }
        }
    }

    public Integer countApply(ApplyQuery query) {
        String sql = null;
        QueryParam queryParam = whereSql(query, "a");
        if (query.getType() == Apply.TYPE_SHOP) { //工作坊
            sql = "select count(*) from t_innovate_apply a " +
                    "join t_innovate_shop s on a.id = s.apply_id "
//                    + queryParam.getSql();
            + "where a.apply_type=:type AND a.apply_step=:applyStep AND a.userid=:userid";
        } else if (query.getType() == Apply.TYPE_SUG) { //合理化建议
            sql = "select count(*) from t_innovate_apply a " +
                    "join t_innovate_advice s on a.id = s.pid "
//                    + queryParam.getSql();
                    + "where a.apply_type=:type AND a.apply_step=:applyStep AND a.userid=:userid";
        }
        System.out.println(sql);
        System.out.println(query.getApplyStep());
        System.out.println(query.getUserid());
        System.out.println(query.getType());
        Integer count = pubDao.queryParamInt(sql, query);
        return count == null ? 0 : count;
    }

    public QueryParam whereSql(ApplyQuery query, String alias) {
        String sql = "";
        List<Object> params = new ArrayList();
        if (query.getType() != null) {
            sql += (alias + ".apply_type=? ");
            params.add(query.getType());
        }
        if (query.getStatus() != null) {
            sql += ("AND " + alias + ".status=? ");
            params.add(query.getStatus());
        }
        if (query.getApplyStep() != null) {
            sql += ("AND " + alias + ".apply_step=? ");
            params.add(query.getApplyStep());
        }
        if (query.getDeptid() != null) {
            sql += ("AND " + alias + ".deptid !=? ");
            params.add(query.getDeptid());
        }
        if (query.getUserid() != null) {
            sql += ("AND " + alias + ".userid=? ");
            params.add(query.getUserid());
        }
        if ("".equals(sql)) {
            return null;
        }
        if (sql.startsWith("AND")) {
            sql = sql.substring(3, sql.length());
        }

        //limit
        if (query.getPageIs()) {
            //order by
            sql += "order by " + alias + ".add_time DESC ";

            sql += "limit " + query.getPagestart() + ", " + query.getRowsPerPage();
        }
        sql = "where " + sql.trim();

        return new QueryParam(sql, TypeUtils.listToObject(params));
    }

    @Autowired
    private PubDao pubDao;

}

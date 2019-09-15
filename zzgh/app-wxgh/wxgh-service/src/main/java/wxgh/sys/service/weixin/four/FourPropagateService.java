package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.data.FpShenheData;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.param.four.QueryPropagateParam;
import wxgh.sys.dao.four.PropagateDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
public class FourPropagateService{

    @Autowired
    private PropagateDao propagateDao;
    @Autowired
    private PubDao generalDao;

    @Transactional
    public Integer addPropagete(FourPropagate propagate) {
        propagateDao.save(propagate);
        return propagate.getId();
    }

    public List<FourPropagate> getPropagates(PropagateParam query) {

        SQL sql = buildSql(query);

        return generalDao.queryList(sql.sql(), query, FourPropagate.class);
//        return propagateDao.getPropagates(query);
    }

    private SQL buildSql(PropagateParam query){
        SQL.SqlBuilder sql = new SQL.SqlBuilder();
        if(query.getDeviceStatus() != null && query.getDeviceStatus() == 1){
            sql
                    .field("p.*, d.name AS deptName, pt.name AS fpName, fpc.name AS fpcName, m.`name` AS marketing")
                    .table("four_propagate p")
                    .join("dept d", "p.dept_id = d.deptid", Join.Type.LEFT)
                    .join("four_project pt", "p.fp_id = pt.id")
                    .join("four_project_content fpc", "p.fpc_id = fpc.id")
                    .join("marketing m", "m.`id` = p.`mid`");
        }else{
            sql.field("p.id,p.numb, p.remark, p.budget, p.status, p.suggest,p.device_status, p.userid, p.apply_time, p.mid")
                    .field("p.unit, p.de_id,p.add_is")
                    .field("fd.brand, fd.model_name")
                    .field("d.name AS deptName, pt.name AS fpName")
                    .field("fpc.name AS fpcName, m.`name` AS marketing")
                    .table("four_propagate p")
                    .join("dept d", "p.dept_id = d.deptid", Join.Type.LEFT)
                    .join("four_details fd", "fd.id = p.de_id")
                    .join("four_project pt", "fd.fp_id = pt.id")
                    .join("four_project_content fpc", "fd.fpc_id = fpc.id")
                    .join("marketing m", "m.`id` = p.`mid`");
        }

        if(query.getUserid() != null)
            sql.where("p.userid = :userid");
        if(query.getStatus() != null)
            sql.where("p.status = :status");
        if(query.getDeviceStatus() != null)
            sql.where("p.device_status = :deviceStatus");
        if(query.getId() != null)
            sql.where("p.id = :id");

        return sql.build();
    }

    public FourPropagate getPropagate(PropagateParam query) {

        SQL sql = buildSql(query);

        return generalDao.query(sql.sql(), query, FourPropagate.class);

//        return propagateDao.getPropagate(query);
    }

    @Transactional
    public void delete(Integer id) {
        propagateDao.delete(id);
    }

    @Transactional
    public Integer shenhe(Integer id, Integer status) {
        FpShenheData fpShenheData = new FpShenheData();
        fpShenheData.setId(id);
        fpShenheData.setStatus(status);
        return propagateDao.shenhe(fpShenheData);
    }

    public List<FourPropagate> applyListRefresh(QueryPropagateParam query) {
        return propagateDao.applyListRefresh(query);
    }

    public List<FourPropagate> applyListMore(QueryPropagateParam query) {
        return propagateDao.applyListMore(query);
    }

    public FourPropagate getOne(QueryPropagateParam queryPropagate) {
        return propagateDao.getOne(queryPropagate);
    }

    public List<FourPropagate> getApplysNotInTargetDeptId(QueryPropagateParam query, List<Integer> ids) {
        String in = "";
        if (ids != null) {
            for (Integer id : ids) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getFourOldestId() != null) {
            and = " and p.id<" + query.getFourOldestId() + " ";
        }
        String sql = "SELECT p.*, d.name AS deptName, pt.name AS fpName, fpc.name AS fpcName\n" +
                "FROM t_four_propagate p\n" +
                "LEFT JOIN t_dept d ON p.dept_id = d.deptid JOIN t_four_project pt ON p.fp_id = pt.id JOIN\n" +
                "t_four_project_content fpc ON p.fpc_id = fpc.id\n" +
                "where p.dept_id not in (" + in + ") and p.status=?\n" + and +
                "ORDER BY p.`id` DESC limit 0, 15; ";
        return generalDao.queryList(FourPropagate.class, sql, query.getStatus());
    }

    @Transactional
    public Integer updateOne(FourPropagate fourPropagate) {
        return propagateDao.updateOne(fourPropagate);
    }

    public List<Integer> listCounts() {
        String sql = "select count(*) from t_four_propagate where status=?";
        List<Integer> counts = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            Integer count = generalDao.query(Integer.class, sql, i);
            counts.add(count == null ? 0 : count);
        }
        return counts;
    }

    @Transactional
    public void updateAddIs(Integer id, Integer status) {
        String sql = "update t_four_propagate set add_is = ? where id=?";
        generalDao.execute(sql, status, id);
    }
}

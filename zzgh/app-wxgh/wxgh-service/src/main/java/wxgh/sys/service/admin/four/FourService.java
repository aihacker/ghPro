package wxgh.sys.service.admin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.four.FourDetails;
import wxgh.param.four.FourDetailsParam;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FourService {

    public List<FourDetails> getFourResult(FourDetailsParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("four_details f")
                .join("four_project p", "f.fp_id = p.id")
                .join("four_project_content c", "f.fpc_id = c.id")
                .join("marketing m", "f.mid = m.id")
                .join("dept d", "d.deptid = f.dept_id")
                .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, f.img_ids) ) as thumbs")
                .field("f.*,p.name as fpName,c.name as fpcName")
                .field("m.name as marketName, d.name as deptName")
                .where("f.dept_id = :deptId");

        if (param.getMid() != null) {
            sql.where("f.mid = :mid");
        }

        if(param.getStatus() != null)
            sql.where("f.status = :status");

//        if (param.getUpdateYear() != null) {
//            sql.where("year(f.plan_update) = :updateYear");
//        }
        if(param.getCondit()!=null){
            sql.where("f.condit = :condit");
        }

        return pubDao.queryPage(sql, param, FourDetails.class);
    }

    @Autowired
    private PubDao pubDao;
}

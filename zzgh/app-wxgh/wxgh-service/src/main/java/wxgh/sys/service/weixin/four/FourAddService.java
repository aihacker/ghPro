package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.four.AddParam;
import wxgh.data.four.AddRes;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
@Service
@Transactional(readOnly = false)
public class FourAddService {

    public List<AddRes> byMarket(Integer marketId) {
        if (marketId != null) {
            String sql = "select fp.id, fp.`name` from t_four_details d \n" +
                    "join t_four_project fp on d.fp_id = fp.id\n" +
                    "where d.mid=? GROUP BY fp.`name`";
            return generalDao.queryList(AddRes.class, sql, marketId);
        } else {
            String sql = "select * from t_four_project group by name";
            return generalDao.queryList(AddRes.class, sql);
        }
    }

    public List<AddRes> byFpAndMarket(Integer mid, Integer fpId) {
        if (mid != null) {
            String sql = "select d.fpc_id as id, fpc.`name` from t_four_details d\n" +
                    "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                    "where d.mid=? and d.fp_id = ? GROUP BY d.fpc_id";
            return generalDao.queryList(AddRes.class, sql, mid, fpId);
        } else {
            String sql = "select id, name from t_four_project_content where fp_id = ? GROUP BY `name`";
            return generalDao.queryList(AddRes.class, sql, fpId);
        }
    }

    public List<AddRes> byFpAndMarket(Integer mid, Integer fpId, String name) {
        if (mid != null) {
            String sql = "select d.fpc_id as id, fpc.`name` from t_four_details d\n" +
                    "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                    "where d.mid=? and d.fp_id = ? and fpc.name like concat('%', ?, '%') GROUP BY d.fpc_id";
            return generalDao.queryList(AddRes.class, sql, mid, fpId, name);
        } else {
            String sql = "select id, name from t_four_project_content where fp_id = ? and fpc.name like concat('%', ?, '%') GROUP BY `name`";
            return generalDao.queryList(AddRes.class, sql, fpId, name);
        }
    }

    public List<AddRes> byFpAndMarktAndFpc(AddParam param) {
        if (param.getMid() != null) {
            if (param.getFpcId() == null) {
                String sql = "select d.brand as name from t_four_details d\n" +
                        "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                        "where d.fp_id = ? and fpc.`name`=? and d.mid=? GROUP BY d.brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(), param.getName(), param.getMid());
            } else {
                String sql = "select brand as name from t_four_details\n" +
                        "where fp_id = ? and fpc_id=? and mid=? GROUP BY brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(), param.getFpcId(), param.getMid());
            }
        } else {
            if (param.getFpcId() == null) {
                String sql = "select d.brand as name from t_four_details d\n" +
                        "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                        "where d.fp_id = ? and fpc.`name`=? GROUP BY d.brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(), param.getName());
            } else {
                String sql = "select brand as name from t_four_details\n" +
                        "where fp_id = ? and fpc_id=? GROUP BY brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(), param.getFpcId());
            }
        }

    }

    public List<AddRes> byFpAndMarktAndFpcAndBrand(AddParam param) {
        if (param.getMid() != null) {
            if (param.getFpcId() == null) {
                String sql = "select d.model_name as name, d.id from t_four_details d\n" +
                        "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                        "where d.fp_id = ? and fpc.`name`=? and d.mid=? and d.brand=? GROUP BY d.brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(),
                        param.getName(), param.getMid(), param.getBrand());
            } else {
                String sql = "select model_name as name, id from t_four_details\n" +
                        "where fp_id = ? and fpc_id=? and mid=? and brand=? GROUP BY model_name";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(),
                        param.getFpcId(), param.getMid(), param.getBrand());
            }
        } else {
            if (param.getFpcId() == null) {
                String sql = "select d.model_name as name, d.id from t_four_details d\n" +
                        "join t_four_project_content fpc on d.fpc_id = fpc.id\n" +
                        "where d.fp_id = ? and fpc.`name`=? and d.brand=? GROUP BY d.brand";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(),
                        param.getName(), param.getBrand());
            } else {
                String sql = "select model_name as name, id from t_four_details\n" +
                        "where fp_id = ? and fpc_id=? and brand=? GROUP BY model_name";
                return generalDao.queryList(AddRes.class, sql, param.getFpId(),
                        param.getFpcId(), param.getBrand());
            }
        }
    }

    @Autowired
    private PubDao generalDao;

}

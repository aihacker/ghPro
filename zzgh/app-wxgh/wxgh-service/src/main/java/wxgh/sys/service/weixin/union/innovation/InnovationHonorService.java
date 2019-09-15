package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.union.innovation.honor.HonorData;
import wxgh.param.union.innovation.honor.HonorParam;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-20 17:24
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = false)
public class InnovationHonorService {

    @Autowired
    private PubDao pubDao;

    /**
     * 获取荣誉列表
     * @param honorParam
     * @return
     */
    public List<HonorData> list(HonorParam honorParam){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_work_honor wh")
                .field("wh.name, wh.award_grade as awardGrade, wh.apply_time as applyTime, wh.remark, wh.people")
                .order("wh.apply_time", Order.Type.DESC);

        return pubDao.queryPage(sql, honorParam, HonorData.class);
    }
    
}


package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.data.four.DeptNameId;
import wxgh.data.four.MarketId;
import wxgh.data.four.MarketListData;
import wxgh.entity.four.Marketing;
import wxgh.param.four.MarketListParam;
import wxgh.param.four.QueryMarketingParam;
import wxgh.sys.dao.four.MarketingDao;

import java.util.List;

/**
 * Created by ✔ on 2016/11/16.
 */
@Service
@Transactional(readOnly = true)
public class MarketingService{

    public List<Marketing> getList(QueryMarketingParam queryMarketing) {
        return marketingDao.getList(queryMarketing);
    }

    @Transactional
    public void addMarketing(Marketing marketing) {
        marketingDao.save(marketing);
    }

    @Transactional
    public Integer addOneMaeketing(Marketing marketing) {
        marketingDao.save(marketing);
        return marketing.getId();
    }

    public Marketing getMarketing(String name, Integer deptid) {
        String sql = "select * from t_marketing where name=? and deptid=? ";
        return generalDao.query(Marketing.class, sql, name, deptid);
    }

    public Marketing getMarketing(String name, Integer deptid, Integer id) {
        String sql = "select * from t_marketing where name=? and deptid=? and id != ?";
        return generalDao.query(Marketing.class, sql, name, deptid, id);
    }

    public List<DeptNameId> getDepts() {
        String sql = "select d.deptid,d.name from t_marketing m join t_dept d on m.deptid=d.deptid GROUP BY m.deptid";
        return generalDao.queryList(DeptNameId.class, sql);
    }

    /**
     * 获取营销中心列表
     * @param param
     * @return
     */
    public List<MarketListData> getMarkets(MarketListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("marketing m")
                .field("m.id, m.deptid, m.name, sf.file_path as avatar")
                .field("d.name as companyName")
                .join("sys_file sf", "m.avatar = sf.file_id", Join.Type.LEFT)
                .join("dept d", "d.deptid = m.deptid");
        if(param.getDeptid() != null)
            sql.where("m.deptid = :deptid");
        if(param.getId() != null)
            sql.where("m.id = :id");
        if(param.getName() != null)
            sql.where("m.name like concat('%', :name, '%')");
        return generalDao.queryPage(sql, param, MarketListData.class);
    }

    public List<MarketId> getMarketIds(Integer deptid) {
        String sql = null;
        if (deptid == null) {
            deptid = 1;
            sql = "select id as mid,name from t_marketing where 1=?";
        } else {
            sql = "select id as mid,name from t_marketing where deptid=?";
        }
        return generalDao.queryList(MarketId.class, sql, deptid);
    }

    public Marketing getOne(QueryMarketingParam queryMarketing) {
        return marketingDao.getOne(queryMarketing);
    }

    public List<String> getMarketings() {
        String sql = "select name from t_marketing";
        return generalDao.queryList(String.class, sql);
    }

    @Autowired
    private MarketingDao marketingDao;

    @Autowired
    private PubDao generalDao;
}

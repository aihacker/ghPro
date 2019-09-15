package wxgh.sys.service.weixin.common.suggest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.common.suggest.SuggestCate;
import wxgh.param.common.suggest.SuggestCateParam;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 16:51
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class SuggestCateService {

    @Autowired
    private PubDao pubDao;

    public List<SuggestCate> getCates(SuggestCateParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder();
        sql.table("suggest_cate").where("status = :status");

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        return pubDao.queryList(sql.select().build().sql(), param, SuggestCate.class);
    }

    @Transactional
    public void delete(String id) {
        String sql = "delete from t_suggest_cate where id in(" + id + ")";
        pubDao.execute(sql);
    }

    @Transactional
    public void insertOrUpdate(SuggestCate cate) {
        String sql;
        if (cate.getId() == null) {
            cate.setStatus(1);
            sql = "insert into t_suggest_cate(name, status) values(:name,:status)";
        } else {
            sql = "update t_suggest_cate set name=:name where id = :id";
        }
        pubDao.executeBean(sql, cate);
    }


}


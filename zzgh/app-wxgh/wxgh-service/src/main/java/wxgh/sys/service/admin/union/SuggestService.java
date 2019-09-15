package wxgh.sys.service.admin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.param.union.suggest.SuggestParam;
import wxgh.data.wxadmin.suggest.SuggestList;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-15
 **/
@Service
@Transactional(readOnly = true)
public class SuggestService {

    public List<SuggestList> listSuggest(SuggestParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("user_suggest s").join("user u", "s.userid = u.userid")
                .join("dept d", "d.deptid = s.deptid")
                .join("suggest_cate c", "c.id = s.cate_id")
                .field("s.*,u.userid, u.`name` as username, u.mobile, d.name as deptname, c.name as cateName");

        if (param.getCateId() != null) {
            sql.where("s.cate_id = :cateId");
        }
        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        return pubDao.queryList(sql.select().build().sql(), param, SuggestList.class);
    }

    @Transactional
    public void apply(Integer id, Integer status) {
        String sql = new SQL.SqlBuilder()
                .table("user_suggest")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
    }



    @Autowired
    private PubDao pubDao;
}

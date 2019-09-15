package wxgh.sys.service.wxadmin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.wxadmin.suggest.SuggestInfo;
import wxgh.data.wxadmin.suggest.SuggestList;
import wxgh.param.union.suggest.ListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
@Transactional(readOnly = true)
public class SuggestService {

    @Transactional
    public void delete(Integer id) {
        String sql = "delete from t_user_suggest where id = ?";
        pubDao.execute(sql, id);
    }

    public SuggestInfo getSuggest(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("user_suggest s")
                .field("s.id, s.title, s.content, s.status, s.create_time, s.userid")
                .field("c.name as cateName")
                .field("u.name as username")
                .join("suggest_cate c", "c.id = s.cate_id")
                .join("user u", "u.userid = s.userid")
                .where("s.id = ?")
                .build();
        return pubDao.query(SuggestInfo.class, sql.sql(), id);
    }

    public List<SuggestList> listSuggest(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("user_suggest s")
                .field("s.id, s.title, s.content, s.status, s.create_time as addTime")
                .field("c.id as cateId, c.name as cateName")
                .field("u.name as username")
                .field("d.name as deptname")
                .join("suggest_cate c", "c.id = s.cate_id")
                .join("user u", "u.userid = s.userid")
                .join("dept d", "u.deptid = d.id", Join.Type.LEFT)
                .order("s.create_time", Order.Type.DESC);
        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }
        return pubDao.queryPage(sql, param, SuggestList.class);
    }

    @Transactional
    public void apply(Integer id, Integer status) {
        String sql = "update t_user_suggest set status=? where id = ?";
        pubDao.execute(sql, status, id);
    }

    public String getUserid(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("user_suggest")
                .field("userid")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    @Autowired
    private PubDao pubDao;
}

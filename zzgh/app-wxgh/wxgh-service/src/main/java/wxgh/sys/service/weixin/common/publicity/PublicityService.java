package wxgh.sys.service.weixin.common.publicity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.Page;
import wxgh.data.common.publicity.PublicityData;
import wxgh.data.common.publicity.PublicityList;
import wxgh.entity.common.publicity.Publicity;
import wxgh.param.common.publicity.PublicityParam;
import wxgh.param.common.publicity.PublicityQuery;
import wxgh.param.common.publicity.QueryPublicity;
import wxgh.sys.dao.common.publicity.PublicityDao;

import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-03 10:00
 * ----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class PublicityService {

    @Autowired
    private PublicityDao publicityDao;

    @Autowired
    private PubDao pubDao;


    public List<Publicity> getPublicitys(QueryPublicity queryPublicity) {
        return publicityDao.getPublicitys(queryPublicity);
    }

    public List<Publicity> getList(PublicityParam publicityParam) {
        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("publicity");
        return pubDao.queryPage(builder, publicityParam, Publicity.class);
    }

    public Publicity getPublicity(Integer id) {
        return publicityDao.get(id);
    }


    @Transactional
    public void delete(Integer id) {
        publicityDao.delete(id);
    }


    @Transactional
    public void delete(String id) {
        String[] ids = id.split(",");
        publicityDao.delete(ids);
    }


    @Transactional
    public void update(PublicityData publicityData) {
        publicityDao.update(publicityData);
    }


    @Transactional
    public Integer addPublicity(PublicityData publicityData) {
        return publicityDao.addPublicity(publicityData);
    }


    public List<Publicity> queryPublicity(String name, String content) {
        QueryPublicity queryLaw = new QueryPublicity();
        queryLaw.setContent(content);
        queryLaw.setName(name);
        return publicityDao.queryPublicity(queryLaw);
    }


    public Integer getCount(QueryPublicity queryPublicity) {
        return publicityDao.getCount(queryPublicity);
    }


    @Transactional
    public Integer setTop(PublicityData publicityData) {
        return publicityDao.setTop(publicityData);
    }


    public List<Publicity> applyListRefresh(QueryPublicity query) {
        return publicityDao.applyListRefresh(query);
    }


    public List<Publicity> applyListMore(QueryPublicity query) {
        return publicityDao.applyListMore(query);
    }


    public Publicity getPublicity(QueryPublicity queryPublicity) {
        return publicityDao.getPublicity(queryPublicity);
    }


    public List<Publicity> getNotices(PublicityQuery query) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from t_publicity");
        if (query.getType() != null) {
            sql.append(" where type=:type");
        }
        sql.append(" ORDER BY top desc,time desc");

        if (query.getPageIs()) {
            sql.append(" limit :pagestart, :rowsPerPage");
        }
        return pubDao.queryList(sql.toString(), query, Publicity.class);
    }


    public Integer getNoticeCount(PublicityQuery query) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) from t_publicity");
        if (query.getType() != null) {
            sql.append(" where type=:type");
        }
        return pubDao.queryParamInt(sql.toString(), query);
    }


    @Transactional
    public Integer updateNotice(Integer id, Integer top) {
        String sql = "update t_publicity set top=? where id=?";
        return pubDao.execute(sql, top, id);
    }


    @Transactional
    public void saveNotice(Publicity publicity) {
        publicityDao.save(publicity);
    }


    @Transactional
    public void updateNotice(Publicity publicity) {
        StringBuilder sql = new StringBuilder("update t_publicity set");
        if (publicity.getName() != null) {
            sql.append(" name = :name,");
        }
        if (publicity.getContent() != null) {
            sql.append(" content = :content,");
        }
        if (publicity.getContent() != null) {
            sql.append(" picture = :picture,");
        }
        if (publicity.getTop() != null) {
            sql.append(" top = :top,");
        }
        String upSql = sql.substring(0, sql.length() - 1);
        upSql += " where id=:id";
        pubDao.executeBean(upSql, publicity);
    }


    public List<Publicity> getNotices(String id) {
        String sql = "select * from t_publicity where id in(" + id + ")";
        return pubDao.queryList(Publicity.class, sql);
    }

    public List<PublicityList> listPublicity(Page page) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("publicity")
                .field("id, name as title, time as addTime, type, top, content")
                .order("top", Order.Type.DESC)
                .order("time", Order.Type.DESC);
        return pubDao.queryPage(sql, page, PublicityList.class);
    }
}


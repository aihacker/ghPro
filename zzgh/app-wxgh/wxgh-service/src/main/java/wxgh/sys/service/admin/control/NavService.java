package wxgh.sys.service.admin.control;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.consts.Status;
import wxgh.data.pcadmin.nav.NavInfo;
import wxgh.data.pcadmin.nav.NavList;
import wxgh.data.pcadmin.nav.NavValue;
import wxgh.data.pub.NameValue;
import wxgh.entity.admin.Nav;
import wxgh.entity.admin.NavCate;
import wxgh.param.pcadmin.NavCateParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2017/8/9.
 */
@Service
@Transactional(readOnly = true)
public class NavService {

    public List<NavValue> listNavs() {
        String sql = new SQL.SqlBuilder()
                .table("nav n")
                .field("c.name, c.id")
                .field("n.name as navName, n.id as navId")
                .join("nav_cate c", "n.cate_id = c.id")
                .where("n.parent_id = 0")
                .build()
                .sql();

        Map<Integer, NavValue> navMap = new HashMap<>();
        pubDao.queryList(sql, null, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Integer cateId = resultSet.getInt("id");
                NavValue navValue = navMap.get(cateId);

                String navName = resultSet.getString("navName");
                String navId = resultSet.getString("navId");
                NameValue nav = new NameValue(navName, navId);
                if (navValue == null) {
                    navValue = new NavValue();
                    navValue.setValue(cateId.toString());
                    navValue.setName(resultSet.getString("name"));

                    List<NameValue> navs = new ArrayList<>();
                    navValue.setNavs(navs);
                    navs.add(nav);
                    navMap.put(cateId, navValue);
                } else {
                    navMap.get(cateId).getNavs().add(nav);
                }
            }
        });
        return new ArrayList<>(navMap.values());
    }

    public List<NavCate> getNavCates(NavCateParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nav_cate");
        if (param.getStatus() != null) {
            sql.where("status = :status");
        }
        if (param.getCateId() != null && param.getCateId() != 0) {
            sql.where("id = :cateId");
        }
        if (param.getCates() != null && !"0".equals(param.getCates())) {
            sql.where("id in(" + param.getCates() + ")");
        }
        sql.order("cate_order");
        return pubDao.queryList(sql.build().sql(), param, NavCate.class);
    }

    public List<NavInfo> getNavInfo(Integer cateId) {
        SQL sql = new SQL.SqlBuilder()
                .table("nav n")
                .field("n.id, n.name, n.info, n.url, n.icon, n.parent_id")
                .field("(select name from t_nav where id = n.parent_id) as parentName")
                .where("n.cate_id = ?")
                .order("n.parent_id, n.nav_order")
                .build();
        return pubDao.queryList(NavInfo.class, sql.sql(), cateId);
    }

    public List<NavList> getNavs(Integer cateId, String navId) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nav")
                .order("parent_id", Order.Type.ASC)
                .order("nav_order", Order.Type.ASC);
        List<Nav> navs;
        if (cateId != null && cateId.intValue() != 0) {
            sql.where("cate_id = ?");
            navs = pubDao.queryList(Nav.class, sql.build().sql(), cateId);
        } else {
            navs = pubDao.queryList(Nav.class, sql.build().sql());
        }

        LinkedHashMap<Integer, NavList> map = new LinkedHashMap<>();
        String[] navids = null;
        if (!TypeUtils.empty(navId)) {
            navids = navId.split(",");
        }
        for (Nav n : navs) {
            if (n.getParentId().intValue() == 0) {
                if (navids != null && !TypeUtils.contain(navids, n.getId().toString())) {
                    continue;
                }
                NavList navList = new NavList();
                navList.setId(n.getId());
                navList.setName(n.getName());
                navList.setIcon(n.getIcon());
                navList.setInfo(n.getInfo());
                navList.setNavOrder(n.getNavOrder());
                navList.setParentId(n.getParentId());
                navList.setUrl(n.getUrl());
                map.put(n.getId(), navList);
            } else {
                NavList nvTmp = map.get(n.getParentId());
                if (nvTmp == null) {
                    System.out.println(n.getParentId());
                    continue;
                }
                List<Nav> tmp = nvTmp.getNavs();
                if (tmp == null) {
                    tmp = new ArrayList<>();
                    tmp.add(n);
                    map.get(n.getParentId()).setNavs(tmp);
                } else {
                    map.get(n.getParentId()).getNavs().add(n);
                }
            }
        }
        return new ArrayList<>(map.values());
    }

    @Transactional
    public void delCates(String id) {
        pubDao.execute(SQL.deleteByIds("nav_cate", id));

        //删除nav分类下的所有菜单
        String[] ids = id.split(",");
        String sql = "delete from t_nav where cate_id = ?";

        pubDao.batch(sql, ids);
    }

    @Transactional
    public void delNav(String id) {
        pubDao.execute(SQL.deleteByIds("nav", id));

        //删除子菜单
        String[] ids = id.split(",");
        String sql = "delete from t_nav where parent_id = ?";

        pubDao.batch(sql, ids);
    }

    @Transactional
    public void addNavCate(NavCate navCate) {
        navCate.setStatus(Status.NORMAL.getStatus());
        SQL sql = new SQL.SqlBuilder()
                .field("name, info, status")
                .value(":name, :info, :status")
                .insert("nav_cate")
                .build();
        pubDao.executeBean(sql.sql(), navCate);
    }

    @Transactional
    public void addNav(Nav nav) {
        SQL sql = new SQL.SqlBuilder()
                .field("name, info, url, icon, parent_id, cate_id")
                .value(":name, :info, :url, :icon, :parentId, :cateId")
                .insert("nav")
                .build();
        pubDao.executeBean(sql.sql(), nav);
    }

    @Autowired
    private PubDao pubDao;

    public List<NameValue> getOneNavs(Integer cateId) {
        SQL sql = new SQL.SqlBuilder()
                .table("nav")
                .field("id as value, name")
                .where("parent_id = ?")
                .where("cate_id = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), 0, cateId);
    }
}

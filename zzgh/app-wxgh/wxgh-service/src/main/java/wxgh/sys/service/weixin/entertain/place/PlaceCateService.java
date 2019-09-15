package wxgh.sys.service.weixin.entertain.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.entity.entertain.place.PlaceCate;
import wxgh.param.entertain.place.PlaceCateParam;
import wxgh.sys.dao.entertain.place.PlaceCateDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2016/11/21
 */
@Service
@Transactional(readOnly = true)
public class PlaceCateService {

    @Transactional
    public void addCate(PlaceCate placeCate) {
        placeCateDao.save(placeCate);
    }

    public List<PlaceCate> getCates(Integer status) {
        String sql = "select id,name,info,img_path AS imgPath from t_place_cate where status=?";
        return generalDao.queryList(PlaceCate.class, sql, status);
    }

    public List<PlaceCate> getPlaceCate(String cateIds) {
        String sql = "select id,name,info,img_path AS imgPath from t_place_cate where status=1 and find_in_set(id,?)>0";
        return generalDao.queryList(PlaceCate.class, sql, cateIds);
    }

    public List<PlaceCate> getPlaceCates(String cateIds) {
        String sql = new SQL.SqlBuilder()
                .table("t_place_cate c")
                .field("c.id,c.name,c.info")
                .field("f.file_path as imgPath")
                .join("t_sys_file f", "f.file_id = c.img_path", Join.Type.LEFT)
                .where("c.status=1")
                .where("find_in_set(c.id,?)>0")
                .select()
                .build()
                .sql();
        return generalDao.queryList(PlaceCate.class, sql, cateIds);
    }

    public PlaceCate getCate(Integer id) {
        return placeCateDao.get(id);
    }

    @Transactional
    public void delete(String id) {
        String[] ids = id.split(",");
        placeCateDao.delete(ids);
    }

    public List<PlaceCate> getList(PlaceCateParam placeCateQuery) {
        return placeCateDao.getList(placeCateQuery);
    }

    public PlaceCate getOne(PlaceCateParam placeCateQuery) {
        return placeCateDao.getOne(placeCateQuery);
    }

    @Transactional
    public Integer updateData(PlaceCateParam placeCateQuery) {
        return placeCateDao.updateData(placeCateQuery);
    }

    @Transactional
    public void del(PlaceCate placeCate) {
        placeCateDao.delete(placeCate);
    }

    @Transactional
    public void save(PlaceCate placeCate) {
        placeCateDao.save(placeCate);
    }

    @Autowired
    private PlaceCateDao placeCateDao;

    @Autowired
    private PubDao generalDao;
}

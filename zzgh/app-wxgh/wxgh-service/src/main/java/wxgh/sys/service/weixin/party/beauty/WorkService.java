package wxgh.sys.service.weixin.party.beauty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.party.beauty.Work;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.param.party.beauty.WorkQuery;
import wxgh.sys.dao.union.innovation.WorkDao;

import java.util.ArrayList;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 09:26
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class WorkService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer saveWork(Work work) {
        workDao.save(work);
        return work.getId();
    }

    public Work get(Integer id){
        return workDao.get(id);
    }

    public Work getOne(Integer id) {
        Work work = workDao.show(id);
        String sql = "select thumb, path from t_work_file where work_id=? and status=? and type=?";
        List<WorkFile> files = pubDao.queryList(WorkFile.class, sql, id, 1, WorkFile.TYPE_WORK);
        work.setWorkFiles(files);
        return work;
    }

    public String getUserid(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("work")
                .field("user_id")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    public List<Work> getWorkList(Integer type) {
        return workDao.getWorkList(type);
    }

    public List<Work> listWorks(WorkQuery query) {
        /*
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work w")
//                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, a.file_ids)) files")
                .field("w.id, w.user_id, w.name, w.type, w.remark, w.add_time, w.status")
                .field("u.name as username")
                .field("f.thumb as previewImage, f.path as files")
//                .join("sys_file f", "w.image_id = f.file_id")
                .join("work_file f", "f.work_id = w.id")
                .join("user u", "u.userid = w.user_id")
                .groupBy("w.id")
                .order("w.add_time", Order.Type.DESC);
        if(query.getStatus() != null)
            sql.where("w.status = :status");
        if(query.getType() != null)
            sql.where("w.type = :type");
        return pubDao.queryList(sql.build().sql(), query, Work.class);
        */
        return workDao.listWorks(query);
    }

    public Integer countWorks(WorkQuery query) {
        /*
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work w")
                .join("user u", "u.userid = w.user_id")
//                .join("sys_file f")
                .count();

        if(query.getStatus() != null)
            sql.where("w.status = :status");
        if(query.getType() != null)
            sql.where("w.type = :type");

        Integer count = pubDao.queryParamInt(sql.build().sql(), query);*/
        Integer count = workDao.countWorks(query);
        return count == null ? 0 : count;
    }

    public List<Integer> counts() {
        String sql = "select count(*) from t_work where status=?";
        List<Integer> counts = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            Integer count = pubDao.query(Integer.class, sql, i);
            counts.add(count == null ? 0 : count);
        }
        return counts;
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_work set status = ? where id=?";
        pubDao.execute(sql, status, id);
    }


}


package wxgh.sys.service.admin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.party.match.PhotoMatch;
import wxgh.param.tribe.admin.PhotoParam;
import wxgh.param.union.group.ListParam;
import wxgh.sys.dao.tribe.admin.photo.PhotographDao;

import java.util.List;

/**
 * Created by cby on 2017/8/30.
 */
@Service
@Transactional(readOnly =true)
public class PhotographService {
    @Autowired
    private PubDao pubDao;
    @Autowired
    private PhotographDao photographDao;

    @Transactional
    public void save(PhotoMatch match){
        photographDao.save(match);
    }

    public List<PhotoMatch> get_result(ListParam param){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("sheying_match m")
                .field("m.id,\n" +
                        "\tm.name,\n" +
                        "\ts.file_path as cover,\n" +
                        "\tm.start_time AS startTime,\n" +
                        "\tm.works_type AS worksType")
                .join("t_sys_file s","s.file_id=m.cover");
        List<PhotoMatch> photoMatches = pubDao.queryPage(sql, param, PhotoMatch.class);
        return photoMatches;
    }

    public  List<PhotoParam> totalHuman(ListParam param){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("t_sheying_match_join j")
                .field("j.id,\n" +
                        "\tu.name,\n" +
                        "\tj.name as workName,\n" +
                        "\tj.type,\n" +
                        "\tj.add_time as addTime")
                .join("t_user u","j.userid=u.userid")
                .where("j.status=1")
                .where("j.mid=:userid");
        List<PhotoParam> photoParams = pubDao.queryPage(sql, param, PhotoParam.class);
        return photoParams;
    }

    @Transactional
    public void del_detail(String id){
        pubDao.execute(SQL.deleteByIds("sheying_match_join", id));
    }

    @Transactional
    public void del_work(String id){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("sheying_match_join_vote")
                .where("join_id=?")
                .delete();
        pubDao.execute(sql.build().sql(),id);
    }

    public List<PhotoParam> getworks(ListParam param){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("t_sheying_match_join_vote v")
                .field("v.join_id as id,u.name,j.name as workName,j.type,count(j.name) as vote")
                .join("t_sheying_match_join j","j.id=v.join_id")
                .join("t_user u","u.userid=v.userid")
                .where("j.status=1")
                .where("j.mid=:userid")
                .groupBy("j.name")
                .order("vote", Order.Type.DESC);
        List<PhotoParam> photoParams = pubDao.queryPage(sql, param, PhotoParam.class);
        return photoParams;
    }

}

package wxgh.sys.service.admin.tribe;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.tribe.TribeResultData;
import wxgh.entity.pub.SysFile;
import wxgh.entity.tribe.TribeResult;

import java.util.List;

/**
 * Created by cby on 2017/8/18.
 */
@Service
@Transactional(readOnly = true)
public class TribeResultService {
    @Autowired
    private PubDao pubDao;

    @Transactional
    public int save(TribeResult tribeResult) {
        SQL sql = new SQL.SqlBuilder()
                .field("title,content,add_date,brief_info,author,link,coverImg")
                .value(":title,:content,:addDate,:briefInfo,:author,:link,:coverImg")
                .insert("tribe_result")
                .build();
        Integer id = pubDao.insertAndGetKey(sql.sql(), tribeResult);
        return id;
    }

    public List<TribeResultData> getList(List<Integer> ids){
        String sql = new SQL.SqlBuilder()
                .table("tribe_result t")
                .field("t.*")
                .sys_file("t.coverImg")
                .where("t.id in ("+ TypeUtils.listToStr(ids)+")")
                .select()
                .build()
                .sql();
        return pubDao.queryList(TribeResultData.class, sql);
    }

    //根据id查询出路径
    public SysFile getSysFile(String id){
        String sql="SELECT\n" +
                "\tid,\n" +
                "\tfile_id,\n" +
                "\tfilename,\n" +
                "\tfile_path,\n" +
                "\tmime_type,\n" +
                "\tthumb_path,\n" +
                "\tsize,\n" +
                "\tadd_time,\n" +
                "\tmd5,\n" +
                "\ttype\n" +
                "FROM\n" +
                "\tt_sys_file\n" +
                "WHERE\n" +
                "\tfile_id = ?";
        SysFile query = pubDao.query(SysFile.class, sql, id);
        return query;
    }
}

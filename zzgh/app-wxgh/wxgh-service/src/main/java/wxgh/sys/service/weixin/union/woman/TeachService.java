package wxgh.sys.service.weixin.union.woman;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.Page;
import pub.error.ValidationError;
import wxgh.app.session.user.UserSession;
import wxgh.data.common.FileInfo;
import wxgh.data.entertain.act.JoinList;
import wxgh.data.union.woman.teach.TeachDetail;
import wxgh.data.union.woman.teach.TeachList;
import wxgh.data.union.woman.teach.TeachShow;
import wxgh.entity.union.woman.TeachJoin;
import wxgh.entity.union.woman.WomanTeach;
import wxgh.param.union.woman.JoinParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@Service
@Transactional(readOnly = true)
public class TeachService {

    public List<TeachList> teachList(Page page) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach t")
                .field("t.id, t.name, t.start_time, t.end_time, t.content,t.join_num")
                .sys_file("t.cover")
                .order("t.start_time", Order.Type.DESC);
        List<TeachList> teachs = pubDao.queryPage(sql, page, TeachList.class);
        if (!TypeUtils.empty(teachs)) {
            for (int i = 0, len = teachs.size(); i < len; i++) {
                TeachList t = teachs.get(i);
                teachs.get(i).setTime(DateUtils.formatDate(t.getStartTime(), t.getEndTime()));
            }
        }
        return teachs;
    }

    public TeachList get(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach t")
                .field("t.*")
                .sys_file("t.cover")
                .where("t.id = ?")
                .order("t.start_time", Order.Type.DESC);
        return pubDao.query(TeachList.class, sql.select().build().sql(), id);
    }

    public TeachDetail getDetail(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach t")
                .field("t.*")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, t.files)) as filePaths")
                .field("(select group_concat(filename) from t_sys_file where find_in_set(file_id, t.files)) as fileNames")
                .sys_file("t.cover")
                .where("t.id = ?")
                .order("t.start_time", Order.Type.DESC);
        TeachDetail teachDetail = pubDao.query(TeachDetail.class, sql.select().build().sql(), id);
        teachDetail.setNameList(TypeUtils.strToList(teachDetail.getFileNames()));
        teachDetail.setPathList(TypeUtils.strToList(teachDetail.getFilePaths()));
        return teachDetail;
    }

    public List<JoinList> joinList(JoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach_join j")
                .field("j.add_time, j.userid")
                .field("u.name as username, u.avatar")
                .field("d.name as deptname")
                .join("user u", "u.userid = j.userid")
                .join("dept d", "u.deptid = d.id")
                .where("j.teach_id = :id");
        return pubDao.queryPage(sql, param, JoinList.class);
    }

    @Transactional
    public void join(Integer id) {
        String userid = UserSession.getUserid();
        String sql = "select id from t_woman_teach_join where userid = ? and teach_id = ?";
        Integer joinId = pubDao.query(Integer.class, sql, userid, id);
        if (joinId != null) {
            throw new ValidationError("对不起，您已经报过名哦！");
        }

        //获取限制人数和已报名人数
        TeachShow teachShow = show(id);
        if(teachShow.getJoinTotal() >= teachShow.getJoinNum()){
            throw new ValidationError("对不起，当前报名人数已满");
        }

        TeachJoin join = new TeachJoin();
        join.setAddTime(new Date());
        join.setTeachId(id);
        join.setUserid(userid);

        pubDao.executeBean(JdbcSQL.save(TeachJoin.class), join);
    }

    public TeachShow show(Integer id) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach t")
                .field("t.id, t.name, t.content, t.start_time, t.end_time, t.add_time, t.remark, t.files,t.join_num")
                .sys_file("t.cover")
                .field("(select id from t_woman_teach_join where userid = ? and teach_id = t.id) as joinIs")
                .field("(select count(*) from t_woman_teach_join where teach_id = t.id) as joinTotal")
                .where("t.id = ?");
        TeachShow teachShow = pubDao.query(TeachShow.class, sql.build().sql(), UserSession.getUserid(), id);
        if (teachShow != null) {
            teachShow.setJoinIs(teachShow.getJoinIs() == null ? 0 : 1);
            String joinSql = "select u.avatar from t_woman_teach_join j join t_user u on u.userid = j.userid where j.teach_id = ?";
            List<String> joins = pubDao.queryList(String.class, joinSql, id);
            teachShow.setJoins(joins);

            //文件列表
            if (!TypeUtils.empty(teachShow.getFiles())) {
                String fileSql = "select file_path as path, thumb_path as thumb, filename from t_sys_file where find_in_set(file_id, ?)";
                teachShow.setFileList(pubDao.queryList(FileInfo.class, fileSql, teachShow.getFiles()));
                teachShow.setFiles(null);
            }

            teachShow.setTime(DateUtils.formatDate(teachShow.getStartTime(), teachShow.getEndTime()));
        }
        return teachShow;
    }

    @Transactional
    public void delete(String id) {
        pubDao.execute(SQL.deleteByIds("woman_teach", id));
    }

    @Transactional
    public Integer addTeach(WomanTeach teach) {
        teach.setAddTime(new Date());
        String sql = new SQL.SqlBuilder()
                .field("name,content,start_time,end_time,add_time,cover,remark,files,join_num")
                .value(":name,:content,:startTime,:endTime,:addTime,:cover,:remark,:files,:joinNum")
                .insert("woman_teach")
                .build().sql();
        return pubDao.insertAndGetKey(sql, teach);
    }

    @Transactional
    public void editTeach(WomanTeach teach) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_teach")
                .where("id = :id")
                .update();
        if(teach.getId() == null){
            return;
        }

        if(teach.getName() != null)
            sql.set("name = :name");
        if(teach.getContent() != null)
            sql.set("content = :content");
        if(teach.getStartTime() != null)
            sql.set("start_time = :startTime");
        if(teach.getEndTime() != null)
            sql.set("end_time = :endTime");
        if(teach.getCover() != null)
            sql.set("cover = :cover");
        if(teach.getRemark() != null)
            sql.set("remark = :remark");
        if(teach.getFiles() != null)
            sql.set("files = :files");
        if(teach.getJoinNum() != null)
            sql.set("join_num = :joinNum");

        pubDao.executeBean(sql.build().sql(), teach);
    }

    public List<JoinList> downmsg(Integer id){
        return pubDao.queryList(JoinList.class,"SELECT u.name as username,u.mobile as mobile,d.name as deptname,u.department as dept FROM t_woman_teach_join j INNER JOIN t_user u ON u.userid = j.userid INNER JOIN t_dept d ON u.deptid = d.id WHERE j.teach_id = ?",id);

    }

    @Autowired
    private PubDao pubDao;
}

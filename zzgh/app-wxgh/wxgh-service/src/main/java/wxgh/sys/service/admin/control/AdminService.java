package wxgh.sys.service.admin.control;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.utils.DateUtils;
import wxgh.app.utils.PasswordUtils;
import wxgh.entity.admin.NewAdmin;
import wxgh.param.admin.control.AdminParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
@Transactional(readOnly = true)
public class AdminService {

    @Transactional
    public String editPassword(String oldpassword, String password, Integer id) {

        String sql = "select * from t_new_admin where id = ?";
        NewAdmin oldAdmin = pubDao.query(NewAdmin.class, sql, id);

        if (oldAdmin == null || !PasswordUtils.equles(oldpassword, oldAdmin.getPassword())) {
            return "原密码错误";
        }

        NewAdmin admin = new NewAdmin();
        admin.setPassword(PasswordUtils.getPassword(password));
        admin.setChangeDate(DateUtils.getNow());
        admin.setId(id);
        editAdmin(admin);

        return null;
    }

    public NewAdmin login(String name, String password, HttpSession session) {
        String sql = "select * from t_new_admin where name = ?";
        NewAdmin admin = pubDao.query(NewAdmin.class, sql, name);
        if (admin == null || !PasswordUtils.equles(password, admin.getPassword())) {
            throw new ValidationError("用户名或密码错误");
        }

        int date;
        //登录成功时校验上次修改密码的时间，如果为空设置为0；
        try{
            date = DateUtils.countDays(admin.getChangeDate());
        }catch (Exception e){
            date = 0;
        }

        session.setAttribute("changePwdDate",date);

        return admin;
    }

    public List<NewAdmin> listAdmin(AdminParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("new_admin a")
                .field("a.id, a.admin_id, a.name, a.cate_id, a.remark, a.nav_id")
                .field("(if(a.cate_id=0, '超级管理员', (select GROUP_CONCAT(name) from t_nav_cate where FIND_IN_SET(id,a.cate_id)))) as cateName")
                .order("a.cate_id");
        if (param.getCateId() != null) {
            sql.where("FIND_IN_SET(:cateId, a.cate_id)");
        }
        return pubDao.queryPage(sql, param, NewAdmin.class);
    }

    @Transactional
    public void addAdmin(NewAdmin admin) {
        if (TypeUtils.empty(admin.getPassword())) {
            admin.setPassword("123456");
        }
        //判断管理员是否存在
        String sql = "select id from t_new_admin where name = ?";
        Integer id = pubDao.query(Integer.class, sql, admin.getName());
        if (id != null) {
            throw new ValidationError("管理员已存在哦");
        }

        admin.setPassword(PasswordUtils.getPassword(admin.getPassword()));
        admin.setAdminId(StringUtils.uuid());

        pubDao.executeBean(JdbcSQL.save(NewAdmin.class), admin);
    }

    @Transactional
    public void editAdmin(NewAdmin admin) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("new_admin")
                .update();
        if (admin.getCateId() != null) {
            sql.set("cate_id = :cateId");
        }
        if (admin.getChangeDate() != null) {
            sql.set("change_date = :changeDate");
        }
        if (admin.getRemark() != null) {
            sql.set("remark = :remark");
        }
        if (admin.getPassword() != null) {
            sql.set("password = :password");
        }
        if (admin.getNavId() != null) {
            sql.set("nav_id = :navId");
        }

        //条件
        if (admin.getName() != null) {
            sql.where("name = :name");
        }
        if (admin.getId() != null) {
            sql.where("id = :id");
        }
        pubDao.executeBean(sql.build().sql(), admin);
    }

    @Transactional
    public void delAdmin(String id) {
        pubDao.execute(SQL.deleteByIds("new_admin", id));
    }

    @Autowired
    private PubDao pubDao;
}

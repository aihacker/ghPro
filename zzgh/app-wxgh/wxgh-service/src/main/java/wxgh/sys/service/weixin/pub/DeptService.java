package wxgh.sys.service.weixin.pub;

import com.libs.common.type.TypeUtils;
import com.weixin.bean.call.event.qywx.CreateDept;
import com.weixin.bean.call.event.qywx.DeleteDept;
import com.weixin.bean.call.event.qywx.UpdateDept;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.SQL.SqlBuilder;
import wxgh.entity.pub.Dept;

@Service
@Transactional(readOnly=true)
public class DeptService {

    @Transactional
    public Dept getOne(Integer id)
    {
        String sql = "select * from t_dept where deptid = ?";
        return (Dept)this.pubDao.query(Dept.class, sql, new Object[] { id });
    }

    public Dept getOneById(Integer id) {
        String sql = "select * from t_dept where id = ?";
        return (Dept)this.pubDao.query(Dept.class, sql, new Object[] { id });
    }

    public List<Dept> getListChild(Integer parentid)
    {
        SQL sql = new SQL.SqlBuilder()
                .table("dept")
                .field("*")
                .where("parentid = ?")
                .select()
                .build();
        return this.pubDao.queryList(Dept.class, sql.sql(), new Object[] { parentid });
    }

    public String getDeptNameByUid(String userid)
    {
        SQL sql = new SQL.SqlBuilder()
                .table("dept d")
                .join("user u", "u.deptid = d.id")
                .field("d.name")
                .where("u.userid = ?")
                .limit("1")
                .select()
                .build();
        return (String)this.pubDao.query(String.class, sql.sql(), new Object[] { userid });
    }

    @Transactional
    public void deleteDept(DeleteDept deleteDept)
    {
        SQL sql = new SQL.SqlBuilder()
                .where("deptid = ?")
                .delete("dept")
                .build();
        this.pubDao.execute(sql.sql(), new Object[] { deleteDept.getId() });
    }

    @Transactional
    public void updateDept(UpdateDept updateDept)
    {
        SQL sql = new SQL.SqlBuilder()
                .set("name = :name")
                .set("parentid = (select id from t_dept where deptid = :parentid)")
                .where("deptid = :id")
                .update("dept")
                .build();
        this.pubDao.execute(sql.sql(), new Object[] { updateDept });
    }

    @Transactional
    public void addDept(CreateDept createDept) {
        String sql = "insert into t_dept (deptid, name, parentid, `order`)\nselect :deptid, :name, id, :order from t_dept where deptid = :parentid";

        this.pubDao.execute(sql, new Object[] { createDept });
    }

    /**
     * 获取四小的区分公司列表
     * @return
     */
    public List<Dept> getFourCompany(){
        List<Integer> deptIds = new ArrayList<>();
        deptIds.add(1);
        deptIds.add(331);
        deptIds.add(334);
        deptIds.add(332);
        deptIds.add(335);
        deptIds.add(333);
        deptIds.add(330);
        String sql = new SQL.SqlBuilder()
                .table("dept")
                .field("*")
                .where("deptid in ("+ TypeUtils.listToStr(deptIds)+")")
                .select()
                .build()
                .sql();
        return pubDao.queryList(Dept.class, sql);
    }

    /**
     * 获取分公司列表
     * @return
     */
    public List<Dept> getCompanyList(){
        SQL sql = new SQL.SqlBuilder()
                .table("t_dept d")
                .field("d.*")
                .join("t_dept dp", "dp.id = d.parentid")
                .where("dp.deptid = 1")
                .select()
                .build();
        return pubDao.queryList(Dept.class, sql.sql());
    }

    /**
     * 获取分公司信息
     * @param id dept 表 id
     * @return
     */
    public Dept getComparny(Integer id){
        String sql = "select d.* from t_dept d join t_dept dp on dp.id = d.parentid where dp.deptid = 1 and find_in_set(d.id, query_dept_parent(?))";
        return pubDao.query(Dept.class, sql, id);
    }

    public Integer getDeptId(Integer deptid){
       String sql="select id from t_dept where deptid=?";
       return pubDao.queryInt(sql,deptid);
    }

    @Autowired
    private PubDao pubDao;

}
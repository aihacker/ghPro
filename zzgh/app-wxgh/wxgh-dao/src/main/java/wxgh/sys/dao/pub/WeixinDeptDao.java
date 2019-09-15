package wxgh.sys.dao.pub;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.pub.Dept;
import wxgh.param.pub.dept.WxDeptQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-20 11:07
 *----------------------------------------------------------
 */
@Repository
public class WeixinDeptDao extends MyBatisDao<Dept> {

    
    public Integer addDepts(List<Dept> depts) {
        return getSqlSession().insert("xlkai_addDept", depts);
    }

    
    public Dept getDept(WxDeptQuery query) {
        return getSqlSession().selectOne("xlkai_getDept", query);
    }

    
    public List<Dept> getDepts(WxDeptQuery query) {
        return getSqlSession().selectList("xlkai_getDept", query);
    }

    
    public List<Dept> getUserDepts(String userid) {
        return getSqlSession().selectList("xlkai_getUserDepts", userid);
    }

    
    public Integer insertOrUpdate(List<Dept> depts) {
        return getSqlSession().update("xlkai_insertOrUpdate", depts);
    }

    
    public Integer delWxNotExistDept(List<Dept> Depts) {
        return getSqlSession().delete("xlkai_delWxNotExist", Depts);
    }

    
    public List<String> getDeptNames(List<String> deptIds) {
        return getSqlSession().selectList("xlkai_getDeptNameBydeptIds", deptIds);
    }

    
    public List<Dept> getDepts() {
        return getSqlSession().selectList("getDepts");
    }

    
    public String getDeptStr(String userid) {
        return selectOne("xlkai_getUserDeptsStr", userid);
    }

    
    public Integer updateDept(Dept Dept) {
        return execute("xlkai_updateDept", Dept);
    }

    
    public String getChildDeptid(Integer deptid) {
        return selectOne("getChildDeptid", deptid);
    }

    
    public String getParentDeptid(Integer deptid) {
        return selectOne("getParentDeptid", deptid);
    }

}

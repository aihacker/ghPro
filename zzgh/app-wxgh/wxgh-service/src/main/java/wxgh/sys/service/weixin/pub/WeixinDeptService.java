package wxgh.sys.service.weixin.pub;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.Dept;
import wxgh.param.pub.dept.WxDeptQuery;
import wxgh.sys.dao.pub.WeixinDeptDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-20 11:03
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class WeixinDeptService {

    @Autowired
    private WeixinDeptDao weixinDeptDao;

    
    @Transactional
    public Integer addDepts(List<Dept> depts) {
        return weixinDeptDao.addDepts(depts);
    }

    
    public Dept getDept(WxDeptQuery query) {
        return weixinDeptDao.getDept(query);
    }

    
    public List<Dept> getDepts(WxDeptQuery query) {
        return weixinDeptDao.getDepts(query);
    }

    
//    public Map<String, Integer> getOtherIdByDeptId(Integer deptId) {
//        Map<String, Integer> map = new HashMap<>();
//        WxDeptQuery query = new WxDeptQuery();
//        query.setDeptid(deptId);
//
//        Dept weixinDept1 = weixinDeptDao.getDept(query);
//        if (weixinDept1.getDeptype() == WeixinDept.TYPE_COMPANY) {
//            map.put("company", deptId);
//        } else if (weixinDept1.getDeptype() == WeixinDept.TYPE_AREA) {
//            map.put("area", deptId);
//            query.setDeptid(weixinDept1.getParentid());
//            WeixinDept weixinDept2 = weixinDeptDao.getDept(query);
//            map.put("company", weixinDept2.getDeptid());
//        } else if (weixinDept1.getDeptype() == WeixinDept.TYPE_DEPT) {
//            map.put("dept", deptId);
//            query.setDeptid(weixinDept1.getParentid());
//            WeixinDept weixinDept3 = weixinDeptDao.getDept(query);
//            map.put("area", weixinDept3.getDeptid());
//
//            query.setDeptid(weixinDept3.getParentid());
//            WeixinDept weixinDept4 = weixinDeptDao.getDept(query);
//            map.put("company", weixinDept4.getDeptid());
//        }
//
//        return map;
//    }

    
    public List<Dept> getUserDepts(String userid) {
        return weixinDeptDao.getUserDepts(userid);
    }

    
    public String getDeptStr(String userid) {
        return weixinDeptDao.getDeptStr(userid);
    }

    
    @Transactional
    public Integer updateDept(Dept weixinDept) {
        return weixinDeptDao.updateDept(weixinDept);
    }


    
    public String getChildDeptid(Integer deptid) {
        return weixinDeptDao.getChildDeptid(deptid);
    }

    
    public String getParentDeptid(Integer deptid) {
        return weixinDeptDao.getParentDeptid(deptid);
    }

    
    public String getCompanyName(Integer deptid) {
        String sql = "select name from t_dept where deptid= query_dept_company_id2(?)";
        return pubDao.query(String.class, sql, deptid);
    }

    
    public Integer getCompanyId(Integer deptid) {
        String sql = "select query_dept_company_id2(?)";
        return pubDao.query(Integer.class, sql, deptid);
    }

    
    public String getChildDeptIn(Integer deptid) {
        String sql = "select query_dept_child2(query_dept_company_id(?))";
        return pubDao.query(String.class, sql, deptid);
    }

    
    public Integer countDept(Integer deptid) {
        String deptids = getChildDeptIn(deptid);
        String sql = "select count(*) from t_user where deptid in(" + deptids + ")";
        return pubDao.query(Integer.class, sql);
    }

    
    public List<String> getDepts(Integer parentid) {
        String sql = "select name from t_dept where parentid=?";
        return pubDao.queryList(String.class, sql, parentid);
    }

    /**
     * 获取全部部门
     *
     * @return
     */
    private Map<Integer, Dept> getDepts() {
        String sql = "select deptid, parentid from t_dept";
        List<Dept> depts = pubDao.queryList(Dept.class, sql);
        Map<Integer, Dept> map = new HashMap<>();
        if (depts != null) {
            for (Dept dept : depts) {
                map.put(dept.getDeptid(), dept);
            }
        }
        return map;
    }

    @Autowired
    private PubDao pubDao;
    
}

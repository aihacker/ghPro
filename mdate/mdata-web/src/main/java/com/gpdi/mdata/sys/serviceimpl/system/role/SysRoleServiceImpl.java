package com.gpdi.mdata.sys.serviceimpl.system.role;

import com.gpdi.mdata.sys.dao.system.SysModuleDao;
import com.gpdi.mdata.sys.dao.system.SysRoleDao;
import com.gpdi.mdata.sys.dao.system.SysRoleModuleDao;
import com.gpdi.mdata.sys.dao.system.SysUserDao;
import com.gpdi.mdata.sys.entity.system.SysModule;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.web.system.data.QueryData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.util.*;

/**
 *
 */
@Service
@Transactional(readOnly = false)
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysModuleDao sysModuleDao;
    @Autowired
    private SysRoleModuleDao sysRoleModuleDao;

    @Autowired
    private GeneralDao generalDao;

    @Override
    @Transactional
    public void delete(Integer id) {
        sysRoleDao.delete(id);
        String sqlM = "delete from sys_role_module where role_id=" + id;
        String sqlU = "delete from sys_role_user where role_id=" + id;
        generalDao.execute(sqlM);
        generalDao.execute(sqlU);
    }



    @Override
    @Transactional
    public Integer save(SysRole sysRole) {
        if (sysRole.getRoleId() == null) {
            sysRoleDao.save(sysRole);
            List<SysModule> sysModules = sysModuleDao.getAll();
            List<SysUser> sysUsers = sysUserDao.getAll();
            StringBuilder sb = new StringBuilder("insert into sys_role_module (role_id,module_id,is_enable) values");
            StringBuilder sbU = new StringBuilder("insert into sys_role_user (role_id,user_id,is_enable) values");
            int roleid = sysRole.getRoleId();
            for (int i = 0; i < sysModules.size(); i++) {
                sb.append("(").append(roleid).append(",").append(sysModules.get(i).getModuleId()).append(",").append(1).append(" )").append(",");
            }
            for (int i = 0; i < sysUsers.size(); i++) {
                sbU.append("(").append(roleid).append(",").append(sysUsers.get(i).getUserId()).append(",").append(1).append(" )").append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sbU.deleteCharAt(sbU.length() - 1);
            generalDao.execute(sb.toString());
            generalDao.execute(sbU.toString());
        }
        sysRoleDao.save(sysRole);
        return sysRole.getRoleId();
    }

    @Override
    public SysRole get(Integer id) {
        return sysRoleDao.get(id);
    }

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String where = "";

        if (StrFuncs.notEmpty(queryData.getName())) {
            where += " and i.role_name like :name";
            query.put("name", StrFuncs.anyLike(queryData.getName()));
        }
        query.select("i.*")
                .from("sys_role i")
                .where(where)
                .orderBy(" role_code ");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleDao.getAll();
    }

    @Override
    public void saveModuleByRole(List<SysRole> list , Integer moduleId) {
        StringBuilder sb = new StringBuilder("insert into sys_role_module (role_id,module_id,is_enable) values");
        for (int i = 0; i < list.size(); i++) {
            sb.append("(").append(list.get(i).getRoleId()).append(",").append(moduleId).append(",").append(1).append(" )").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        generalDao.execute(sb.toString());
    }

    @Override
    public String formTree(Integer roleId) {
        //List<SysRole> list = sysRoleDao.getAll();
        JSONArray jsonArray = new JSONArray();
        //List<SysModule> subtreeList = sysModuleDao.getRoleMoudleTree(roleId);
        String sql = "SELECT a.* ,b.is_enable from sys_module a left join " +
                "(SELECT module_id,is_enable FROM sys_role_module WHERE role_id="+roleId+") b on a.module_id=b.module_id";
        List<SysModule> subtreeList =generalDao.queryList(SysModule.class,sql);
        for (SysModule m : subtreeList) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("id", m.getModuleId());
            jsonObject2.put("pId", m.getParentId());
            jsonObject2.put("name", m.getModuleName());
            jsonObject2.put("value", m.getIsEnable());
            if (m.getIsEnable()!= null && m.getIsEnable() == 0) {
                jsonObject2.put("checked", true);
            }
            jsonObject2.put("open", true);
            jsonArray.add(jsonObject2);
        }
        return toArray(jsonArray);
    }

    @Override
    public void saveTree(Integer roleId, String array[]) {
        Map<String, Object> map = new HashMap<>();
        map.put("array", array);
        map.put("roleId", roleId);
        sysRoleModuleDao.updataTree(map);
    }


    private String toArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            list.add(jsonArray.getString(i));
        }
        String[] stringArray = list.toArray(new String[list.size()]);
        StringBuilder sb = new StringBuilder("[");
        int len = stringArray.length;
        for (int a = 0; a < len; a++) {
            sb.append(stringArray[a]).append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}

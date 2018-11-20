package com.gpdi.mdata.sys.serviceimpl.system.user;
import com.gpdi.mdata.pub.BaseQuery;
import com.gpdi.mdata.sys.dao.system.*;
import com.gpdi.mdata.sys.entity.system.*;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.sys.utils.JsonUtil;
import com.gpdi.mdata.web.system.data.QueryData;
import com.gpdi.mdata.web.system.data.UserData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.ListQuery;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
@Service
@Transactional(readOnly = false)
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysModuleDao sysModuleDao;

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private SysVarDao sysVarDao;

    @Override
    @Transactional
    public void delete(Integer id) {
        sysUserDao.delete(id);
        String sql="delete from sys_role_user where user_id="+id;
        generalDao.execute(sql);
    }

    @Override
    @Transactional
    public void save(UserData userData) {
//        String del="delete from sys_role_user where user_id="+userData.getUserId();
//        String addRole="INSERT INTO sys_role_user ( role_id,user_id,is_enable ) ";
//        int size=userData.getRoles().size();
//        for(int i=0;i<size-1;i++){
//            addRole+="select "+userData.getRoles().get(i)+","+userData.getUserId()+",0"+" UNION ALL ";
//        }
//        addRole+="select "+userData.getRoles().get(size-1)+","+userData.getUserId()+",0 ;";
//
//        String delRegion="delete from user_data_region where user_id="+userData.getUserId();
//        String addRegion = "INSERT INTO user_data_region ( user_id,region_id ) ";
//        int reSize=userData.getRegions().size();
//        for(int i=0;i<reSize-1;i++){
//            addRegion+="select "+userData.getUserId()+","+userData.getRegions().get(i)+" UNION ALL ";
//        }
//        addRegion+="select "+userData.getUserId()+","+userData.getRegions().get(reSize-1)+" ;";
//        generalDao.execute(del);
//        generalDao.execute(addRole);
//        generalDao.execute(delRegion);
//        generalDao.execute(addRegion);
//        generalDao.execute(sqlR);
        //String sql = "UPDATE sys_role_user SET is_enable=1 WHERE role_id in() AND user_id=? ";
        StringBuilder setAllRole = new StringBuilder("UPDATE sys_role_user SET is_enable=1 WHERE role_id in(");
        List<Integer> allRoleId = sysRoleDao.getAllRoleId();//全部角色id
        //先设置所有用户角色不可用，在设置用户提交的角色id为0可用。
        for (int i = 0; i < allRoleId.size(); i++) {
            setAllRole.append(allRoleId.get(i)).append(",");
        }
        setAllRole.deleteCharAt(setAllRole.length() - 1);
        setAllRole.append(")").append(" and user_id=").append(userData.getUserId());
        generalDao.execute(setAllRole.toString());
        //设置可用的角色
        List<Integer> userRoles = userData.getRoles();
        if (userRoles != null) {
            StringBuilder setUserRole = new StringBuilder("UPDATE sys_role_user SET is_enable=0 WHERE role_id in(");
            for (int i = 0; i < userData.getRoles().size(); i++) {
                setUserRole.append(userRoles.get(i)).append(",");
            }
            setUserRole.deleteCharAt(setUserRole.length() - 1);
            setUserRole.append(")").append(" and user_id=").append(userData.getUserId());
            generalDao.execute(setUserRole.toString());
        }
        //保存区域管理
        //采用删除的新增的方式为用户修改数据访问区域。
        String delRegion = "delete from user_data_region where user_id=" + userData.getUserId();
        generalDao.execute(delRegion);
        String addRegion = "INSERT INTO user_data_region ( user_id,region_id ) ";
        if (userData.getRegions() != null) {
            int reSize = userData.getRegions().size();
            for (int i = 0; i < reSize - 1; i++) {
                addRegion += "select " + userData.getUserId() + "," + userData.getRegions().get(i) + " UNION ALL ";
            }
            addRegion += "select " + userData.getUserId() + "," + userData.getRegions().get(reSize - 1) + " ;";
            generalDao.execute(addRegion);
        }
    }
//        System.out.println("setAllRole----------"+setAllRole);
//        System.out.println("setUserRole----------"+setUserRole);
//    public Integer saveAndToJOB(SysUser sysUser) {
//        sysUserDao.saveAndToJOB(sysUser);
//        return sysUser.getUserId();
//    }

    @Override
    public Integer savePass(SysUser sysUser) {
        sysUserDao.save(sysUser);
        return sysUser.getUserId();
    }

//    @Override
//    public SysUser get(Integer user_id) {
//        return sysUserDao.get(user_id);
//    }

    @Override
    public SysUser getUser(Integer id) {
        return sysUserDao.getUser(id);
    }

    @Override
    public Integer save(SysUser sysUser) {
        //AESOperator aesOperator=AESOperator.getInstance();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if(sysUser.getId()==null){
//            // dataSource.setPassword(aesOperator.encrypt(dataSource.getPassword()));
//            dataSource.setCreate_time(sdf.parse(sdf.format(new Date())));
//        } else {
////            String bPass= dataSourceDao.get(dataSource.getId()).getPassword();
////            if(!bPass.equals(dataSource.getPassword())){
////                dataSource.setPassword(aesOperator.encrypt(dataSource.getPassword()));
////            }
//            dataSource.setUpdate_time(sdf.parse(sdf.format(new Date())));
//        }
        sysUserDao.save(sysUser);
        return sysUser.getUserId();
    }

    @Override
    public SysUser getUser(String userName) {
        return sysUserDao.getUser(userName);
    }

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String where = "";

        if (StrFuncs.notEmpty(queryData.getName())) {
            where += " and i.user_name like :name";
            query.put("name", StrFuncs.anyLike(queryData.getName()));
        }
        query.select("i.*")
                .from("sys_user i")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public UserData getUserData(Integer id) {
        UserData userData = new UserData();
        List<Integer> userRoles = sysRoleDao.getRoleByUserId(id);
        List<SysRole> allRole=sysRoleDao.getAll();
        userData.setAllRoles(allRole);
        userData.setRoles(userRoles);

        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<allRole.size();i++){
            SysRole all=allRole.get(i);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id", all.getRoleId());
            jsonObject.put("pId", 0);
            jsonObject.put("name",all.getRoleName());
            jsonObject.put("open", false);
            //加载角色下的所有可用功能模块
            List<SysModule> treeList = sysModuleDao.getUsedMoudleByRole(all.getRoleId());
            for (SysModule sysModule : treeList) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id",  UUID.randomUUID());
                jsonObject2.put("pId",all.getRoleId());
                jsonObject2.put("name", sysModule.getModuleName());
                jsonObject2.put("open", true);
                jsonObject2.put("nocheck", true);
                jsonArray.add(jsonObject2);
            }
            for(int j=0;j<userRoles.size();j++){
                SysRole sysRole=allRole.get(i);
//                System.out.print("-------------sysRole.getRoleId():"+sysRole.getRoleId()+"  userRoles.get(j):"+userRoles.get(j));
//                System.out.println(sysRole.getRoleId()==userRoles.get(j));
                if(sysRole.getRoleId().equals(userRoles.get(j))){
                    jsonObject.put("checked", true);
                    break;
                }
            }
            jsonArray.add(jsonObject);
        }
        userData.setTree(JsonUtil.toArray(jsonArray));

        return userData;
    }

    @Override
    public List<SysRole> getAllRole() {
        return sysRoleDao.getAll();
    }

    @Override
    public List<SysUser> getAllUser() { return generalDao.queryList(SysUser.class,"select * from sys_user"); }

    @Override
    public  List<String> getModuleByRoleSAndParent(com.gpdi.mdata.sys.entity.system.QueryData queryData) {
       String sql ="SELECT m.module_code FROM sys_module m ,sys_role_module rm where " +
                "m.module_id=rm.module_id and rm.is_enable=0 and rm.role_id in (" +
                "SELECT  role_id FROM sys_role_user WHERE user_id="+queryData.getParentId()+"  and is_enable=0" +
                ") and m.parent_id in (" +
                "select module_id from sys_module mm where mm.module_code='"+queryData.getModuleCode()+"'" +
                ") GROUP BY m.module_code";
        List<String> list = generalDao.queryList(String.class,sql);
        return list;
        //return sysModuleDao.getMoudleByRoleAndParent(queryData);
    }

    @Override
    public List<Integer> getRoleByUser(Integer id) {
        return sysRoleDao.getRoleByUserId(id);
    }
}

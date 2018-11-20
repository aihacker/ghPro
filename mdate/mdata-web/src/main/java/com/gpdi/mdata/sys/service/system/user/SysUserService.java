package com.gpdi.mdata.sys.service.system.user;

import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.web.system.data.QueryData;
import com.gpdi.mdata.web.system.data.UserData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.text.ParseException;
import java.util.List;

/**
 *
 */
public interface SysUserService {

    /**
     * 删除操作
     * @param id
     */
    void delete(Integer id);

    /**
     * 保存操作
     * @param
     * @return
     */
    void save(UserData userData);

    /**
     * 保存密码
     *
     * @return
     */
    Integer savePass(SysUser sysUser);

//    /**
//     * 根据id查询操作
//     * @param id
//     * @return
//     */
//    SysUser get(Integer id);


    /**
     * 根据id查询操作
     * @param id
     * @return
     */
    SysUser getUser(Integer id);


    /**
     * 保存操作
     * @param sysUser
     * @return
     */
    Integer save(SysUser sysUser) throws ParseException;

    SysUser getUser(String userName);

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult query(QueryData queryData, QuerySettings settings);

    UserData getUserData(Integer id);

    List<SysRole> getAllRole();

    List<SysUser> getAllUser();

    List<String> getModuleByRoleSAndParent(com.gpdi.mdata.sys.entity.system.QueryData queryData);

    List<Integer> getRoleByUser(Integer id);
}

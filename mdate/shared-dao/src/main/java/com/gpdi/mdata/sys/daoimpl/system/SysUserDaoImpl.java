package com.gpdi.mdata.sys.daoimpl.system;
        import com.gpdi.mdata.sys.dao.system.SysUserDao;
        import com.gpdi.mdata.sys.entity.system.SysUser;
        import org.springframework.stereotype.Repository;
        import pub.dao.mybatis.MyBatisDao;

        import java.util.List;

@Repository
public class SysUserDaoImpl extends MyBatisDao<SysUser> implements SysUserDao {
    public SysUserDaoImpl(){
        super(SysUser.class);
    }

    @Override
    public void savePass(SysUser sysUser) {
        getSqlSession().update("com.gpdi.mdata.sys.entity.system.SysUser.savepass",sysUser);
    }

    @Override
    public SysUser getUser(Integer id) {
        return getSqlSession().selectOne("com.gpdi.mdata.sys.entity.system.SysUser.getUser",id);
    }

    @Override
    public SysUser getUser(String userName) {
        return getSqlSession().selectOne("com.gpdi.mdata.sys.entity.system.SysUser.selectUser",userName);
    }

    @Override
    public SysUser getUserByStaffId(String staffId) {
        return getSqlSession().selectOne("com.gpdi.mdata.sys.entity.system.SysUser.getUserByStaffId",staffId);
    }

    @Override
    public List<String> getAllStaffId() {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysUser.getAllStaffId");
    }

    @Override
    public List<SysUser> getUserByStaffIds(String[] list) {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysUser.getUserByStaffIds",list);
    }
}

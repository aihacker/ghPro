package wxgh.sys.dao.pub;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.pub.UserInfo;
import wxgh.param.pub.user.UserInfoQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:28
 *----------------------------------------------------------
 */
@Repository
public class UserInfoDao extends MyBatisDao<UserInfo> {

    
    public Integer insertOrUpdate(UserInfo userInfo) {
        return execute("xlkai_insertOrUpdate", userInfo);
    }

    
    public List<UserInfo> getInfos(UserInfoQuery query) {
        //System.out.println("selectList(\"get_applyList\", query)   size = "+selectList("get_applyList", query).size());
        return getSqlSession().selectList("get_applyList", query);
    }

    
    public UserInfo getUserInfoByUid(String uid) {
        return selectOne("getUserInfoByUid", uid);
    }

    
    public Integer delUserInfo(String uid) {
        return getSqlSession().delete("delUserInfo", uid);
    }

    
    public List<UserInfo> applyListRefresh(UserInfoQuery query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<UserInfo> applyListMore(UserInfoQuery query) {
        return selectList("applyListMore", query);
    }
    
}


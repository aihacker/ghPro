package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.User;
import wxgh.entity.pub.UserInfo;
import wxgh.param.pub.user.UserInfoQuery;
import wxgh.sys.dao.pub.UserInfoDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:31
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer insertOrUpdate(UserInfo userInfo) {
        return userInfoDao.insertOrUpdate(userInfo);
    }

    public UserInfo getInfo(String userid) {
        return userInfoDao.getUserInfoByUid(userid);
    }

    public UserInfo getUserInfoByUid(String uid) {
        return userInfoDao.getUserInfoByUid(uid);
    }

    @Transactional
    public Integer delUserInfo(String uid) {
        return userInfoDao.delUserInfo(uid);
    }

    public List<UserInfo> getInfos(UserInfoQuery query) {
        return userInfoDao.getInfos(query);
    }

    public List<UserInfo> applyListRefresh(UserInfoQuery query) {
        return userInfoDao.applyListRefresh(query);
    }

    public List<UserInfo> applyListMore(UserInfoQuery query) {
        return userInfoDao.applyListMore(query);
    }

    public List<Integer> getTestUserDeptIds(Integer parentDeptId) {
        String sql = "select deptid from t_dept where deptid=? or parentid=?;";
        return pubDao.queryList(Integer.class, sql, parentDeptId, parentDeptId);
    }

    public List<UserInfo> getApplysNotInTargetDeptId(UserInfoQuery query, List<Integer> deptIds, Integer type) {
        String in = "";
        if (deptIds != null) {
            for (Integer id : deptIds) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getUserOldestId() != null && !query.getUserOldestId().equals("")) {
            and = " and u.id<" + query.getUserOldestId() + " ";
        }
        String sql = "select ui.*\n" +
                "from t_user_info ui join t_user u on u.userid=ui.user_id\n" +
                "where u.deptid not in (" + in + ") \n" + and +
                "and u.apply_status=? and u.step_numb=?\n" +
                "order by u.id desc limit 0,15;";
        List<UserInfo> userInfos = pubDao.queryList(UserInfo.class, sql, query.getApplyStatus(), query.getStepNumb());
        if (userInfos.size() > 0) {
            for (UserInfo userInfo : userInfos) {
                String sql1 = "select * from t_user where userid=?";
                User user = pubDao.query(User.class, sql1, userInfo.getUserId());
                userInfo.setUser(user);
            }
        }

        return userInfos;
    }
}



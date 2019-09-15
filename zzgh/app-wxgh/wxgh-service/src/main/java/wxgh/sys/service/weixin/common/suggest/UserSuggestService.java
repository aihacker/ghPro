package wxgh.sys.service.weixin.common.suggest;


import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.common.suggest.UserSuggest;
import wxgh.param.common.suggest.SuggestParam;
import wxgh.param.common.suggest.UserSuggestQuery;
import wxgh.sys.dao.common.suggest.UserSuggestDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-27 16:58
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class UserSuggestService {

    @Autowired
    private UserSuggestDao userSuggestDao;

    @Autowired
    private PubDao pubDao;
//    @Autowired
//    private UserScoreDao userScoreDao;

    public List<UserSuggest> getSuggests(UserSuggestQuery query) {
        query.setDeptid(query.getDeptid());
        return userSuggestDao.getSuggest(query);
    }

    public List<UserSuggest> getSuggestList(SuggestParam suggestParam){
        SQL.SqlBuilder builder = new SQL.SqlBuilder()
                .table("t_user_suggest");
//        if(suggestParam.getDeptid() != null)
//            builder.where("deptid = :deptid");
        if(suggestParam.getCid() != null)
            builder.where("cate_id = :cid");
        if(suggestParam.getStatus() != null)
            builder.where("status = :status");
        return pubDao.queryPage(builder, suggestParam, UserSuggest.class);
    }
   
    @Transactional
    public Integer updateCommNumb(Integer id) {
        String sql = "UPDATE t_user_suggest SET comm_num=comm_num+1 WHERE id=" + id;
        return pubDao.execute(sql);
    }

   
    @Transactional
    public Integer updateLovNumb(Integer id) {
        String sql = "UPDATE t_user_suggest SET lov_num=lov_num+1 WHERE id=" + id;
        return pubDao.execute(sql);
    }

   
    @Transactional
    public Integer updateTranNum(Integer id) {
        String sql = "UPDATE t_user_suggest SET tran_num=tran_num+1 WHERE id=" + id;
        return pubDao.execute(sql);
    }

   
    @Transactional
    public Integer updateSeeNum(Integer id) {
        String sql = "UPDATE t_user_suggest SET see_num=see_num+1 WHERE id=" + id;
        return pubDao.execute(sql);
    }

   
    public UserSuggest getSuggest(Integer id) {
        UserSuggest UserSuggest = userSuggestDao.getSuggest(id);
        if (UserSuggest != null && UserSuggest.getCreateTime() != null) {
            UserSuggest.setTimeStr(DateUtils.formatDate(UserSuggest.getCreateTime()));
        }
        return UserSuggest;
    }

   
    @Transactional
    public Integer addSuggest(UserSuggest UserSuggest) {
        userSuggestDao.save(UserSuggest);
        return UserSuggest.getId();
    }

   
    public List<UserSuggest> getSuggestss(UserSuggestQuery query) {
        return userSuggestDao.getSuggests(query);
    }

   
    public Integer getCount(UserSuggestQuery userSuggestQuery) {
        return userSuggestDao.getCount(userSuggestQuery);
    }

   
    @Transactional
    public Integer shenhe(UserSuggestQuery userSuggestQuery) {
        return userSuggestDao.shenhe(userSuggestQuery);
    }

   
    public UserSuggest getOneSuggest(Integer id) {
        return userSuggestDao.getOneSuggest(id);
    }

   
    @Transactional
    public Integer del(Integer id) {
        return userSuggestDao.del(id);
    }

   
    public List<UserSuggest> applyListRefresh(UserSuggestQuery query) {
        return userSuggestDao.applyListRefresh(query);
    }

   
    public List<UserSuggest> applyListMore(UserSuggestQuery query) {
        return userSuggestDao.applyListMore(query);
    }

   
    public List<UserSuggest> getApplysNotInTargetDeptId(UserSuggestQuery query, List<Integer> ids) {
        String in = "";
        if (ids != null) {
            for (Integer id : ids) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getUserSuggestOldestId() != null) {
            and = " and us.id<" + query.getUserSuggestOldestId() + " ";
        }
        String sql = "SELECT us.*, sc.name AS cateName, u.name AS userName, wd.name AS deptname\n" +
                "FROM t_user_suggest us\n" +
                "JOIN t_suggest_cate sc ON sc.id = us.cate_id\n" +
                "JOIN t_user u ON u.userid = us.userid\n" +
                "JOIN t_dept wd ON wd.deptid = us.deptid\n" +
                "where us.deptid not in (" + in + ") and us.status=?\n" + and +
                "ORDER BY us.id DESC limit 0, 15";
        return pubDao.queryList(UserSuggest.class, sql, query.getStatus());
    }

   
//    public SendInfo getSendInfoById(Integer id) {
//        String sql = "select '建言池申请' as sendType,\n" +
//                "s.id as itemId,\n" +
//                "u.name as username,u.userid as userId,\n" +
//                "d.name as deptName\n" +
//                "from t_user_suggest s join t_user u on u.userid=s.userid\n" +
//                "join t_dept d on u.deptid=d.deptid \n" +
//                "where s.id=?;";
//        return pubDao.queryValue(SendInfo.class, sql, id);
//    }

   
    @Transactional
    public Integer addScore(String userId, Integer addId) {
        Integer status = 0;

        String sql1 = "select count(id) from t_suggest_lov " +
                "where userid=? and sug_id=?";
        String sql2 = "select count(id) from t_suggest_comm " +
                "where userid=? and sug_id=? and parentid=0";

        Integer isLovExist = pubDao.query(Integer.class, sql1, userId, addId);
        Integer isCommExist = pubDao.query(Integer.class, sql2, userId, addId);

        String isAddScore;
        if (isCommExist == 1 && isLovExist == 0) {//第一次评论
            isAddScore = "add";
        } else if (isCommExist == 0 && isLovExist == 1) {//第一次点赞
            isAddScore = "add";
        } else {
            isAddScore = "not";
        }

//        if (isAddScore.equals("add")) {
//            UserScore userScore = new UserScore();
//            userScore.setUserid(userId);
//            userScore.setScore(1f);
//            userScore.setAddTime(new Date());
//            userScore.setAddType("lov_comm_suggest");
//            userScore.setAddId(addId);
//            userScore.setStatus(1);
//            userScore.setScoreType(2);
//            userScore.setGroupStr("association_score");
//            userScore.setJsTime(DateFuncs.dateTimeToStr(new Date(), "yyyy-MM-dd"));
//            userScoreDao.save(userScore);
//            status = userScore.getId();
//        }
        return status;
    }

}


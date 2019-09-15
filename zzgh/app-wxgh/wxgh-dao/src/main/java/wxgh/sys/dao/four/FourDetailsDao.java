package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.four.DetailInfo;
import wxgh.data.four.FourDetailsData;
import wxgh.entity.four.FourDetails;
import wxgh.param.four.FourDetailsParam;
import wxgh.param.four.FourListParam;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Repository
public class FourDetailsDao extends MyBatisDao<FourDetails>{


    public List<FourDetails> getDetails(FourDetailsParam query) {
        return selectList("xlkai_getDetails", query);
    }

    public FourDetails getDetail(FourDetailsParam query) {
        return selectOne("xlkai_getDetails", query);
    }

    public void addFD(FourDetailsData fourDetailsData) {
        getSqlSession().insert("liuhe.sys.entity.FourDetails.addFD", fourDetailsData);
    }

    public List<FourDetails> getFDs(FourDetailsParam fourDetailsQuery) {
        return selectList("zzj_getList", fourDetailsQuery);
    }

    public Integer getCount(FourDetailsParam fourDetailsQuery) {
        return selectOne("getCount", fourDetailsQuery);
    }

    public Integer delFD(Integer id) {
        return getSqlSession().delete("liuhe.sys.entity.FourDetails.delFD", id);
    }

    public List<FourDetails> getdeptStrList() {
        return selectList("getdeptStrList");
    }

    public List<FourDetails> getList(FourDetailsParam fourDetailsQuery) {
        return selectList("zzj_getList", fourDetailsQuery);
    }

    public FourDetails getOne(FourDetailsParam fourDetailsQuery) {
        return selectOne("zzj_getList", fourDetailsQuery);
    }

    public List<FourDetails> getListWithMarketing(FourDetailsParam fourDetailsQuery) {
        return selectList("zzj_getListWithMarketing", fourDetailsQuery);
    }

    public List<FourDetails> getMarketFour(FourDetailsParam fourDetailsQuery) {
        return selectList("get_market_four", fourDetailsQuery);
    }

    public List<FourDetails> getPerFourProject(FourDetailsParam fourDetailsQuery) {
        return selectList("get_per_four_project", fourDetailsQuery);
    }

    public FourDetails getMarketName(FourDetailsParam query) {
        return selectOne("getMarketName", query);
    }

    public List<FourDetails> getTaizhangNumb(FourDetailsParam fourDetailsQuery) {
        return selectList("getTaizhangNumb", fourDetailsQuery);
}

    public List<FourDetails> getTaizhangList(FourDetailsParam fourDetailsQuery) {
        return selectList("getTaizhangList", fourDetailsQuery);
    }

    public List<FourDetails> getDetails(FourListParam query) {
        return selectList("xlkai_getFourList", query);
    }

    public Integer getCount(FourListParam query) {
        return selectOne("xlkai_getFourListCount", query);
    }

    public Integer updateDetails(FourDetails details) {
        return execute("xlkai_updateFourDetails", details);
    }

    public Integer getTaizhangCount(FourDetailsParam fourDetailsQuery) {
        return selectOne("getTaizhangCount", fourDetailsQuery);
    }

    public List<DetailInfo> groupDetailByListIn(FourDetailsParam fourDetailsQuery) {
        return selectList("groupDetailByListIn", fourDetailsQuery);
    }

}

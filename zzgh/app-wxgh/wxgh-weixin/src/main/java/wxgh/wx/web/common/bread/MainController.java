package wxgh.wx.web.common.bread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.bread.Bread;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.wx.web.common.disease.*;

import java.util.Date;

@Controller
public class MainController {

    @RequestMapping
    public void index(Model model){
        SeUser user = UserSession.getUser();
        String sql = "select * from t_bread where userid = :userid limit 1";
        Bread bread = pubDao.query(sql,user,Bread.class);
        model.put("b",bread);
    }

    @RequestMapping
    public ActionResult add(Bread bread) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录哦");
        }

        if (StrUtils.empty(bread.getCate()) || "0".equals(bread.getCate())) {
            return ActionResult.error("商家不能为空哦");
        }

        if (bread.getMobile().length() != 11) {
            return ActionResult.error("请填写11个数字哦~");
        }



        bread.setAddTime(new Date());
        bread.setUserid(user.getUserid());
        String sql = "select count(*) from t_bread where userid = ?";
        Integer count = pubDao.queryInt(sql,user.getUserid());
        if(count ==  0){
            String sql1 = "insert into t_bread (userid, name, add_time, mobile, year, month, cate)\n" +
                    "values(:userid, :name, :addTime, :mobile, :year,:month,:cate)";
            pubDao.executeBean(sql1, bread);
        }else {
            String sql2 = "update t_bread set add_time=:addTime,mobile=:mobile,year=:year,month=:month,cate=:cate where userid=:userid";
            pubDao.executeBean(sql2,bread);
        }

        return ActionResult.ok();
    }

    @Autowired
    private PubDao pubDao;
}

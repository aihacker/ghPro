package wxgh.wx.web.common.bodycheck;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.bodycheck.Bodycheck;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PubDao pubDao;
    private SimpleDateFormat sdf;

    @RequestMapping
    public void index(Model model){
        SeUser user = UserSession.getUser();
        Integer deptid = user.getDeptid();
        String sql = "SELECT d.name FROM t_dept d where d.id = ?";
        String deptname = pubDao.query(String.class,sql,deptid);
        String sql2 = "SELECT d.parentid FROM t_dept d where d.id = ?";
        Integer parentid = pubDao.queryInt(sql2,deptid);
        model.put("deptname",deptname);
        model.put("parentid",parentid);
        if (user == null) {
            model.put("msg", "你还没有登录哦");
            return;
        }
    }

    @RequestMapping
    public void apply(Model model){
        SeUser user = UserSession.getUser();
        Integer deptid = user.getDeptid();
        String sql = "SELECT d.name FROM t_dept d where d.id = ?";
        String deptname = pubDao.query(String.class,sql,deptid);
        model.put("deptname",deptname);
        if (user == null) {
            model.put("msg", "你还没有登录哦");
            return;
        }
    }

        @RequestMapping
        public ActionResult add(Bodycheck bodycheck){
            SeUser user = UserSession.getUser();
            if (user == null) {
                return ActionResult.error("你还没有登录哦");
            }


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createFormatTime = new Date();
            try{
                createFormatTime = format.parse("2018-07-22 00:00:00");
            }catch (Exception exception) {
                exception.printStackTrace();
            }

            String[ ]  arr2 = {"CkSZ13318226662","EqlP13380220068","tMZC13392268828",
                    "rIja13380226288","CkxZ18022213913","wxgh13318228700","vBXv13392268362"};
            List l = TypeUtils.arrayToList(arr2);
            if(l.contains(user.getUserid())){
                Date datenow = new Date();
                if(datenow.getTime()>createFormatTime.getTime()){
                    return ActionResult.error("已经超过预约时间，预约不成功");
                }
            }else{
                return ActionResult.error("已经超过预约时间，预约不成功");
            }
//            Date datenow = new Date();
//            if(datenow.getTime()>createFormatTime.getTime()){
//                return ActionResult.error("已经超过预约时间，预约不成功");
//            }




            String sql2 = "select * from t_bodycheck where userid = ?";
            Bodycheck ex = pubDao.query(Bodycheck.class,sql2,user.getUserid());

            if(ex != null){
                String sql = "delete from t_bodycheck where userid = ?";
                pubDao.execute(sql,ex.getUserid());
            }

            bodycheck.setAddTime(new Date());
            bodycheck.setUserid(user.getUserid());

            SQL sql = new SQL.SqlBuilder()
                    .field("plus,encore,hospital,fuli,idcard,age,name,add_time,dept,userid,gender,mobile,marriage,quote,category,extra")
                    .value(":plus,:encore,:hospital,:fuli,:idcard,:age,:name,:addTime,:dept,:userid,:gender,:mobile,:marriage,:quote,:category,:extra")
                    .insert("bodycheck")
                    .build();

            int i = pubDao.insertAndGetKey(sql.sql(), bodycheck);

            return ActionResult.ok("预约成功");
        }

        @RequestMapping
        public void show(Model model){
            SeUser user = UserSession.getUser();

            String sql = "select * from t_bodycheck where userid = ?";
            Bodycheck bodycheck = pubDao.query(Bodycheck.class,sql,user.getUserid());
            if(bodycheck == null){
                model.put("has",0);
            }else{
                model.put("has",1);
                model.put("info",bodycheck);
            }

        }
}

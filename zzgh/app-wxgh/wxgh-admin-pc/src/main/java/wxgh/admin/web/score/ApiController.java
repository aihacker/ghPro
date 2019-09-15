package wxgh.admin.web.score;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.sys.excel.score.ScoreWriteApi;
import wxgh.entity.pub.score.ScoreExcel;
import wxgh.entity.score.IntegralGoods;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.param.score.GiveParam;
import wxgh.param.score.Result;
import wxgh.query.score.QueryIntegralGoods;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-24
 **/
@Controller
public class ApiController {
    @RequestMapping
    public ActionResult give(String json){
        if(!StrUtils.empty(json)){
            GiveParam param = JsonUtils.parseBean(json,GiveParam.class);

            param.setScoreType(1);
            List<Result> list = param.getResults();
            if(TypeUtils.empty(list)){
                return ActionResult.error("请选择用户或部门噢");
            }

//            if(param.getScoreType().equals(0)){
//                return ActionResult.error("请选择赠送积分类型");
//            }


            Date date = new Date();
            int dateId = DateFuncs.toIntDate(date);

            for (Result r : param.getResults()) {
                if (r.getType() == 1) { //用户
                    SimpleScore score = new SimpleScore();
                    score.setInfo(param.getInfo());
                    score.setUserid(r.getId());
                    score.setScore(param.getScore());
                    if(param.getScoreType().equals(1)){
                        scoreService.user(score, ScoreType.GIVE);
                    }else if(param.getScoreType().equals(2)){
                        scoreService.tribe(score, ScoreType.GIVE);
                    }else if(param.getScoreType().equals(3)){
                        scoreService.di(score, ScoreType.GIVE);
                    }
                }else if (r.getType() == 2){ // 部门
                    String sql = "select userid from t_user";
//                    String sql = "select userid from t_user where find_in_set(deptid, query_dept_child(?))";
                    List<String> userids = pubDao.queryList(String.class,sql);
                    for(String u:userids){
                        SimpleScore score = new SimpleScore();
                        score.setInfo(param.getInfo());
                        score.setUserid(u);
                        score.setScore(param.getScore());
                        if(param.getScoreType().equals(1)){
                            scoreService.user(score, ScoreType.GIVE);
                        }else if(param.getScoreType().equals(2)){
                            scoreService.tribe(score, ScoreType.GIVE);
                        }else if(param.getScoreType().equals(3)){
                            scoreService.di(score, ScoreType.GIVE);
                        }
                    }
                }
            }

        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult goodslist(QueryIntegralGoods query) {
        query.setPageIs(true);
        query.setRowsPerPage(6);

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.*")
                .table("integral_goods g")
                .sys_file("g.img");
        if(query.getId()!=null){
            sql.where("g.id=:id");
        }
        if(query.getType()!=-1){
            sql.where("g.type=:type");
        }

//        if (query.getPageIs()) {
//            Integer count = pubDao.queryParamInt(sql.count().build().sql(), query);
//            query.setTotalCount(count);
//
//            sql.limit(":pagestart, :rowsPerPage");
//        }

        List<IntegralGoods> integralGoods = pubDao.queryList(sql.select().build().sql(),query,IntegralGoods.class);
        for(IntegralGoods i:integralGoods){
            i.setImg(PathUtils.getImagePath(i.getPath()));
        }

        RefData refData = new RefData();
        refData.put("datas", integralGoods);
        refData.put("page", new PageBen(query));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult goodsadd(IntegralGoods integralGoods) {
        integralGoods.setAddTime(new Date());
        SQL.SqlBuilder sqlBuilder = new SQL.SqlBuilder()
                .insert()
                .table("integral_goods")
                .field("name,img,points,info,addTime,type")
                .value(":name,:img,:points,:info,:addTime,:type");
        pubDao.insertAndGetKey(sqlBuilder.build().sql(),integralGoods);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult goodsdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("integral_goods",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult goodschange(IntegralGoods integralGoods) {
        SQL.SqlBuilder sqlBuilder = new SQL.SqlBuilder()
                .update()
                .table("integral_goods");
        if (integralGoods.getName() != null) {
            sqlBuilder.set("name = :name");
        }
        if (integralGoods.getImg() != null) {
            sqlBuilder.set("img= :img");
        }if (integralGoods.getPoints() != null) {
            sqlBuilder.set("points = :points");
        }if (integralGoods.getInfo() != null) {
            sqlBuilder.set("info = :info");
        }if (integralGoods.getType() != -1) {
            sqlBuilder.set("type = :type");
        }
        if(integralGoods.getId()!=null){
            sqlBuilder.where("id = :id");
        }
        pubDao.executeBean(sqlBuilder.build().sql(),integralGoods);
        return ActionResult.ok();
    }

    @RequestMapping
    public void downexcel(HttpServletResponse response){

        System.out.println("");
        System.out.println("进入下载方法");
        List<ScoreExcel> list = scoreService.downexcel();

        if(list.size()>0){
            ScoreWriteApi scoreWriteApi = new ScoreWriteApi();
            scoreWriteApi.toExcel(list);
            scoreWriteApi.response(response);
        }

    }

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private PubDao pubDao;
}

package wxgh.admin.web.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.sys.excel.bodycheck.BodycheckWriteApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.wxadmin.suggest.SuggestList;
import wxgh.entity.common.bodycheck.Bodycheck;
import wxgh.entity.common.law.Law;
import wxgh.entity.common.publicity.Publicity;
import wxgh.entity.common.suggest.SuggestCate;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.vote.VoteList;
import wxgh.param.common.publicity.PublicityQuery;
import wxgh.param.common.suggest.SuggestCateParam;
import wxgh.param.union.law.LawParam;
import wxgh.param.union.suggest.SuggestParam;
import wxgh.sys.service.admin.union.SuggestService;
import wxgh.param.union.vote.VoteParam;
import wxgh.sys.service.admin.union.LawService;
import wxgh.sys.service.admin.union.VoteService;
import wxgh.sys.service.weixin.common.publicity.PublicityService;
import wxgh.sys.service.weixin.common.suggest.SuggestCateService;
import wxgh.sys.service.weixin.pub.SysFileService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-15
 **/
@Controller
public class ApiController {

    @RequestMapping
    public void downexcel(HttpServletResponse response){

        System.out.println("");
        System.out.println("进入下载方法");
        String sql = "SELECT a.*,d.`parentid` FROM (SELECT b.*,u.deptid FROM t_bodycheck b LEFT JOIN t_user u ON b.`userid` = u.`userid`) a LEFT JOIN \n" +
                "t_dept d\n" +
                "ON d.id = a.deptid where d.parentid = 3" ;
        List<Bodycheck> list = pubDao.queryList(Bodycheck.class,sql,null);

        if(list.size()>0){
            BodycheckWriteApi api = new BodycheckWriteApi();
            api.toExcel(list);
            api.response(response);
        }

    }

    @RequestMapping
    public ActionResult push(String id) {
        if (StrUtils.empty(id)) {
            return ActionResult.error("推送失败！");
        }
        if (id.split(",").length > 8) {
            return ActionResult.error("每次推送不能超过8条公告哦");
        }

        weixinPush.publicity(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public void downexcelsecond(HttpServletResponse response){

        System.out.println("");
        System.out.println("进入下载方法");
        String sql = "SELECT a.*,d.`parentid` FROM (SELECT b.*,u.deptid FROM t_bodycheck b LEFT JOIN t_user u ON b.`userid` = u.`userid`) a LEFT JOIN \n" +
                "t_dept d\n" +
                "ON d.id = a.deptid where d.parentid = 4" ;
        List<Bodycheck> list = pubDao.queryList(Bodycheck.class,sql,null);

        if(list.size()>0){
            BodycheckWriteApi api = new BodycheckWriteApi();
            api.toExcel(list);
            api.response(response);
        }

    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status) {
        suggestService.apply(id, status);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult suggestdel(String id){
        String[] ids = id.split(",");
        for(String suid:ids){
            pubDao.execute(SQL.deleteByIds("user_suggest",suid));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult suggestlist(SuggestParam param){
        if (param.getStatus() == null) param.setStatus(0);
        param.setPageIs(true);
        param.setRowsPerPage(7);

        List<SuggestList> list = suggestService.listSuggest(param);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        System.out.print(refData);
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult suggestcatelist(SuggestCateParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(8);

        List<SuggestCate> cates = suggestCateService.getCates(param);

        RefData refData = new RefData();
        refData.put("datas", cates);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult suggestcatedel(String id) {
        if (StrUtils.empty(id)) {
            return ActionResult.error("请选择需要删除的提案类别");
        }
        suggestCateService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult suggestcateadd(SuggestCate cate) {
        suggestCateService.insertOrUpdate(cate);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult suggestcatedit(SuggestCate cate) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update t_suggest_cate set");
        if (cate.getName() != null && cate.getId() != null) {
            sqlBuilder.append(" name=:name,");
        }

        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
        sql += " where id = :id";
        RefData refData = new RefData();
        refData.put("id", pubDao.executeBean(sql, cate));
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult votelist(VoteParam param) {
        if (param.getStatus() == null) param.setStatus(0);
        param.setPageIs(true);
        param.setRowsPerPage(8);
        List<VoteList> list = voteService.voteList(param);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult votedel(String id) {
        String[] ids = id.split(",");
        for(String voteid:ids){
            pubDao.execute(SQL.deleteByIds("voted",voteid));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult voteapply(Integer id, Integer status) {
        voteService.apply(id, status);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult lawlist(LawParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(7);
        List<Law> list = lawService.lawList(param);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult lawdel(String id) {
        String[] ids = id.split(",");
        for(String law:ids){
            pubDao.execute(SQL.deleteByIds("law",law));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    @Transactional
    public ActionResult addlaw(Law law) {
        law.setTime(new Date());
        String sql = "insert into t_law(name, content, time, url, sort_id) values(:name, :content, :time, :url, :sortId)";
        RefData refData = new RefData();
        refData.put("id", pubDao.insertAndGetKey(sql,law));
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult update(Law law) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update t_law set");
        if (law.getName() != null) {
            sqlBuilder.append(" name=:name,");
        }
        if (law.getContent() != null) {
            sqlBuilder.append(" content = :content,");
        }
        if (law.getUrl() != null) {
            sqlBuilder.append(" url = :url,");
        }
        if (law.getSortId() != null) {
            sqlBuilder.append(" sort_id = :sortId,");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
        sql += " where id = :id";
        RefData refData = new RefData();
        refData.put("id", pubDao.executeBean(sql, law));
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult publicitylist(PublicityQuery query) {
        Integer count = publicityService.getNoticeCount(query);
        query.setTotalCount(count);
        query.setPageIs(true);
        query.setRowsPerPage(7);

        List<Publicity> list = publicityService.getNotices(query);

        for(Publicity pub:list){
            SysFile sysFile = sysFileService.getFilePath(pub.getPicture());
            if(sysFile!=null){
                pub.setPicture(PathUtils.getImagePath(sysFile.getFilePath()));
            }else{
                pub.setPicture(PathUtils.getImagePath(pub.getPicture()));
            }
        }

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(query));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult publicitydel(String id) {
        if (StrUtils.empty(id)) {
            return ActionResult.error("请选择需要删除的公告");
        }
        publicityService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult publicityapply(Publicity publicity){
            if (publicity.getId() != null) {
                publicityService.updateNotice(publicity);
                return ActionResult.ok();
            } else {
                publicity.setTime(new Date());
                publicityService.saveNotice(publicity);
                RefData refData = new RefData();
                refData.put("id",publicity.getId());
                return ActionResult.ok(null,refData);
            }
    }



    @Autowired
    private SuggestService suggestService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private LawService lawService;

    @Autowired
    private PublicityService publicityService;

    @Autowired
    private SuggestCateService suggestCateService;

    @Autowired
    private SysFileService sysFileService;

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;
}

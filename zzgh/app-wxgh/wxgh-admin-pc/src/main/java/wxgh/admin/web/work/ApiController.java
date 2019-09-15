package wxgh.admin.web.work;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import wxgh.app.sys.api.FileApi;
import wxgh.data.union.innovation.honor.HonorData;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.common.law.Law;
import wxgh.entity.notice.Notice;
import wxgh.entity.pub.SysFile;
import wxgh.entity.pub.User;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.entity.work.Carousel;
import wxgh.param.admin.pub.ChatGroupParam;
import wxgh.param.pub.file.FileParam;
import wxgh.param.union.innovation.QueryInnovateAdvice;
import wxgh.param.union.innovation.honor.HonorParam;
import wxgh.param.union.innovation.work.WorkResultParam;
import wxgh.param.union.law.LawParam;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;
import wxgh.sys.service.weixin.union.innovation.WorkResultService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult grouplist(ChatGroupParam param) {
        param.setType(ChatGroup.TYPE_TEAM);
        param.setPageIs(true);
        param.setRowsPerPage(5);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group c").sys_file("c.avatar").field("c.*")
                .order("c.add_time", Order.Type.DESC);

        if(param.getType()!=null){
            sql.where("c.type = :type");
        }
        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<ChatGroup> list =  pubDao.queryList(sql.select().build().sql(), param, ChatGroup.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult groupdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("chat_group",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult groupget(Integer id){
        String sql = new SQL.SqlBuilder().table("chat_group s").join("chat_user r","s.group_id=r.group_id")
                .join("user u", "r.userid = u.userid")
                .field("u.`name`").where("s.id = ? and r.status = 1").build().sql();
        List<User> user = pubDao.queryList(User.class,sql,id);

        RefData refData = new RefData();
        refData.put("datas", user);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult inovatelist(QueryInnovateAdvice param) {
        if(param.getStatus() == null){
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);

        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("innovate_advice iad")
                .join("innovate_apply iap","iap.id = iad.pid")
                .join("user u","u.userid = iap.userid")
                .field("iad.*, iap.`add_time` AS applyTime, iap.`audit_idea` AS auditIdea, iap.`audit_time` AS auditTime, iap.`status` AS status, u.`avatar` AS avatar, u.`name` AS username")
                .where("iap.status = :status");

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<InnovateAdvice> list = pubDao.queryList(sql.select().build().sql(),param,InnovateAdvice.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult inovatedel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("innovate_advice",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult inovatechange(Integer id, Integer status){

        String sql = "update t_innovate_apply a left join t_innovate_advice b on a.id=b.pid set a.status = ? where b.id=?";
        pubDao.execute(sql, status, id);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult inovateget(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("innovate_advice iad")
                .join("innovate_apply iap","iap.id = iad.pid")
                .join("user u","u.userid = iap.userid")
                .field("iad.*, iap.`add_time` AS applyTime, iap.`audit_idea` AS auditIdea, iap.`audit_time` AS auditTime, iap.`status` AS status, u.`avatar` AS avatar, u.`name` AS username")
                .where("iad.id = ?");

        InnovateAdvice innovateAdvice = pubDao.query(InnovateAdvice.class,sql.select().build().sql(),id);

        RefData refData = new RefData();
        refData.put("datas", innovateAdvice);
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult honorlist(HonorParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(9);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("work_honor").order("apply_time", Order.Type.DESC);

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<HonorData> list =  pubDao.queryList(sql.select().build().sql(), param, HonorData.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult honordel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("work_honor",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult honoraddone(HonorData data){
        data.setApplyTime(new Date());
        data.setStatus(1);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work_honor")
                .field("name,award_grade,apply_time,remark,people,status")
                .value(":name,:awardGrade,:applyTime,:remark,:people,:status")
                .insert();
        pubDao.executeBean(sql.build().sql(),data);
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult addpic(Integer type, Integer sortId, Integer display, String briefInfo, @RequestParam("img") MultipartFile multipartFile){
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            File toFile = null;
            SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());

            Carousel param = new Carousel();
            param.setImg(sysFile.getFileId());
            param.setBriefInfo(briefInfo);
            param.setDisplay(display);
            param.setAddTime(new Date());
            param.setSortId(sortId);
            param.setType(type);



            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .table("work_carousel")
                    .field("display,add_time,brief_info,img,sort_id,type")
                    .value(":display,:addTime,:briefInfo,:img,:sortId,:type")
                    .insert();

            pubDao.insertAndGetKey(sql.build().sql(),param);

        } else {
            return ActionResult.fail("请上传封面图片");
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult piclist(Carousel param){
        param.setPageIs(true);
        param.setRowsPerPage(7);
        if(param.getType()==null){
            param.setType(1);
        }

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work_carousel c")
                .field("c.*,s.file_path as img")
                .join("t_sys_file s","s.file_id=c.img")
                .order("c.sort_id");

        if(param.getId()!=null){
            sql.where("c.id = :id");
        }

        if(param.getType()!=null){
            sql.where("c.type = :type");
        }


        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<Carousel> list = pubDao.queryList(sql.select().build().sql(),param,Carousel.class);
        for(Carousel c:list){
            c.setPath(PathUtils.getImagePath(c.getPath()));
        }

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult picdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("work_carousel",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult updatepic(Integer type, Integer id, Integer sortId, Integer display, String briefInfo, @RequestParam("img") MultipartFile multipartFile){
        SysFile sysFile = new SysFile();
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            File toFile = null;
            sysFile = fileApi.addFile(multipartFile, new FileParam());
        }
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work_carousel")
                .update();

        Carousel param = new Carousel();
        param.setImg(sysFile.getFileId());
        param.setBriefInfo(briefInfo);
        param.setDisplay(display);
        param.setSortId(sortId);
        param.setId(id);
        param.setType(type);

        if(param.getDisplay() != null){
            sql.set("display = :display");
        }
        if(param.getType() != null){
            sql.set("type = :type");
        }
        if(param.getImg() != null){
            sql.set("img = :img");
        }
        if(param.getBriefInfo() != null){
            sql.set("brief_info = :BriefInfo");
        }
        if(param.getSortId()!=null){
            sql.set("sort_id = :sortId");
        }
        if(param.getId()!=null){
            sql.where("id=:id");
        }

        pubDao.executeBean(sql.build().sql(),param);

        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult updatewithoutpic(Integer id, Integer sortId, Integer display, String briefInfo, Integer type){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("work_carousel")
                .update();

        Carousel param = new Carousel();
        param.setBriefInfo(briefInfo);
        param.setDisplay(display);
        param.setSortId(sortId);
        param.setId(id);
        param.setType(type);

        if(param.getDisplay() != null){
            sql.set("display = :display");
        }
        if(param.getType() != null){
            sql.set("type = :type");
        }
        if(param.getImg() != null){
            sql.set("img = :img");
        }
        if(param.getBriefInfo() != null){
            sql.set("brief_info = :BriefInfo");
        }
        if(param.getSortId()!=null){
            sql.set("sort_id = :sortId");
        }
        if(param.getId()!=null){
            sql.where("id=:id");
        }

        pubDao.executeBean(sql.build().sql(),param);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult worklist(WorkResultParam query){
        if(query.getStatus() == null){
            query.setStatus(0);
        }
        query.setWorkType(2);
        List<WorkResult> workResultList = workResultService.listResult(query);

        RefData refData = new RefData();
        refData.put("datas", workResultList);
        refData.put("page", new PageBen(query));

        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult workdel(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("work_result",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult workchange(Integer id, Integer status){

        workResultService.changeWork(id,status);
        return ActionResult.ok();
    }

    @Transactional
    @RequestMapping
    public ActionResult add(Notice notice) {
        notice.setAddTime(new Date());
        String sql = "insert into t_notice(title, content, add_time,type) values(:title, :content, :addTime, :type)";
        RefData refData = new RefData();
        refData.put("id", pubDao.insertAndGetKey(sql,notice));
        return ActionResult.ok(null,refData);
    }

    @RequestMapping
    public ActionResult lawlist(Notice notice) {
        notice.setPageIs(true);
        notice.setRowsPerPage(7);
        String sql = "select %s from t_notice";
        String selSql = String.format(sql, "*");
        if (notice.getPageIs()) {
            String countSql = String.format(sql, "count(*)");
            Integer count = pubDao.queryParamInt(countSql, notice);
            notice.setTotalCount(count);

            selSql += " limit :pagestart, :rowsPerPage";
        }
        List<Notice> list =  pubDao.queryList(selSql, notice, Notice.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(notice));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult lawdel(String id) {
        String[] ids = id.split(",");
        for(String law:ids){
            pubDao.execute(SQL.deleteByIds("notice",law));
        }
        return ActionResult.ok();
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private InnovateAdviceService innovateAdviceService;

    @Autowired
    private WorkResultService workResultService;

    @Autowired
    private FileApi fileApi;

}

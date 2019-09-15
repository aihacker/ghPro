package wxgh.admin.web.female;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.common.female.FemaleLesson;
import wxgh.entity.common.female.FemaleMama;
import wxgh.entity.pub.SysFile;
import wxgh.param.common.female.FemaleLessonAdminQuery;
import wxgh.param.common.female.FemaleMamaAdminQuery;
import wxgh.sys.service.weixin.common.female.FemaleLessonAdminService;
import wxgh.sys.service.weixin.common.female.FemaleMamaAdminService;
import wxgh.sys.service.weixin.pub.SysFileService;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-17
 **/
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult lessonlist(FemaleLessonAdminQuery query) throws UnsupportedEncodingException {
        Integer count = femaleLessonAdminService.countRecords(query);

        query.setTotalCount(count);
        query.setPageIs(true);
        query.setRowsPerPage(10);

        List<FemaleLesson> femaleLessonList = femaleLessonAdminService.getRecords(query);

        for (FemaleLesson femaleLesson: femaleLessonList){
            femaleLesson.setName(URLDecoder.decode(femaleLesson.getName(), "UTF-8"));
            femaleLesson.setContent(URLDecoder.decode(femaleLesson.getContent(), "UTF-8"));
            SysFile sysFile = sysFileService.getFilePath(femaleLesson.getCover());
            if(sysFile!=null){
                femaleLesson.setCover(PathUtils.getImagePath(sysFile.getFilePath()));
            }else{
                femaleLesson.setCover(PathUtils.getImagePath(femaleLesson.getCover()));
            }
        }

        return ActionResult.okAdmin(femaleLessonList, query);
    }

    @RequestMapping
    public ActionResult lessondel(String id){
        String[] ids = id.split(",");
        for(String delid:ids){
            pubDao.execute(SQL.deleteByIds("female_lesson",delid));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult lessonget(Integer id) throws UnsupportedEncodingException {
        FemaleLessonAdminQuery query = new FemaleLessonAdminQuery();
        query.setId(id);
        FemaleLesson femaleLesson = femaleLessonAdminService.getRecords(query).get(0);
        femaleLesson.setName(URLDecoder.decode(femaleLesson.getName(), "UTF-8"));
        femaleLesson.setContent(URLDecoder.decode(femaleLesson.getContent(), "UTF-8"));

        femaleLesson.setCover(PathUtils.getImagePath(femaleLesson.getCover()));
        return ActionResult.ok(null, femaleLesson);
    }

    @RequestMapping
    public ActionResult lessonapply(FemaleLesson femaleLesson){
        femaleLesson.setCover(femaleLesson.getCover().toString());
        femaleLessonAdminService.updateRecord(femaleLesson);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult mamalist(FemaleMamaAdminQuery query) throws UnsupportedEncodingException {
        Integer count = femaleMamaAdminService.countRecords(query);

        query.setTotalCount(count);
        query.setPageIs(true);
        query.setRowsPerPage(10);

        List<FemaleMama> femaleMamas = femaleMamaAdminService.getRecords(query);

        for (FemaleMama femaleMama:femaleMamas){
            femaleMama.setName(URLDecoder.decode(femaleMama.getName(), "UTF-8"));
            femaleMama.setContent(URLDecoder.decode(femaleMama.getContent(), "UTF-8"));
            SysFile sysFile = sysFileService.getFilePath(femaleMama.getCover());
            if(sysFile!=null){
                femaleMama.setCover(PathUtils.getImagePath(sysFile.getFilePath()));
            }else{
                femaleMama.setCover(PathUtils.getImagePath(femaleMama.getCover()));
            }
        }

        return ActionResult.okAdmin(femaleMamas, query);
    }

    @RequestMapping
    public ActionResult mamadel(String id){
        String[] ids = id.split(",");
        for(String delid:ids){
            pubDao.execute(SQL.deleteByIds("female_mama",delid));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult mamaget(Integer id) throws UnsupportedEncodingException {
        FemaleMamaAdminQuery query = new FemaleMamaAdminQuery();
        query.setId(id);
        FemaleMama femaleMama = femaleMamaAdminService.getRecords(query).get(0);
        femaleMama.setName(URLDecoder.decode(femaleMama.getName(), "UTF-8"));
        femaleMama.setContent(URLDecoder.decode(femaleMama.getContent(), "UTF-8"));

        femaleMama.setCover(PathUtils.getImagePath(femaleMama.getCover()));
        return ActionResult.ok(null, femaleMama);

    }

    @RequestMapping
    public ActionResult mamaapply(FemaleMama femaleMama){
        femaleMama.setCover(femaleMama.getCover().toString());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update t_female_mama set");
        if (femaleMama.getName() != null) {
            sqlBuilder.append(" name=:name,");
        }
        if (femaleMama.getContent() != null) {
            sqlBuilder.append(" content = :content,");
        }
        if (femaleMama.getCover() != null) {
            sqlBuilder.append(" cover = :cover,");
        }
        String sql = sqlBuilder.substring(0, sqlBuilder.length() - 1);
        sql += " where id = :id";
        pubDao.executeBean(sql,femaleMama);
        return ActionResult.ok();
    }

    @Autowired
    private FemaleLessonAdminService femaleLessonAdminService;

    @Autowired
    private FemaleMamaAdminService femaleMamaAdminService;

    @Autowired
    private PubDao pubDao;

    @Autowired
    private SysFileService sysFileService;

}

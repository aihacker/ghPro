package wxgh.wx.web.party.beauty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.TypeUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.beauty.Work;
import wxgh.entity.party.beauty.WorkComment;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.union.innovation.WorkZan;
import wxgh.sys.service.weixin.party.beauty.WorkService;
import wxgh.sys.service.weixin.union.innovation.WorkCommentService;
import wxgh.sys.service.weixin.union.innovation.WorkZanService;

import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 09:47
 *----------------------------------------------------------
 */
@Controller
public class ShowController {

    @Autowired
    private WorkService workService;

    @Autowired
    private WorkZanService workZanService;

    @Autowired
    private WorkCommentService workCommentService;

    @RequestMapping
    public void index(Model model, Integer id, WorkZan workZan) {

        Integer countCom = workCommentService.getCount(id);
        Integer countZan = workZanService.getCount(id);

        Work work = workService.getOne(id);
        List<WorkFile> files = work.getWorkFiles();
        if (!TypeUtils.empty(files)) {
            for (int i = 0; i < files.size(); i++) {
                WorkFile f = files.get(i);
                files.get(i).setThumb(PathUtils.getImagePath(f.getThumb()));
                files.get(i).setPath(PathUtils.getImagePath(f.getPath()));
            }
        }

        // 2017 - 8 - 15改为ajax请求最新三条评论记录
//        List<WorkComment> comments = workCommentService.getList(id);

        SeUser user = UserSession.getUser();
        workZan.setUserId(user.getUserid());
        workZan.setWorkId(id);
        Integer userZan = workZanService.getOne(workZan);

        model.put("countZan", countZan);
        model.put("countCom", countCom);
        model.put("userZan", userZan);
        model.put("work", work);
//        model.put("comments", comments);

    }

    /**
     * 获取最新三条评论记录
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult list3(Integer id){
        return ActionResult.ok(null, workCommentService.getListLimit3(id));
    }

    @RequestMapping
    public ActionResult commentList(Integer id) {
        ActionResult result = ActionResult.ok();
        List<WorkComment> comments = workCommentService.getList(id);
        result.setData(comments);
        return result;
    }

    @RequestMapping
    public ActionResult addComment(WorkComment workComment) {
        SeUser user = UserSession.getUser();
        workComment.setAddTime(new Date());
        workComment.setUserId(user.getUserid());
        workCommentService.saveWorkComment(workComment);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult addZan(WorkZan workZan) {
        String userid = UserSession.getUserid();
        if(workZanService.checkZan(userid, workZan.getId()))
            return ActionResult.ok();
        synchronized (this){
            workZan.setAddTime(new Date());
            workZan.setUserId(userid);
            workZanService.saveWorkZan(workZan);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult delZan(WorkZan workZan) {
        SeUser user = UserSession.getUser();
        workZan.setUserId(user.getUserid());
        workZanService.delZan(workZan);
        return ActionResult.ok();
    }


}


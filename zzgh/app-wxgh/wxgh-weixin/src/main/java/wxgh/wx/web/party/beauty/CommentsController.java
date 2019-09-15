package wxgh.wx.web.party.beauty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.beauty.Work;
import wxgh.entity.party.beauty.WorkComment;
import wxgh.sys.service.weixin.party.beauty.WorkService;
import wxgh.sys.service.weixin.union.innovation.WorkCommentService;

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
 * @Date 2017-08-07 10:39
 *----------------------------------------------------------
 */
@Controller
public class CommentsController {

    @Autowired
    private WorkCommentService workCommentService;

    @Autowired
    private WorkService workService;

    @RequestMapping
    public void index(Model model, Integer id) {

        List<WorkComment> comments = workCommentService.getList(id);
        Work work = workService.getOne(id);

        model.put("comments", comments);
        model.put("work", work);

    }

    @RequestMapping
    public ActionResult addComment(WorkComment workComment){
        SeUser user = UserSession.getUser();
        workComment.setAddTime(new Date());
        workComment.setUserId(user.getUserid());
        workCommentService.saveWorkComment(workComment);
        return ActionResult.ok();
    }
    
}


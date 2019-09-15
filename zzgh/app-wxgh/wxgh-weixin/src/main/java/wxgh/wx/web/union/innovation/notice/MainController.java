package wxgh.wx.web.union.innovation.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.user.UserData;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.entertain.act.ActRegular;
import wxgh.entity.notice.Notice;
import wxgh.param.union.innovation.notice.NoticeParam;
import wxgh.sys.service.weixin.entertain.act.ActService;
import wxgh.sys.service.weixin.notice.NoticeService;
import wxgh.sys.service.weixin.union.innovation.team.ChatGroupService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-04 15:29
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private ActService actService;

    @RequestMapping
    public void index(Model model) {
//        Notice notice = new Notice();
//        notice.setType(1);
//        SeUser user = UserSession.getUser();
//        List<Notice> notices = noticeService.getData(notice);
//        model.put("list", notices);
    }


    /**
     * 获取公告列表
     * @param notice
     * @return
     */
    @RequestMapping
    public ActionResult list(NoticeParam notice) {
        SeUser user = UserSession.getUser();
        Integer deptid = user.getDeptid();
        notice.setPageIs(true);
        return ActionResult.okRefresh(noticeService.getData(notice), notice);
    }

    /**
     * 获取公告详情
     * @param model
     * @param id
     * @param type
     */
    @RequestMapping
    public void detail(Model model, Integer id, Integer type){
        NoticeParam notice = new NoticeParam();
        notice.setId(id);
        Notice notice1 = noticeService.getData(notice).get(0);
        model.put("notice", notice1);
//        if (type == Notice.TYPE_Activity) {  // 活动
//            Act actInfo = actService.getBaseActInfo(notice1.getPid());
//            List<ActRegular> list = actService.getActRegular(actInfo.getActId());
//            model.put("act", actInfo);
//            model.put("regular", list);
//        } else {  // 招募
//            // 检查是否为团队成员
//            boolean check = chatGroupService.checkInGroup(notice1.getPid(), UserSession.getUserid());
//            List<UserData> groupMemberList = chatGroupService.getChatUsers(notice1.getPid());
//            model.put("member", groupMemberList);
//            model.put("check", check);
//        }
    }

    @RequestMapping
    public void detail2(Model model, Integer id, Integer type){
        NoticeParam notice = new NoticeParam();
        notice.setId(id);
        Notice notice1 = noticeService.getData(notice).get(0);
        model.put("notice", notice1);
//        if (type == Notice.TYPE_Activity) {  // 活动
//            Act actInfo = actService.getBaseActInfo(notice1.getPid());
//            List<ActRegular> list = actService.getActRegular(actInfo.getActId());
//            model.put("act", actInfo);
//            model.put("regular", list);
//        } else {  // 招募
//            // 检查是否为团队成员
//            boolean check = chatGroupService.checkInGroup(notice1.getPid(), UserSession.getUserid());
//            List<UserData> groupMemberList = chatGroupService.getChatUsers(notice1.getPid());
//            model.put("member", groupMemberList);
//            model.put("check", check);
//        }
    }


}


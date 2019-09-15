package wxgh.wx.web.party.di.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.party.di.notice.NoticeList;
import wxgh.param.party.di.notice.ListParam;
import wxgh.sys.service.weixin.party.di.NoticeService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(ListParam param) {
        param.setPageIs(true);
        List<NoticeList> notices = noticeService.listNotice(param);
        return ActionResult.okRefresh(notices, param);
    }

    @Autowired
    private NoticeService noticeService;

}

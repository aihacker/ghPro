package wxgh.wx.web.notice.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.party.news.NewsList;
import wxgh.param.union.suggest.ListParam;
import wxgh.sys.service.weixin.notice.NoticeService;

import java.util.List;


/**
 * Created by cby on 2017/8/28.
 */
@Controller
public class ApiController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping
    public ActionResult get_news(ListParam param) {
        param.setPageIs(true);
        List<NewsList> news = noticeService.get_news(param);
        return ActionResult.okRefresh(news, param);
    }
}

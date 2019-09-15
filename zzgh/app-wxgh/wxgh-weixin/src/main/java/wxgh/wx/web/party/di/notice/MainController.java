package wxgh.wx.web.party.di.notice;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.party.di.notice.NoticeInfo;
import wxgh.sys.service.weixin.party.di.NoticeService;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list() {

    }

    @RequestMapping
    public void show(Integer id, Model model) {
        NoticeInfo notice = noticeService.getNoticeInfo(id);
        if (notice != null) {
            notice.setTimeStr(DateUtils.formatDate(notice.getAddTime()));
        }
        model.put("n", notice);
    }

    @Autowired
    private NoticeService noticeService;

}

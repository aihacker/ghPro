package wxgh.wx.web.party.notice.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.DateUtils;
import wxgh.entity.notice.NoticeNews;
import wxgh.sys.service.weixin.notice.NoticeService;

/**
 * Created by cby on 2017/8/28.
 */
@Controller
public class MainController {

    @Autowired
    private NoticeService noticeService;


    @RequestMapping
    public void list(Model model,Integer type){
        model.put("type",type);
    }

    @RequestMapping
    public void show(Model model,Integer id){
        NoticeNews detail = noticeService.get_detail(id);
        if (detail!=null){
            String s = DateUtils.formatDate(detail.getAddTime());
            model.put("time",s);
        }
        model.put("detail",detail);
    }
}

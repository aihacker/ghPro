package wxgh.admin.web.di.notice;

import com.libs.common.crypt.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.party.di.exam.UserList;
import wxgh.data.party.di.notice.NoticeInfo;
import wxgh.param.party.di.notice.NoticeParam;
import wxgh.sys.service.admin.di.notice.NoticeService;

import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping
    public ActionResult get(NoticeParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(5);
        List<NoticeInfo> list = noticeService.getNoticeList(param);

        return ActionResult.okAdmin(list, param);
    }

    @RequestMapping
    public ActionResult del_notice(String id) {
        noticeService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult push(String id, String userid, String group_id) {
        noticeService.push(id, userid, group_id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult search_user(String key) {
        ActionResult result = ActionResult.ok();
        List<UserList> userList = noticeService.searchUser(key);

        result.setData(userList);

        return result;
    }

    @RequestMapping
    public ActionResult update_notice(NoticeParam param){
        String content = URL.decode(param.getContent());//文章转码
        param.setContent(content);
        noticeService.update_notice(param);
        return ActionResult.ok();
    }
}

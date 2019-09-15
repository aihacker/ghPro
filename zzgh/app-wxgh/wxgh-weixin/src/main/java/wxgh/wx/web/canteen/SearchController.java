package wxgh.wx.web.canteen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.SearchList;
import wxgh.param.union.group.SearchParam;
import wxgh.sys.service.weixin.canteen.CanteenService;
import wxgh.sys.service.weixin.union.group.GroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Controller
public class SearchController {

    @RequestMapping
    public ActionResult list(SearchParam param) {
        if (param.getStatus() == null) param.setStatus(1);
        if (param.getUserid() == null) param.setUserid(UserSession.getUserid());
        param.setPageIs(true);

        List<SearchList> searchLists = canteenService.getSearchs(param);

        return ActionResult.okRefresh(searchLists, param);
    }

    @Autowired
    private CanteenService canteenService;

}

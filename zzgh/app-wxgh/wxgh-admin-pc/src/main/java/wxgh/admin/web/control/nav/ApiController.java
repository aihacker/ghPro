package wxgh.admin.web.control.nav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.pcadmin.nav.NavInfo;
import wxgh.data.pub.NameValue;
import wxgh.entity.admin.Nav;
import wxgh.entity.admin.NavCate;
import wxgh.param.pcadmin.NavCateParam;
import wxgh.sys.service.admin.control.NavService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult cate_list() {
        NavCateParam param = new NavCateParam();
        List<NavCate> cates = navService.getNavCates(param);
        return ActionResult.okAdmin(cates, null);
    }

    @RequestMapping
    public ActionResult del_cate(String id) {
        navService.delCates(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add_cate(NavCate navCate) {
        navService.addNavCate(navCate);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult nav_list(Integer cateId) {
        List<NavInfo> navs = navService.getNavInfo(cateId);
        return ActionResult.okAdmin(navs, null);
    }

    @RequestMapping
    public ActionResult one_nav_list(Integer cateId) {
        List<NameValue> navs = navService.getOneNavs(cateId);
        return ActionResult.okWithData(navs);
    }

    @RequestMapping
    public ActionResult del_nav(String id) {
        navService.delNav(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add_nav(Nav nav) {
        navService.addNav(nav);
        return ActionResult.ok();
    }

    @Autowired
    private NavService navService;

}

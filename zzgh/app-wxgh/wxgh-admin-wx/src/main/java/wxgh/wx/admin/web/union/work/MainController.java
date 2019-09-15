package wxgh.wx.admin.web.union.work;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.innovation.ShopList;
import wxgh.param.union.innovation.InnovateApplyQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateShopService;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {
    }

    @RequestMapping
    public ActionResult list(InnovateApplyQuery query) {
        SeUser user = UserSession.getUser();
//        query.setNodeptid(ScopeVerify.verify(user.getDeptid()));

        Integer count = innovateShopService.countAdminShops(query);

        query.setRowsPerPage(15);
        query.setPageIs(true);
        query.setTotalCount(count);

        List<ShopList> shopLists = innovateShopService.getAdminShops(query);

        RefData refData = new RefData();
        refData.put("shops", shopLists);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    @Autowired
    private InnovateShopService innovateShopService;

}

package wxgh.wx.web.party.beauty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.beauty.Work;
import wxgh.param.party.beauty.WorkQuery;
import wxgh.sys.service.weixin.party.beauty.WorkService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 魅美影像
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-04 09:23
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private WorkService workService;

    @RequestMapping
    public void index(Model model) {


    }

    @RequestMapping
    public ActionResult list(WorkQuery query) {
        query.setStatus(1);
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }
//        query.setDeptid(ScopeVerify.verify(user.getDeptid()));

        Integer count = workService.countWorks(query);

        query.setPageIs(true);
        query.setRowsPerPage(15);
        query.setTotalCount(count);

        List<Work> works = workService.listWorks(query);

        RefData refData = new RefData();
        refData.put("works", works);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }
    
}


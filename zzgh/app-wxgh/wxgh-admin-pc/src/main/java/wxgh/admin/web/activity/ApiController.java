package wxgh.admin.web.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.entity.Activities.Activities;
import wxgh.entity.common.ActApply;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.param.pcadmin.ActivitiesParam;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class ApiController {
    @RequestMapping
    public ActionResult applylist(ActivitiesParam param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);


        return ActionResult.ok();
    }

    @Autowired
    private PubDao pubDao;

}

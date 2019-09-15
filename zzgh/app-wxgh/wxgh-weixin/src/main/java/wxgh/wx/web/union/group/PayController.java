package wxgh.wx.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.pay.ListData;
import wxgh.param.union.group.pay.RecordListParam;
import wxgh.sys.service.weixin.union.group.pay.RecordService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@Controller
public class PayController {

    /**
     * 充值记录
     *
     * @param id
     */
    @RequestMapping
    public void record(Integer id) {
    }

    @RequestMapping
    public ActionResult list(RecordListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setUserid(UserSession.getUserid());

        List<ListData> records = recordService.listRecord(param);
        return ActionResult.okRefresh(records, param);
    }

    @Autowired
    private RecordService recordService;
}

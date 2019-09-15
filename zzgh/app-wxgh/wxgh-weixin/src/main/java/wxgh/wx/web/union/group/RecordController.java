package wxgh.wx.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.act.RecordList;
import wxgh.data.union.group.act.RecordTotal;
import wxgh.param.union.group.act.ListParam;
import wxgh.sys.service.weixin.union.group.act.RecordService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Controller
public class RecordController {

    /**
     * 积分会费记录
     *
     * @param id
     * @param model
     */
    @RequestMapping
    public void index(Integer id, Model model) {
        RecordTotal total = recordService.getTotal(id, UserSession.getUserid());
        model.put("total", total);
    }

    @RequestMapping
    public void money_record(Integer id, Model model) {
        RecordTotal total = recordService.getTotal(id, UserSession.getUserid());
        model.put("money", total.getMoney());
    }

    /**
     * 获取用户参与活动获得积分和花费会费记录
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list(ListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setUserid(UserSession.getUserid());

        List<RecordList> recordLists = recordService.listRecord(param);

        return ActionResult.okRefresh(recordLists, param);
    }

    @Autowired
    private RecordService recordService;

}

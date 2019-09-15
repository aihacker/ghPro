package wxgh.wx.web.party.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import wxgh.entity.tribe.TribeResult;
import wxgh.sys.service.weixin.tribe.TribeResultService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
@Controller
public class ResultController {

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public ActionResult resultlist() {
        List<TribeResult> tribeResultList = tribeResultService.getData();

        RefData refData = new RefData();
        refData.put("list", tribeResultList);
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public void info(Model model, Integer id) {
        model.put("info", tribeResultService.getInfo(id));
    }

    @Autowired
    private TribeResultService tribeResultService;

}

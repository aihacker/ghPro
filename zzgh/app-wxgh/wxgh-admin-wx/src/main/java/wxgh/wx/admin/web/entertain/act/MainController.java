package wxgh.wx.admin.web.entertain.act;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.wxadmin.act.ActInfo;
import wxgh.sys.service.wxadmin.act.ActService;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public void show(Model model, Integer id) {
        ActInfo actInfo = actService.getAct(id);
        if (actInfo != null) {
            actInfo.setTime(DateUtils.formatDate(actInfo.getStartTime(), actInfo.getEndTime()));
        }
        model.put("g", actInfo);
    }

    @Autowired
    private ActService actService;
}

package wxgh.wx.web.four;


import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.four.FourDetails;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.sys.service.weixin.four.FourDetailsService;
import wxgh.sys.service.weixin.four.FourPropagateService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.*;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class StatusController {

    @RequestMapping
    public void index(Model model) {
        SeUser user = UserSession.getUser();

        if (user != null) {
            PropagateParam query = new PropagateParam();
            query.setUserid(user.getUserid());

            //获取新增的物品
            query.setDeviceStatus(1);
            List<FourPropagate> propagates = fourPropagateService.getPropagates(query);
            if (TypeUtils.empty(propagates)) {
                propagates = new ArrayList<>();
            }

            //获取更换的物品
            query.setDeviceStatus(2);
            List<FourPropagate> genPropagates = fourPropagateService.getPropagates(query);
            if (TypeUtils.empty(genPropagates)) {
                genPropagates = new ArrayList<>();
            }

            propagates.addAll(genPropagates);
            Collections.sort(propagates, new Comparator<FourPropagate>() {
                @Override
                public int compare(FourPropagate o1, FourPropagate o2) {
                    if (o1.getApplyTime().equals(o2.getApplyTime())) {
                        return 0;
                    }
                    if (o1.getApplyTime().before(o2.getApplyTime())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });

            for (int i = 0; i < propagates.size(); i++) {
                Date date = propagates.get(i).getApplyTime();
                propagates.get(i).setSuggest(DateUtils.formatDate(date));
            }
            model.put("fs", propagates);
        }
    }

    @RequestMapping
    public void show(Model model, PropagateParam query) {
        FourPropagate fourPropagate = fourPropagateService.getPropagate(query);
        if (fourPropagate != null) {
            String name = userService.getUserName(fourPropagate.getUserid());
            fourPropagate.setUsername(name);
            if (fourPropagate.getDeId() != null) {
                FourDetails details = fourDetailsService.get(fourPropagate.getDeId());
                model.put("d", details);
            }
        }
        model.put("p", fourPropagate);
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        fourPropagateService.delete(id);
        return ActionResult.ok();
    }

    @Autowired
    private FourPropagateService fourPropagateService;

    @Autowired
    private UserService userService;

    @Autowired
    private FourDetailsService fourDetailsService;

}

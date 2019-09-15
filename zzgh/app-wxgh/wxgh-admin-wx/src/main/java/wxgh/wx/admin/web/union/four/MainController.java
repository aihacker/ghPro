package wxgh.wx.admin.web.union.four;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.sys.service.weixin.four.FourPropagateService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-09 09:18
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @RequestMapping
    public void index(Model model) {
        List<Integer> counts = fourPropagateService.listCounts();
        model.put("counts", counts);
    }

    @RequestMapping
    public ActionResult list(Integer status) {

        PropagateParam query = new PropagateParam();
        query.setStatus(status);


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

        return ActionResult.ok(null, propagates);
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        fourPropagateService.delete(id);
        return ActionResult.ok();
    }

    @Autowired
    private FourPropagateService fourPropagateService;
    
}


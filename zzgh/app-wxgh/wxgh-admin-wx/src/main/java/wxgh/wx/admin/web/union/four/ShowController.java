package wxgh.wx.admin.web.union.four;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.four.FourDetails;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.sys.service.weixin.four.FourDetailsService;
import wxgh.sys.service.weixin.four.FourPropagateService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/20.
 */
@Controller
public class ShowController {

    @RequestMapping
    public void index(Model model, PropagateParam query) {
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
    public ActionResult apply(FourPropagate propagate) {
        fourPropagateService.updateOne(propagate);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(PropagateParam query, FourDetails details) {
        FourPropagate p = fourPropagateService.getPropagate(query);
        if (p != null) {
            FourDetails fourDetails = new FourDetails();
            fourDetails.setFpId(p.getFpId());
            fourDetails.setFpcId(p.getFpcId());
            fourDetails.setBrand(p.getBrand());
            fourDetails.setModelName(p.getModelName());
            fourDetails.setNumb(p.getNumb());
            fourDetails.setRemark(details.getRemark());
            fourDetails.setUserId(p.getUserid());
            fourDetails.setDeptId(p.getDeptId());
            fourDetails.setBuyTime(details.getBuyTime());
            fourDetails.setCondit(details.getCondit());
            fourDetails.setTime(new Date());
            fourDetails.setCondStr(details.getCondStr()); //资产所属
            fourDetails.setUnit(p.getUnit());
            fourDetails.setImgs("");
            fourDetails.setMid(p.getMid());
            fourDetails.setPlanUpdate(details.getPlanUpdate()); //计划更新年限
            fourDetails.setUsefulLife(details.getUsefulLife()); //计划可用年限
            fourDetails.setRepairCount(0); //修理次数
            fourDetails.setPriceSource(details.getPriceSource()); //资金来源
            fourDetails.setPrice(details.getPrice()); //预算单价

            //如果为更换，则更新原来的台账数据
            if (query.getDeviceStatus() == 2) {
                fourDetails.setId(p.getDeId());
                fourDetailsService.updateDetails(fourDetails);
            } else {
                fourDetailsService.addFourDetail(fourDetails);
            }

            //更新入库标识
            fourPropagateService.updateAddIs(p.getId(), 1);
        }
        return ActionResult.ok();
    }

    @Autowired
    private FourPropagateService fourPropagateService;

    @Autowired
    private UserService userService;

    @Autowired
    private FourDetailsService fourDetailsService;

}

package wxgh.wx.web.union.innovation.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.innovation.work.Apply;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.param.union.innovation.QueryInnovateAdvice;
import wxgh.param.union.innovation.work.ApplyQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;
import wxgh.sys.service.weixin.union.innovation.WorkApplyService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 个人审核进度
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 16:31
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private WorkApplyService workApplyService;

    @Autowired
    private InnovateAdviceService innovateAdviceService;

    /**
     * 首页
     * @param model
     */
    @RequestMapping
    public void index(Model model){
        SeUser user = UserSession.getUser();
        //工作坊
        ApplyQuery query = new ApplyQuery();
        query.setUserid(user.getUserid());
        query.setType(Apply.TYPE_SHOP);
        Integer shopCount = workApplyService.countApply(query);

        //合理化建议
        query.setType(Apply.TYPE_SUG);
        Integer adviceCount = workApplyService.countApply(query);

        model.put("shopCount", shopCount);
        model.put("adviceCount", adviceCount);
        model.put("seuser", user);
    }


    /**
     * 获取列表数据
     * @param query
     * @return
     */
    @RequestMapping
    public ActionResult list(ApplyQuery query) {
        Integer count = workApplyService.countApply(query);
        query.setTotalCount(count);

        query.setPageIs(true);
        query.setRowsPerPage(10);

        List<Apply> applies = workApplyService.listApply(query);

        RefData refData = new RefData();
        refData.put("applys", applies);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());
        return ActionResult.ok(null, refData);
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult del(Integer id) {
        workApplyService.delApply(id);
        return ActionResult.ok();
    }

    /**
     * 创新建议详情
     * @param model
     * @param query
     */
    @RequestMapping
    public void detail(Model model, QueryInnovateAdvice query){
        InnovateAdvice innovateAdvice = innovateAdviceService.getOne(query);
        model.put("data", innovateAdvice);
        model.put("showType", query.getShowType());
    }

}

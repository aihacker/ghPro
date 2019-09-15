package wxgh.wx.web.union.innovation.advice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.param.union.innovation.QueryInnovateAdvice;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 创新建议
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
    private InnovateAdviceService innovateAdviceService;

    @RequestMapping
    public void index(Model model, QueryInnovateAdvice queryInnovateAdvice) {
        queryInnovateAdvice.setStatus(0);
        List<InnovateAdvice> innovateAdvices = innovateAdviceService.getList(queryInnovateAdvice);
        model.put("results", innovateAdvices);
    }

    /**
     * 获取列表数据
     * @param queryInnovateAdvice
     * @return
     */
    @RequestMapping
    public ActionResult getResults(QueryInnovateAdvice queryInnovateAdvice) {
        ActionResult actionResult = ActionResult.ok();
        List<InnovateAdvice> innovateAdvices = innovateAdviceService.getList(queryInnovateAdvice);
        actionResult.setData(innovateAdvices);
        return actionResult;
    }


}

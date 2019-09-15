package wxgh.wx.web.common.law;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.common.law.Law;
import wxgh.sys.service.weixin.common.law.LawService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 14:29
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private LawService lawService;

    @RequestMapping
    public void detail(Model model, Integer id) {
        Law law = lawService.getLaw(id);
        model.put("law", law);
    }

    @RequestMapping
    public void index(Model model) {
        List<Law> laws = lawService.getLaws();
        model.put("laws", laws);
    }

}


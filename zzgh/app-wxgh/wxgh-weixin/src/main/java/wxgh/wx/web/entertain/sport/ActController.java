package wxgh.wx.web.entertain.sport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.entertain.sport.act.SportActInfo;
import wxgh.sys.service.weixin.entertain.sport.SportActService;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-28  10:27
 * --------------------------------------------------------- *
 */
@Controller
public class ActController {

    @Autowired
    private SportActService sportActService;

    @RequestMapping
    public void info(Model model, Integer id) {

        SportActInfo sportActInfo = sportActService.get(id);

        model.put("act", sportActInfo);
    }
}

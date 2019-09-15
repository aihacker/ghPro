package wxgh.wx.web.party.opinion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.party.opinion.OpinionDetailData;
import wxgh.sys.service.weixin.party.opinion.PartyOpinionDetailService;
import wxgh.sys.service.weixin.party.opinion.PartyOpinionService;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2018-01-24  08:57
 * --------------------------------------------------------- *
 */
@Controller
public class MainController {

    @Autowired
    private PartyOpinionDetailService partyOpinionDetailService;

    @Autowired
    private PartyOpinionService partyOpinionService;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void info(Model model, Integer id){
        model.put("p", partyOpinionService.get(id));
    }

    @RequestMapping
    public void list(){

    }

    @RequestMapping
    public void add(){

    }

    @RequestMapping
    public void detail(Model model, Integer id){
        OpinionDetailData detailData = partyOpinionDetailService.get(id);
        partyOpinionDetailService.view(id);
        model.put("p", detailData);
    }

}

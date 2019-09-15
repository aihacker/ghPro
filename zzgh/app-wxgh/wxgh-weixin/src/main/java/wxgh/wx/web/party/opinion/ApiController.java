package wxgh.wx.web.party.opinion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.party.opinion.PartyOpinionDetail;
import wxgh.param.party.opinion.OpinionListParam;
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
 * @Date 2018-01-24  08:58
 * --------------------------------------------------------- *
 */
@Controller
public class ApiController {

    @Autowired
    private PartyOpinionService partyOpinionService;

    @Autowired
    private PartyOpinionDetailService partyOpinionDetailService;

    @RequestMapping
    public ActionResult list(Page page){
        page.setPageIs(true);
        page.setRowsPerPage(10);
        return ActionResult.okRefresh(partyOpinionService.list(page), page);
    }

    @RequestMapping
    public ActionResult detail_list(OpinionListParam param){
        param.setPageIs(true);
        param.setRowsPerPage(10);
        return ActionResult.okRefresh(partyOpinionDetailService.list(param), param);
    }

    @RequestMapping
    public ActionResult add(PartyOpinionDetail partyOpinionDetail){
        if(!partyOpinionService.v(partyOpinionDetail.getOpinionId()))
            return ActionResult.error("对不起你不是该意见的发布对象，你没有权限发布。");
        partyOpinionDetailService.save(partyOpinionDetail);
        return ActionResult.ok();
    }

}

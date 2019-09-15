package wxgh.wx.web.entertain.nanhai.place;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.param.entertain.place.YuYueRecordParam;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceYuyueService;
import wxgh.sys.service.weixin.entertain.place.PlaceYuyueService;

/**
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-20  08:51
 * --------------------------------------------------------- *
 */
@Controller
public class ApiController {

    @Autowired
    private NanHaiPlaceYuyueService placeYuyueService;

    @RequestMapping
    public ActionResult my_yuyue(YuYueRecordParam param){
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setUserid(UserSession.getUserid());
        return ActionResult.okRefresh(placeYuyueService.getUserYuyue(param), param);
    }

}

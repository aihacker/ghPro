package wxgh.wx.web.party.branch.act;

import com.libs.common.data.DateUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.party.branch.PartyActInfo;
import wxgh.sys.service.weixin.party.branch.PartyActService;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class MainController {

    /**
     * 活动列表
     */
    @RequestMapping
    public void list(Integer id) {

    }

    /**
     * 添加活动
     *
     * @param model
     */
    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    /**
     * 活动报名列表
     *
     * @param id
     */
    @RequestMapping
    public void join_list(Integer id) {

    }

    /**
     * 活动详情
     */
    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        PartyActInfo actInfo = partyActService.actInfo(id);
        if (actInfo != null) {
            actInfo.setTimeStr(DateUtils.formatDate(actInfo.getStartTime(), actInfo.getEndTime()));
            Date now = new Date();
            if (now.after(actInfo.getStartTime()) && now.before(actInfo.getEndTime())) {
                actInfo.setTimeOut(1); //活动进行中
            } else if (now.after(actInfo.getEndTime())) {
                actInfo.setTimeOut(2); //活动已结束
            }
        }
        model.put("a", actInfo);

        WeixinUtils.sign(model, ApiList.getShareApi());
    }

    @Autowired
    private PartyActService partyActService;
}

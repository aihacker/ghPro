package wxgh.wx.web.pub.user.score;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.sys.service.weixin.pub.score.ExchangeService;
import wxgh.sys.service.weixin.pub.score.ScoreService;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index(Model model) {
        Float sum = sumTotalScore(ScoreType.PLACE.getType()+","+ ScoreType.GIVE.getType());

        //场馆预约积分
        Float yuyueSum = sumScore(ScoreType.PLACE.getType() + "," + ScoreType.GIVE.getType());
        model.put("yuyueScore", yuyueSum);

        //总积分
        model.put("sumScore", sum+yuyueSum);
    }

    /**
     * 积分转移
     *
     * @param model
     * @throws WeixinException
     */
    @RequestMapping
    public void transfer(Model model) throws WeixinException {
        model.put("score", exchangeService.getExchangeScore(UserSession.getUserid()));

        WeixinUtils.sign(model, ApiList.getContactApi());
        WeixinUtils.sign_contact(model);
    }

    @RequestMapping
    public void show(Model model, Integer id){
        model.put("goods", exchangeService.get(id));
    }

    /**
     * 积分兑换
     *
     * @param model
     */
    @RequestMapping
    public void exchange(Model model) {
        model.put("sum", exchangeService.getExchangeScore(UserSession.getUserid()));
    }

    // 我的兑换
    @RequestMapping
    public void my_exchange(Model model){

    }

    // 兑换详情
    @RequestMapping
    public void exchange_show(Integer id, Model model){
        model.put("d", exchangeService.exchangeDetail(id));
    }

    private Float sumScore(String types) {
        ScoreParam param = new ScoreParam();
        param.setGroup(ScoreGroup.USER);
        param.setUserid(UserSession.getUserid());
        param.setStatus(Status.NORMAL.getStatus());
        if (types != null) {
            param.setInTypes(types);
        }
        return scoreService.sumScore(param);
    }

    private Float sumTotalScore(String types) {
        ScoreParam param = new ScoreParam();
        param.setGroup(ScoreGroup.USER);
        param.setUserid(UserSession.getUserid());
        param.setStatus(Status.NORMAL.getStatus());
        if (types != null) {
            param.setNotTypes(types);
        }
        return scoreService.sumTotalScore(param);
    }

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ExchangeService exchangeService;

}

package wxgh.wx.web.pub.user.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.pub.score.ExchangeList;
import wxgh.entity.pub.score.Score;
import wxgh.entity.pub.score.ScoreTransfer;
import wxgh.entity.score.Exchange;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.score.ExchangeParam;
import wxgh.param.user.UserParam;
import wxgh.sys.service.weixin.pub.score.ExchangeService;
import wxgh.sys.service.weixin.pub.score.ScoreService;
import wxgh.sys.service.weixin.pub.score.ScoreTransferService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult score_list(ScoreParam param) {
        param.setGroup(ScoreGroup.USER);
        param.setUserid(UserSession.getUserid());
        param.setStatus(Status.NORMAL.getStatus());
        param.setPageIs(true);

        List<Score> scores = scoreService.listScore(param);
        //查询已兑换
        List<Score> scoress = this.scoreService.listScore_pay(param);
        for (Score score1 : scoress) {
            scores.add(score1);
        }
        String strDate = "2017-05-25";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        try{
            date = sdf.parse(strDate);
        }catch(ParseException e){

        }
        for(Score score:scores){
            if(score.getAddTime() != null) {
                if (score.getAddTime().before(date)) {
                    if (score.getScoreType().equals(5)) {
                        score.setInfo("已过期" + score.getInfo() + ",不计入结算");
                    }
                }
            } else {
                score.setInfo("积分没有生成时间");
            }
        }
        return ActionResult.okRefresh(scores, param);
    }

    @RequestMapping
    public ActionResult good_list(ExchangeParam param) {
        param.setPageIs(true);
        List<ExchangeList> goods = exchangeService.listGoods(param);
        return ActionResult.okRefresh(goods, param);
    }

    @RequestMapping
    public ActionResult exchange(Exchange exchange){
        exchangeService.exchange(exchange);
        return ActionResult.ok();
    }

    // 已兑换
    @RequestMapping
    public ActionResult my_exchange(UserParam param, Integer type){
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setUserid(UserSession.getUserid());
        if(type == null)
            type = 1;
        if(type == 1)
            return ActionResult.okRefresh(exchangeService.getMyExchangeList(param), param);
        // 未兑换
        return ActionResult.okRefresh(exchangeService.getUnExchangeList(param), param);
    }

    // 取消商品兑换
    @RequestMapping
    public ActionResult confirm_cancel(Integer id){
        exchangeService.cancelExchange(id);
        return ActionResult.ok();
    }

    // 确认商品兑换
    @RequestMapping
    public ActionResult confirm_exchange(Integer id){
        exchangeService.confirmYes(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult transfer(ScoreTransfer scoreTransfer) {
        try {
            String userid = UserSession.getUserid();
            //获取用户总积分（非场馆预约积分）
            Float score = exchangeService.getExchangeScore(userid);

            if (score < scoreTransfer.getScore()) {
                return ActionResult.error("对不起，你的积分不够哦");
            }

            scoreTransfer.setUserid(userid);
            scoreTransfer.setStatus(1);
            scoreTransfer.setAddTime(new Date());
            scoreTransfer.setType(1);

            scoreTransferService.addTransfer(scoreTransfer); //保存转移记录
            return ActionResult.ok();
        } catch (Exception e) {
            return ActionResult.error("积分转移失败");
        }

    }

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreTransferService scoreTransferService;

    @Autowired
    private ExchangeService exchangeService;
}

package wxgh.wx.web.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.match.SheyingMatch;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.param.party.match.JoinQuery;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinService;
import wxgh.sys.service.weixin.party.match.SheyingMatchService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 15:35
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private SheyingMatchService sheyingActService;

    @Autowired
    private SheyingMatchJoinService sheyingMatchJoinService;

    @RequestMapping
    public void add(Model model){}

    @RequestMapping
    public void index(Model model, SheyingMatch sheyingAct) throws ParseException {
        List<SheyingMatch> sheyingActList =  sheyingActService.getData(sheyingAct);
        for (SheyingMatch sheyingAct1: sheyingActList){
            sheyingAct1.setCover(sheyingAct1.getCover());
            Date nowTime = new Date();
            Date startTime = sheyingAct1.getStartTime();
            Date endTime = sheyingAct1.getEndTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (startTime.getTime() > nowTime.getTime()){
                sheyingAct1.setStatusStr("活动尚未开始，倒计时"+daysBetween(nowTime, startTime)+"天");
                sheyingAct1.setStatus(1);
                sheyingAct1.setStatusVal(daysBetween(nowTime, startTime) <0?-(daysBetween(nowTime, startTime)):daysBetween(nowTime, startTime));
            }else if (nowTime.getTime() > startTime.getTime() && endTime.getTime()>nowTime.getTime()){
                sheyingAct1.setStatusStr("进行中，剩余"+daysBetween(nowTime, endTime)+"天");
                sheyingAct1.setStatus(2);
                sheyingAct1.setStatusVal(daysBetween(nowTime, endTime)<0?-(daysBetween(nowTime, endTime)):daysBetween(nowTime, endTime));
            }else {
                sheyingAct1.setStatus(3);
                sheyingAct1.setStatusVal(daysBetween(endTime, nowTime)<0?-(daysBetween(endTime, nowTime)):daysBetween(endTime, nowTime));
                sheyingAct1.setStatusStr("活动已结束，逾期"+ daysBetween(endTime, nowTime)+"天");
            }
        }
        model.addAttribute("list", sheyingActList);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    @RequestMapping
    public void detail(Model model, Integer id) throws UnsupportedEncodingException {
        SeUser user = UserSession.getUser();
        SheyingMatch sheyingActQuery = new SheyingMatch();
        sheyingActQuery.setId(id);
        SheyingMatch sheyingAct = sheyingActService.getData(sheyingActQuery).get(0);
        sheyingAct.setCover(sheyingAct.getCover());
        sheyingAct.setContent(URLDecoder.decode(sheyingAct.getContent(), "UTF-8"));
        sheyingAct.setRule(URLDecoder.decode(sheyingAct.getRule(), "UTF-8"));
        model.addAttribute("data", sheyingAct);

        //判断当前用户是否参加
        JoinQuery query = new JoinQuery();
        query.setUserid(user.getUserid());
        query.setType(sheyingAct.getWorksType());
        query.setMid(id);
        SheyingMatchJoin joinStatusId = sheyingMatchJoinService.getStatusAndId(query);
        if (joinStatusId != null) {
            model.put("joinStatus", joinStatusId.getStatus());
            model.put("joinId", joinStatusId.getId());
        }

        Date startTime = sheyingAct.getStartTime();
        Date endTime = sheyingAct.getEndTime();
        Date nowTime = new Date();
        int status = 0;//1未开始，2进行中，3已结束
        if (startTime.getTime() > nowTime.getTime()) {
            status = 1;
        } else if (nowTime.getTime() > startTime.getTime() && endTime.getTime() > nowTime.getTime()) {
            status = 2;
        } else if (nowTime.getTime() > endTime.getTime()) {
            status = 3;
        }
        model.addAttribute("actStatus", status);
    }

    @RequestMapping
    public ActionResult join(SheyingMatchJoin join) {
        SeUser user = UserSession.getUser();

        Integer id = sheyingMatchJoinService.check(join);
        if(id != null && id > 0)
            return ActionResult.ok(null, id);

        join.setUserid(user.getUserid());
        join.setAddTime(new Date());
        join.setStatus(0); //未上传作品

        sheyingMatchJoinService.addData(join);

        return ActionResult.ok(null, join.getId());
    }
    
}


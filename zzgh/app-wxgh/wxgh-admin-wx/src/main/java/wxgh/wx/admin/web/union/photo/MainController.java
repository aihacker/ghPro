package wxgh.wx.admin.web.union.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.party.match.SheyingMatch;
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
        SheyingMatch sheyingActQuery = new SheyingMatch();
        sheyingActQuery.setId(id);
        SheyingMatch sheyingAct = sheyingActService.getData(sheyingActQuery).get(0);
        sheyingAct.setCover(sheyingAct.getCover());
        sheyingAct.setContent(URLDecoder.decode(sheyingAct.getContent(), "UTF-8"));
        sheyingAct.setRule(URLDecoder.decode(sheyingAct.getRule(), "UTF-8"));
        model.addAttribute("data", sheyingAct);
    }

    
}


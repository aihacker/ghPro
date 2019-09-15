package wxgh.wx.web.party.syact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.match.SheyingAct;
import wxgh.entity.party.match.SheyingActJoin;
import wxgh.sys.service.weixin.party.syact.SheyingActJoinService;
import wxgh.sys.service.weixin.party.syact.SheyingActService;

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
 * @Date 2017-08-07 18:01
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private SheyingActService sheyingActService;

    @Autowired
    private SheyingActJoinService sheyingActJoinService;

    @RequestMapping
    public void index(Model model,SheyingAct sheyingActQuery) throws ParseException {
        List<SheyingAct> sheyingActList =  sheyingActService.getData(sheyingActQuery);
        if(sheyingActList != null)
        for (SheyingAct sheyingAct1: sheyingActList){
            sheyingAct1.setCover(sheyingAct1.getCover());
            Date nowTime = new Date();
            Date startTime = sheyingAct1.getStartTime();
            Date endTime = sheyingAct1.getEndTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int countDay = 0;
            if (startTime.getTime() > nowTime.getTime()){
                countDay = daysBetween(nowTime, startTime)<0?-(daysBetween(nowTime, startTime)):daysBetween(nowTime, startTime);
                sheyingAct1.setStatusStr("活动尚未开始，倒计时"+countDay+"天");
            }else if (nowTime.getTime() > startTime.getTime() && endTime.getTime()>nowTime.getTime()){
                countDay = daysBetween(nowTime, endTime)<0?-(daysBetween(nowTime, endTime)):daysBetween(nowTime, endTime);
                sheyingAct1.setStatusStr("进行中，剩余"+countDay+"天");
            }else {
                countDay = daysBetween(endTime, nowTime)<0?-(daysBetween(endTime, nowTime)):daysBetween(endTime, nowTime);
                sheyingAct1.setStatusStr("活动已结束，逾期"+ countDay+"天");
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

    /**
     * 活动详情
     *
     * @param model
     * @param id
     */
    @RequestMapping
    public void detail(Model model, Integer id){
        SeUser user = UserSession.getUser();
        SheyingAct sheyingActQuery = new SheyingAct();
        sheyingActQuery.setId(id);
        SheyingAct sheyingAct = sheyingActService.getData(sheyingActQuery).get(0);
        sheyingAct.setCover(PathUtils.getImagePath(sheyingAct.getCover()));
        model.addAttribute("data", sheyingAct);

        SheyingActJoin sheyingActJoinQuery = new SheyingActJoin();
        sheyingActJoinQuery.setAid(id);
        sheyingActJoinQuery.setUserid(user.getUserid());
        List<SheyingActJoin> sheyingActJoinList = sheyingActJoinService.getData(sheyingActJoinQuery);
        if (null != sheyingActJoinList && sheyingActJoinList.size()>0){
            model.put("isJoin", 1);
        }else {
            model.put("isJoin", 0);
        }

        SheyingActJoin sheyingActJoinQuery2 = new SheyingActJoin();
        sheyingActJoinQuery2.setAid(id);
        List<SheyingActJoin> sheyingActJoinListRS = sheyingActJoinService.getData(sheyingActJoinQuery2);
        model.addAttribute("joinList", sheyingActJoinListRS);

        Date startTime = sheyingAct.getStartTime();
        Date endTime = sheyingAct.getEndTime();
        Date nowTime = new Date();
        int status = 0;//1未开始，2进行中，3已结束
        if (startTime.getTime()>nowTime.getTime()){
            status = 1;
        }else if (nowTime.getTime()>startTime.getTime() && endTime.getTime()>nowTime.getTime()){
            status = 2;
        }else if (nowTime.getTime()>endTime.getTime()){
            status = 3;
        }
        model.put("actStatus", status);
    }

}


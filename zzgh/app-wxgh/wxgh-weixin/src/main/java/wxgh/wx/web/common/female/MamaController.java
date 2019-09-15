package wxgh.wx.web.common.female;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.common.female.FemaleMamaData;
import wxgh.entity.common.female.FemaleMamaJoin;
import wxgh.param.common.female.QueryFemaleMama;
import wxgh.sys.service.weixin.common.female.FemaleMamaJoinService;
import wxgh.sys.service.weixin.common.female.FemaleMamaService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ✔ on 2017/5/4.
 */
@Controller
public class MamaController {

    @Autowired
    private FemaleMamaService diNoticeService;

    @Autowired
    private FemaleMamaService femaleMamaService;

    @Autowired
    private FemaleMamaJoinService femaleMamaJoinService;

    @RequestMapping
    public void index(Model model){

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");


        List<Map>  mapList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Date dateTmp = new Date(date.getTime() + i * 24 * 60 * 60 * 1000);
            String dateFullStr = simpleDateFormat2.format(dateTmp);
            String dateStr = simpleDateFormat.format(dateTmp);
            int week = getWeek(dateTmp);


            Map map = new HashMap();
            map.put("dateFullStr", dateFullStr);
            map.put("dateStr", dateStr);
            map.put("week", getWeekStr(week));

            mapList.add(map);


        }

        model.addAttribute("dateList", mapList);

    }

    public String getWeekStr (int week){
        String weekStr = null;
        switch (week){
            case 1:{
                weekStr = "周一";
                break;
            }
            case 2:{
                weekStr = "周二";
                break;
            }
            case 3:{
                weekStr = "周三";
                break;
            }
            case 4:{
                weekStr = "周四";
                break;
            }
            case 5:{
                weekStr = "周五";
                break;
            }
            case 6:{
                weekStr = "周六";
                break;
            }
            case 7:{
                weekStr = "周日";
                break;
            }
            default: {
                weekStr = "未知";
                break;
            }
        }
        return  weekStr;
    }

    public int getWeek(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        String day = format.format(date);
        int currentdayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            currentdayForWeek = 7;
        } else {
            currentdayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return currentdayForWeek;
    }

    @RequestMapping
    public ActionResult getListData(Integer indexID, boolean isFirst, boolean isRefresh) throws UnsupportedEncodingException {
        QueryFemaleMama queryFemaleMama = new QueryFemaleMama();

        if (!isFirst && !isRefresh){
            queryFemaleMama.setIndexID(indexID);
        }

        List<FemaleMamaData> femaleMamaList = diNoticeService.getDatas(queryFemaleMama);

        for (FemaleMamaData femaleMama: femaleMamaList){
            femaleMama.setName(URLDecoder.decode(femaleMama.getName(), "UTF-8"));
            femaleMama.setContent(URLDecoder.decode(femaleMama.getContent(), "UTF-8"));
//            femaleMama.setCover(PathUtils.getImagePath(femaleMama.getCover()));
        }

        return ActionResult.ok(null, femaleMamaList);
    }

    @RequestMapping
    public void show(Model model, Integer id) throws UnsupportedEncodingException, WeixinException {

        WeixinUtils.sign(model, ApiList.getCloseApi());

        QueryFemaleMama queryFemaleMama = new QueryFemaleMama();
        queryFemaleMama.setId(id);
        FemaleMamaData femaleMama = femaleMamaService.getDatas(queryFemaleMama).get(0);

        femaleMama.setName(URLDecoder.decode(femaleMama.getName(), "UTF-8"));
        femaleMama.setContent(URLDecoder.decode(femaleMama.getContent(), "UTF-8"));
//        femaleMama.setCover(PathUtils.getImagePath(femaleMama.getCover()));


        model.addAttribute("mama", femaleMama);
    }


    @RequestMapping
    public ActionResult join(FemaleMamaJoin femaleMamaJoin) throws UnsupportedEncodingException {

        if (femaleMamaJoin.getStartTime() >= femaleMamaJoin.getEndTime())
            return ActionResult.error("开始时间不能大于结束时间");

        femaleMamaJoin.setDateStr(URLDecoder.decode(femaleMamaJoin.getDateStr(), "UTF-8"));
        FemaleMamaJoin femaleMamaJoinQuery = new FemaleMamaJoin();
        femaleMamaJoinQuery.setDateStr(femaleMamaJoin.getDateStr());
        femaleMamaJoinQuery.setMid(femaleMamaJoin.getMid());
        List<FemaleMamaJoin>  femaleMamaJoinList = femaleMamaJoinService.getData(femaleMamaJoinQuery);

        Integer startTime = femaleMamaJoin.getStartTime();
        Integer endTime = femaleMamaJoin.getEndTime();

        for (FemaleMamaJoin femaleMamaJoin1: femaleMamaJoinList){
            if ((startTime >= femaleMamaJoin1.getStartTime() && startTime <= femaleMamaJoin1.getEndTime()) || (endTime >= femaleMamaJoin1.getStartTime() && endTime <= femaleMamaJoin1.getEndTime()) || (startTime < femaleMamaJoin1.getStartTime() && endTime > femaleMamaJoin1.getEndTime())){
                return ActionResult.error("对不起该时间段已有预约");
            }else {
                continue;
            }
        }

        SeUser user = UserSession.getUser();

        femaleMamaJoin.setAddTime(new Date());
        femaleMamaJoin.setUserid(user.getUserid());
        femaleMamaJoinService.add(femaleMamaJoin);
        return ActionResult.ok();
    }




}

package wxgh.wx.web.common.female;


import com.libs.common.json.JsonUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.common.female.FemaleLessonData;
import wxgh.entity.common.female.FemaleLessonJoin;
import wxgh.param.common.female.QueryFemaleLesson;
import wxgh.param.common.female.QueryFemaleLessonJoin;
import wxgh.sys.service.weixin.common.female.FemaleLessonJoinService;
import wxgh.sys.service.weixin.common.female.FemaleLessonService;

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
public class LessonController {

    @Autowired
    private FemaleLessonService diNoticeService;

    @Autowired
    private FemaleLessonService femaleLessonService;

    @Autowired
    private FemaleLessonJoinService femaleLessonJoinService;

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
            case 1:
                weekStr = "周一";
                break;
            case 2:
                weekStr = "周二";
                break;
            case 3:
                weekStr = "周三";
                break;
            case 4:
                weekStr = "周四";
                break;
            case 5:
                weekStr = "周五";
                break;
            case 6:
                weekStr = "周六";
                break;
            case 7:
                weekStr = "周日";
                break;
            default:
                weekStr = "未知";
                break;
        }
        return  weekStr;
    }

    private int getWeek(Date date) {
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
    public ActionResult getListData(Integer indexID, boolean isFirst, boolean isRefresh, Date date) throws UnsupportedEncodingException {
        QueryFemaleLesson queryFemaleLesson = new QueryFemaleLesson();

        if (!isFirst && !isRefresh){
            queryFemaleLesson.setIndexID(indexID);
        }

        if(date != null)
            queryFemaleLesson.setDate(date);

        List<FemaleLessonData> femaleLessonList = diNoticeService.getDatas(queryFemaleLesson);

        for (FemaleLessonData femaleLesson: femaleLessonList){
            femaleLesson.setName(URLDecoder.decode(femaleLesson.getName(), "UTF-8"));
            femaleLesson.setContent(URLDecoder.decode(femaleLesson.getContent(), "UTF-8"));
//            femaleLesson.setCover(PathUtils.getImagePath(femaleLesson.getCover()));
        }

        return ActionResult.ok(null, femaleLessonList);
    }

    @RequestMapping
    public void show(Model model, Integer id) throws UnsupportedEncodingException, WeixinException {

        WeixinUtils.sign(model, ApiList.getCloseApi());

        QueryFemaleLesson queryFemaleLesson = new QueryFemaleLesson();
        queryFemaleLesson.setId(id);
        FemaleLessonData femaleLesson = femaleLessonService.getDatas(queryFemaleLesson).get(0);

        femaleLesson.setName(URLDecoder.decode(femaleLesson.getName(), "UTF-8"));
        femaleLesson.setContent(URLDecoder.decode(femaleLesson.getContent(), "UTF-8"));
//        femaleLesson.setCover(PathUtils.getImagePath(femaleLesson.getCover()));

        if (!StrUtils.empty(femaleLesson.getFiles())) {
            String filesStr = femaleLesson.getFiles();

            Map map = JsonUtils.parseMap(filesStr, String.class, List.class);

            List<Map> mapList = (List<Map>) map.get("files");
            List<Map> mapListRs = new ArrayList<>();
            model.addAttribute("filesCount", mapList);
            for (Map map1 : mapList) {
                Map mapRs = new HashMap<>();
                String name = URLDecoder.decode((String) map1.get("name"), "UTF-8");
                mapRs.put("name", name);
                String url = URLDecoder.decode((String) map1.get("url"), "UTF-8");
                url = PathUtils.getImagePath(url);
                mapRs.put("url", url);
                mapListRs.add(mapRs);
            }
            model.addAttribute("filesMap", mapListRs);
        }

        model.addAttribute("lesson", femaleLesson);

        SeUser user = UserSession.getUser();
        QueryFemaleLessonJoin queryFemaleLessonJoin = new QueryFemaleLessonJoin();
        queryFemaleLessonJoin.setFid(id);
        queryFemaleLessonJoin.setUserid(user.getUserid());
        List<FemaleLessonJoin> femaleLessonJoins = femaleLessonJoinService.getData(queryFemaleLessonJoin);
        boolean join = false;
        if (null != femaleLessonJoins && femaleLessonJoins.size() >0){
            join = true;
        }

        // 检查是否报名
        model.addAttribute("join", femaleLessonJoinService.checkJoin(user.getUserid(), id));


        int joinCount = 0;

        QueryFemaleLessonJoin queryFemaleLessonJoin2 = new QueryFemaleLessonJoin();
        queryFemaleLessonJoin2.setFid(id);
        queryFemaleLessonJoin2.setUserid(user.getUserid());
        queryFemaleLessonJoin2.setAll(true);
        List<FemaleLessonJoin> femaleLessonJoin2s = femaleLessonJoinService.getData(queryFemaleLessonJoin2);
        List<FemaleLessonJoin> femaleLessonJoinsRs = new ArrayList<>();


        if (null != femaleLessonJoin2s && femaleLessonJoin2s.size() >0){
            joinCount = femaleLessonJoin2s.size();
            femaleLessonJoinsRs = femaleLessonJoin2s.size()>3?femaleLessonJoin2s.subList(0,3):femaleLessonJoin2s;

        }

        model.addAttribute("joinCount", joinCount);
        model.addAttribute("joinList", femaleLessonJoinsRs);

        // 检查课程状态, 1 开设中, 2 未开始 3 已结束
        Integer open = 1;
        Date now = new Date();
        if(now.getTime() < femaleLesson.getStartTime().getTime())
            open = 2;
        else if(now.getTime() > femaleLesson.getEndTime().getTime())
            open = 3;

        model.put("open", open);

    }

    @RequestMapping
    public ActionResult join(FemaleLessonJoin femaleLessonJoin){
        SeUser user = UserSession.getUser();
        femaleLessonJoin.setUserid(user.getUserid());
        if(user.getUserid() == null || femaleLessonJoin.getFid() == null)
            return ActionResult.ok("课程不存在");
        if(femaleLessonJoinService.checkJoin(user.getUserid(), femaleLessonJoin.getFid()))
            return ActionResult.ok("你已经报名了");
        femaleLessonJoin.setAddTime(new Date());
        femaleLessonJoinService.add(femaleLessonJoin);
        return ActionResult.ok();
    }



}

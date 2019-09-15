package wxgh.wx.web.union.woman.mom;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.model.Model;
import wxgh.data.union.woman.mom.MomShow;
import wxgh.data.union.woman.mom.TimeList;
import wxgh.sys.service.weixin.union.woman.MomService;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/14.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list(Model model) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Date dateTmp = new Date(date.getTime() + i * 24 * 60 * 60 * 1000);
            String dateStr = simpleDateFormat.format(dateTmp);
            int week = DateUtils.getWeek(dateTmp);
            Map map = new HashMap();
            map.put("weekInt", week);
            map.put("dateStr", dateStr);
            map.put("dateid", DateFuncs.toIntDate(dateTmp));
            map.put("week", DateUtils.getWeekName(week));
            mapList.add(map);
        }

        model.addAttribute("dateList", mapList);
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        MomShow momShow = momService.show(id);
        model.put("m", momShow);
    }

    @RequestMapping
    public void time(Model model, Integer id) {
        List<TimeList> times = momService.listTime(id);
        model.put("times", times);
    }

    @RequestMapping
    public void yuyue(Model model, Integer id) {
        String week = momService.week(id);
        List<Map> weeks = new ArrayList<>();
        String[] weekStrs = week.split(",");
        for (String w : weekStrs) {
            Map map = new HashMap();
            int weekId = Integer.valueOf(w);
            map.put("week", w);
            map.put("name", DateUtils.getWeekName(weekId));
            map.put("dateid", getDateId(weekId));
            weeks.add(map);
        }
        model.put("weeks", weeks);
    }

    @RequestMapping
    public void yuyue_list() {
    }

    private Integer getDateId(int week) {
        int nowWeek = DateUtils.getWeek(new Date()); //获取今天星期
        int nowDateId = DateFuncs.getIntToday();
        int diff;
        if (week > nowWeek) {
            diff = week - nowWeek;
        } else {
            diff = week + nowWeek - 1;
        }
        int dateid = DateFuncs.addDay(nowDateId, diff);
        return dateid;
    }

    @Autowired
    private MomService momService;
}

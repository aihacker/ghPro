package wxgh.admin.web.union.woman.mom;

import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.union.woman.MomTime;
import wxgh.entity.union.woman.WomanMom;
import wxgh.entity.union.woman.WomanMomData;
import wxgh.entity.union.woman.WomanMomTime;
import wxgh.param.union.woman.MomParam;
import wxgh.param.union.woman.MomYuyueParam;
import wxgh.sys.service.weixin.union.woman.MomService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(MomParam param) {
        param.setPageIs(true);
        List<WomanMom> moms = momService.listAdminMom(param);
        return ActionResult.okAdmin(moms, param);
    }

    @RequestMapping
    public ActionResult delete(String id){
        momService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add_mom(String womanMom){
        WomanMomData womanMomData = JsonUtils.parseBean(womanMom, WomanMomData.class);
        List<WomanMomTime> times = womanMomData.getTimes();
        if (womanMomData.getWeek()!=null)
            womanMomData.setStatus(1);
        else
            womanMomData.setStatus(0);
        womanMomData.setAddTime(new Date());
        Integer id = momService.add_mom(womanMomData);
        List<WomanMomTime> womanmomTimes = womanMomData.getTimes();
        if(!womanmomTimes.isEmpty()){
            for (WomanMomTime time: womanmomTimes) {
                time.setStatus(1);
                time.setMid(id);
            }
            momService.add_times(womanmomTimes);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult update(String womanMom){
        WomanMomData womanMomData = JsonUtils.parseBean(womanMom, WomanMomData.class);
        womanMomData.setAddTime(new Date());
        List<WomanMomTime> times = womanMomData.getTimes();
        //删除掉time表中所有的这个id下的值
        momService.deleteByMid(womanMomData.getId());
        //更新mom数据
        momService.updateMom(womanMomData);
        if(!times.isEmpty()){
            for (WomanMomTime time: times) {
                time.setMid(womanMomData.getId());
                time.setStatus(1);
            }
            momService.add_times(times);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_yuyue(MomYuyueParam param){
        param.setPageIs(true);
        Date predate = DateUtils.getFirstWeekDay(new Date(), -1);
        Integer first = DateUtils.getFirstWeekDayInt(predate);
        Integer last = DateUtils.getLastWeekDayInt(new Date());
        param.setFirstweekDay(first);
        param.setLastweekDay(last);
        List<MomYuyueParam> momYuyes = momService.getMomYuyes(param);
        if (momYuyes!=null){
            for (MomYuyueParam m:momYuyes) {
                String date = DateFuncs.formatDate(m.getDate());
                m.setCurrentData(date);
            }
        }
        return ActionResult.okAdmin(momYuyes,param);
    }

    @RequestMapping
    public ActionResult get_week(Integer id){
        List<Integer> integers = new ArrayList<>();
        String weeks = momService.getWeek(id);
        String[] week = weeks.split(",");
        //找出没有开放的时间
        for (int i=1;i<8;i++){
            String s = String.valueOf(i);
            boolean contains = Arrays.asList(week).contains(s);
            if(!contains)
                integers.add(i);
        }
        return ActionResult.okWithData(integers);
    }

    @RequestMapping
    public ActionResult get_open_time(Integer id){
        List<MomTime> timeList = momService.getTimeList(id);
        return ActionResult.okWithData(timeList);
    }

    @Autowired
    private MomService momService;

}


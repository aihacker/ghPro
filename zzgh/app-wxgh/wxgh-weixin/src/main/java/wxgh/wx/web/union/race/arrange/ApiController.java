package wxgh.wx.web.union.race.arrange;


import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import wxgh.entity.union.race.*;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.param.union.race.arrange.Arrange;
import wxgh.param.union.race.arrange.ArrangeParam;
import wxgh.param.union.race.arrange.ArrangeResult;
import wxgh.param.union.race.arrange.RaceApi;
import wxgh.param.union.race.arrange.group.GroupInfo;
import wxgh.param.union.race.arrange.result.Arrg;
import wxgh.param.union.race.arrange.result.Result;
import wxgh.param.union.race.arrange.result.ResultDay;
import wxgh.param.union.race.arrange.result.Time;
import wxgh.sys.service.weixin.union.race.RaceArrangeService;
import wxgh.sys.service.weixin.union.race.RaceGroupService;
import wxgh.sys.service.weixin.union.race.RaceJoinService;
import wxgh.sys.service.weixin.union.race.RaceService;

import java.util.*;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-27  14:17
 * --------------------------------------------------------- *
 */
@Controller
public class ApiController {

    @Autowired
    private RaceJoinService raceJoinService;

    @Autowired
    private RaceArrangeService raceArrangeService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceGroupService raceGroupService;

    @RequestMapping
    public ActionResult bianpai(ArrangeParam param) {

        Race race = raceService.getRace(param.getRaceId());
        if (race == null) {
            return ActionResult.error("比赛可能取消了哦");
        }
        if (race.getBianpaiIs() == 1) {
            ActionResult result = ActionResult.error("比赛已编排哦");
            result.setData(-101);
            return result;
        }

        Date today = new Date();
        if (race.getEndTime().before(today)) {
            return ActionResult.error("比赛已结束哦");
        }

        List<RaceArrange> raceArranges = new ArrayList<>();

        if (param.getSaizhi() == ArrangeParam.TYPE_XUNHUAN) {
            RaceJoinQuery joinQuery = new RaceJoinQuery();
            joinQuery.setRaceId(param.getRaceId());
            List<RaceJoin> joins = raceJoinService.getJoinsAndName(joinQuery);
            if (!TypeUtils.empty(joins) && joins.size() >= 2) {
                Collections.shuffle(joins);//重新打乱顺序

                Map<Integer, RaceJoin> joinMap = new HashMap<>();
                int i = 1;
                for (RaceJoin join : joins) {
                    joinMap.put(i, join);
                    i++;
                }

                ArrangeResult arrangeResult = null;
                if (param.getXunhuan() == 1) {
                    arrangeResult = RaceApi.single_loop_guding(joins.size());
                } else {
                    arrangeResult = RaceApi.single_loop_beige(joins.size());
                }
                Map<Integer, List<Arrange>> resultMap = arrangeResult.getArrangeMap();

                i = 0;
                for (Map.Entry<Integer, List<Arrange>> entry : resultMap.entrySet()) {
                    List<Arrange> results = entry.getValue();
                    for (int j = 0; j < results.size(); j++) {
                        int raval1 = results.get(j).getRival1();
                        int raval2 = results.get(j).getRival2();

                        RaceArrange arrange = new RaceArrange();
                        arrange.setRaceId(param.getRaceId());
                        arrange.setRival1(raval1 == 0 ? null : joins.get(raval1 - 1).getUserid());
                        arrange.setName1(raval1 == 0 ? null : joins.get(raval1 - 1).getName());
                        arrange.setRival2(raval2 == 0 ? null : joins.get(raval2 - 1).getUserid());
                        arrange.setName2(raval2 == 0 ? null : joins.get(raval2 - 1).getName());
                        arrange.setLunNum(entry.getKey());
                        arrange.setOrderNum(entry.getKey() * (i + 1));
                        arrange.setType(race.getRaceType());
                        arrange.setStatus(0);
                        arrange.setArrangeType(RaceArrange.ARRANGE_TYPE_XUNHUAN);
                        arrange.setLunkong((StrUtils.empty(arrange.getRival1()) || StrUtils.empty(arrange.getRival2())) ? 1 : 0);

                        raceArranges.add(arrange);

                        i++;
                    }
                }
            } else {
                return ActionResult.error("参赛人员不能少于2人哦");
            }
        } else if (param.getSaizhi() == ArrangeParam.TYPE_GROUPXUNHUAN) {
            List<GroupInfo> groupInfos = raceGroupService.getGroupInfo(param.getRaceId());
            if (!TypeUtils.empty(groupInfos)) {
                raceArranges = RaceApi.group_loop(groupInfos, race);
            }
        }


        if (TypeUtils.empty(raceArranges)) {
            return ActionResult.error("参赛人员不能少于2人哦");
        }

        //修改场地
        int i = 0;
        int j = 0;

        String amTime = param.getAmTime();
        String pmTime = param.getPmTime();
        if (StrUtils.empty(amTime)) {
            amTime = "09:00";
        }
        if (StrUtils.empty(pmTime)) {
            pmTime = "14:00";
        }

        int[] ams = getTimes(amTime);
        int[] pms = getTimes(pmTime);

        String startTime = param.getStartTime();
        if (StrUtils.empty(startTime)) {
            startTime = DateUtils.formatDate(race.getStartTime(), "yyyy-MM-dd");
        }

        int day = getDay(startTime); //日期
        int hour = ams[0]; //小时
        int minute = ams[1]; //分钟
        int a = 1; //上午
        int b = 1; //下午
        for (RaceArrange r : raceArranges) {
            if (!StrUtils.empty(r.getRival1()) && !StrUtils.empty(r.getRival2())) {
                if (i % param.getAddrNumb() == 0) {
                    i = 0;
                    if (hour < 12) {
                        hour = ams[0] + (a - 1);
                        a++;
                    }
                    if (hour >= 12 && hour <= 18) {
                        hour = pms[0] + (b - 1);
                        a = 1;
                        b++;
                    }
                    if (hour > 18) {
                        hour = ams[0];
                        day++;
                        a = 1;
                        b = 1;
                    }
                }
                String timeStr = getYM(startTime) + "-" + day + " " + hour + ":" + minute;
                raceArranges.get(j).setRaceTime(DateUtils.formatStr(timeStr, "yyyy-MM-dd HH:mm"));

                raceArranges.get(j).setAddress("场地" + (i + 1));
                i++;
            }
            j++;
        }
        raceArrangeService.saveList(raceArranges);

        raceService.updateBianpaiStatus(param.getRaceId(), 1); //更新编排状态为已编排

        return ActionResult.ok();
    }

    private int[] getTimes(String time) {
        String[] ams = time.split(":");
        int amHour = Integer.parseInt(ams[0]);
        int amMil = Integer.parseInt(ams[1]);
        return new int[]{amHour, amMil};
    }

    private int getDay(String time) {
        Date date = DateUtils.formatStr(time, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private String getYM(String time) {
        Date date = DateUtils.formatStr(time, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
    }



    // group ---------------------------------


    @RequestMapping
    public ActionResult list(Integer id) {

        List<GroupInfo> groupInfos = raceGroupService.getGroupInfo(id);

        return ActionResult.ok(null, groupInfos);
    }

    @RequestMapping
    public ActionResult group(Integer id) {

        List<GroupInfo> infos = new ArrayList<>();

        RaceJoinQuery joinQuery = new RaceJoinQuery();
        joinQuery.setRaceId(id);
        List<RaceJoin> joins = raceJoinService.getJoinsAndName(joinQuery);
        if (!TypeUtils.empty(joins)) {
            Collections.shuffle(joins);
            Map<Integer, List<Integer>> groupMap = RaceApi.group_loop_fenzu(joins.size());

            for (Map.Entry<Integer, List<Integer>> entry : groupMap.entrySet()) {
                Integer key = entry.getKey();
                List<Integer> val = entry.getValue();

                GroupInfo info = new GroupInfo();
                RaceGroup group = new RaceGroup();
                group.setRaceId(id);
                group.setName(TypeUtils.getNameByIndex(key - 1) + "组");
                group.setNumb(val.size());
                info.setGroup(group);

                List<RaceGroupDetail> details = new ArrayList<>();
                int i = 1;
                for (Integer v : val) {
                    RaceJoin join = joins.get(v - 1);
                    RaceGroupDetail detail = new RaceGroupDetail();
                    detail.setName(join.getName());
                    detail.setUserid(join.getUserid());
                    detail.setUserOrder(i);
                    details.add(detail);
                    i++;
                }
                info.setDetails(details);

                infos.add(info);
            }
            raceGroupService.saveMap(infos, id);
        }
        return ActionResult.ok(null, infos);
    }


    // group ---------------------------------





    // result -----------------


    @RequestMapping
    public ActionResult get(Integer id) {

        //获取未轮空编排
        List<RaceArrange> arranges = raceArrangeService.getArranges(id, null, 0);

        Map<String, List<RaceArrange>> dayArrangesMap = new LinkedHashMap<>();
        Map<String, List<String>> hourMap = new LinkedHashMap<>();
        Map<String, RaceArrange> arrangeMap = new LinkedHashMap<>();
        List<String> address = new ArrayList<>();
        for (RaceArrange a : arranges) {
            String day = DateUtils.formatDate(a.getRaceTime(), "yyyy-MM-dd");
            String hour = DateUtils.formatDate(a.getRaceTime(), "HH:mm");
            //日
            List<RaceArrange> dayTmp = dayArrangesMap.getOrDefault(day, null);
            if (dayTmp == null) {
                dayTmp = new ArrayList<>();
                dayTmp.add(a);
                dayArrangesMap.put(day, dayTmp);
            } else {
                dayArrangesMap.get(day).add(a);
            }

            //时间
            List<String> hours = hourMap.getOrDefault(day, null);
            if (hours == null) {
                hours = new ArrayList<>();
                hours.add(hour);
                hourMap.put(day, hours);
            } else {
                if (!hours.contains(hour)) {
                    hourMap.get(day).add(hour);
                }
            }

            //地址
            String key = day + hour + a.getAddress();
            arrangeMap.put(key, a);


            if (!address.contains(a.getAddress())) {
                address.add(a.getAddress());
            }
        }

        Result result = new Result();
        result.setAddress(address);
        List<ResultDay> days = new ArrayList<>();
        result.setResultDays(days);

        for (Map.Entry<String, List<RaceArrange>> entry : dayArrangesMap.entrySet()) {
            ResultDay resultDay = new ResultDay();
            resultDay.setDay(entry.getKey());

            List<Time> times = new ArrayList<>();
            resultDay.setTimes(times);

            List<String> timeStrs = hourMap.get(entry.getKey());
            for (String t : timeStrs) {
                Time time = new Time();
                time.setTime(t);
                List<Arrg> arrgs = new ArrayList<>();
                time.setArrgs(arrgs);


                for (String a : address) {
                    String key = entry.getKey() + t + a;
                    RaceArrange race = arrangeMap.get(key);
                    if (race == null) {
                        race = new RaceArrange();
                    }
                    Arrg arrg = new Arrg();
                    arrg.setUser1(race.getName1());
                    arrg.setUser2(race.getName2());
                    arrgs.add(arrg);
                }
                times.add(time);
            }
            days.add(resultDay);
        }

        RefData refData = new RefData();
        refData.put("bianpais", result);

        //获取轮空名单
        List<RaceArrange> lunkongArrange = raceArrangeService.getArranges(id, null, 1);

        if (!TypeUtils.empty(lunkongArrange)) {
            TreeMap<Integer, List<String>> lunkongUsers = new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            for (RaceArrange a : lunkongArrange) {
                List<String> tmp = lunkongUsers.get(a.getLunNum());
                String rival = StrUtils.empty(a.getRival1()) ? a.getName2() : a.getName1();
                if (tmp == null) {
                    tmp = new ArrayList<>();
                    tmp.add(rival);
                    lunkongUsers.put(a.getLunNum(), tmp);
                } else {
                    lunkongUsers.get(a.getLunNum()).add(rival);
                }
            }
            refData.put("lunkongs", lunkongUsers);
        }

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult edit(Integer type, String old, String edit, Integer id) {
        if (type == 1) {
            raceArrangeService.updateAddress(id, old, edit);
        } else {
            old += ":00";
            raceArrangeService.updateTime(id, old, edit);
        }
        return ActionResult.ok();
    }

    // result -----------------



}

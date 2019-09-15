package wxgh.wx.web.entertain.nanhai.place;

import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.utils.place.NanHaiVerifyUtils;
import wxgh.data.entertain.place.*;
import wxgh.data.entertain.place.yuyue.*;
import wxgh.data.entertain.place.yuyue.YuyueList;
import wxgh.entity.entertain.nanhai.place.*;
import wxgh.entity.entertain.place.*;
import wxgh.param.entertain.place.GudingParam;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.param.entertain.place.TimeParam;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.sys.service.weixin.entertain.nanhai.place.*;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import java.util.*;

/**
 * Created by cby on 2017/7/27.
 */
@Controller
public class MainController {

    @Autowired
    private NanHaiPlaceService placeService;

    @Autowired
    private NanHaiPlaceCateService placeCateService;

    @Autowired
    private NanHaiPlaceYuyueService placeYuyueService;

    @Autowired
    private NanHaiPlaceimgService placeimgService;

    @Autowired
    private NanHaiPlaceSiteService placeSiteService;

    @Autowired
    private NanHaiVerifyUtils verifyUtils;

    @Autowired
    private NanHaiPlaceGudingService placeGudingService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private NanHaiPlaceTimeService placeTimeService;


    @RequestMapping
    public void index(Model model) {
        List<NanHaiPlaceCate> cates = placeCateService.getCates(1);

        model.put("cates", cates);
    }

    // 我的预约
    @RequestMapping
    public void list(Model model) {
        String userid = UserSession.getUserid();
        List<MeYuyue> yuyues = placeYuyueService.getUserYuyue(1, userid);
        model.put("nouse", yuyues);
        model.put("nouserCount", yuyues == null ? 0 : yuyues.size());

        List<MeYuyue> nothingYuyues = placeYuyueService.getUserYuyue(3, userid);
        model.put("nothings", nothingYuyues);
        model.put("nothingCount", nothingYuyues == null ? 0 : nothingYuyues.size());
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        PlaceParam placeQuery = new PlaceParam();
        placeQuery.setId(id);
        NanHaiPlace place = placeService.getPlace(placeQuery);

        List<String> imgs = placeimgService.getImgs(id);
        List<String> newImgs = new ArrayList<>();
        if (!TypeUtils.empty(imgs)) {
            for (int i = 0; i < imgs.size(); i++) {
                newImgs.add(PathUtils.getImagePath(imgs.get(i)));
            }
        }

        if (!StrUtils.empty(place.getTypeInt())) {
            List<NanHaiPlaceCate> cates = placeCateService.getPlaceCates(place.getTypeInt());

            model.put("cates", cates);
            model.put("cateCount", cates == null ? 0 : cates.size());
        }

        model.put("countYuyue", placeSiteService.getCount(id));

        model.put("place", place);
    }

    @RequestMapping
    public void subscribe(Model model, Integer id, Integer type) {

        //时间

        Integer weekInt = 0;
        String weekStr = "";
        Integer dateId = 0;
        // 当天是单还是双
        Integer isSingle = 1;
        System.out.println("--\n--\n--\n--\n--\n--\n" + new Date() +"--\n--\n--\n--\n--\n--\n");
        List<TimeData> timeDatas = new ArrayList<>();
        //Date mondayDate = DateUtils.getFirstWeekDay(new Date()); //周一
        Date sundayDate = DateUtils.getFirstWeekDay(new Date());//周日
        Integer limitDay = 14;
        for (int i = 0; i < limitDay; i++) {
            //Date tmpDate = DateFuncs.addDay(mondayDate, i);
            Date tmpDate = DateFuncs.addDay(sundayDate, i);
            TimeData timeData = new TimeData();
            timeData.setDateId(DateFuncs.toIntDate(tmpDate));
            timeData.setTime(DateFuncs.dateTimeToStr(tmpDate, "MM-dd"));
            int week = i % 7;
            // 判断日期是单周还是双周
            timeData.setIsSingle(DateUtils.isSingleWeek(tmpDate) ? 1 : 0);
            timeData.setWeek(DateUtils.getWeekName(week + 1));
            //20180625 zxc 注释
//            switch (week) {
//                case 0:
//                    week = 6;
//                    break;
//                case 1:
//                    week = 0;
//                    break;
//                case 2:
//                    week = 1;
//                    break;
//                case 3:
//                    week = 2;
//                    break;
//                case 4:
//                    week = 3;
//                    break;
//                case 5:
//                    week = 4;
//                    break;
//                case 6:
//                    week = 5;
//                    break;
//            }
            timeData.setWeekInt(week + 1);
            if (DateFuncs.toIntDate(tmpDate) == DateFuncs.getIntToday()) {
                timeData.setToday(true);
                weekInt = timeData.getWeekInt();
                weekStr = timeData.getWeek();
                dateId = timeData.getDateId();
                isSingle = timeData.getIsSingle();
            }
            timeDatas.add(timeData);
        }
        model.put("times", timeDatas);
        model.put("weekStr", weekStr);
        model.put("dateId", dateId);
        model.put("week", weekInt);
        model.put("isSingle", isSingle);
    }

    @RequestMapping
    public void yuyue(Model model) {
        Integer placeId = 1;
        PlaceParam placeQuery = new PlaceParam();
        placeQuery.setId(placeId);
        NanHaiPlace place = placeService.getPlace(placeQuery);

        if (!StrUtils.empty(place.getTypeInt())) {
            List<NanHaiPlaceCate> cates = placeCateService.getPlaceCate(place.getTypeInt());
            model.put("cates", cates);
            if (!TypeUtils.empty(cates)) {
                model.put("cateId", cates.get(0).getId());
            }
        }
        model.put("placeId", placeId);
    }

    @RequestMapping
    public ActionResult place_list(PlaceParam query) {

        //默认内部场地
        if (query.getPlaceType() == null) query.setPlaceType(NanHaiPlace.PLACE_TYPE_DEPT);

        Integer count = placeService.countPlaces(query);

        RefData refData = new RefData();

        query.setIsAd(1);
        NanHaiPlace place = placeService.getAdPlace(query.getPlaceType(), query.getTypeInt());

        query.setIsAd(0);
        query.setPageIs(true);
        query.setTotalCount(count);
        query.setRowsPerPage(5);

        if (place != null && place.getIsAd() == 0) {
            query.setPagestart(query.getPagestart() + 1);
        }

        List<ListData> placeList = placeService.getIndexList(query);

        refData.put("places", placeList);
        refData.put("page", query.getPages());
        refData.put("place", place);

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult delete(Integer id, Integer type, Integer week) {
        if (type == 0) { //删除预约信息
            placeYuyueService.delteYuyue(id, week);
        } else {  // 取消预约
            placeYuyueService.delteYuyueSe(week, id, 1, null);
        }

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_list(TimeParam query) {
        SelectYuyue selectYuyue = new SelectYuyue();

        List<SelectSite> sites = placeSiteService.getSelectSite(query.getPlaceId(), query.getCateId());
        if (!TypeUtils.empty(sites)) { //如果有场地

            List<SelectTime> selectTimes = placeTimeService.getTime(query.getPlaceId(), query.getCateId(), query.getWeek());

            if (!TypeUtils.empty(selectTimes)) { //如果有可预约时间

                List<SelectTime> newTims = null;
                for (int i = 0; i < sites.size(); i++) {
//                    List<SelectTime> tims = placeTimeService.getTime(sites.get(i).getId(), query.getWeek());
                    List<SelectTime> tims = placeTimeService.getTimes(sites.get(i).getId(), query.getWeek(), query.getIsSingle());
                    newTims = new ArrayList<>();

                    Map<String, SelectTime> timeMap = new HashMap<>();
                    if (!TypeUtils.empty(tims)) {
                        for (SelectTime t : tims) {
                            timeMap.put(t.getStartTime() + "-" + t.getEndTime(), t);
                        }
                    }

                    for (SelectTime t : selectTimes) {
                        SelectTime tt = timeMap.getOrDefault(t.getStartTime() + "-" + t.getEndTime(), null);
                        if (tt == null) {
                            tt = new SelectTime(-1);
                        }
                        newTims.add(tt);
                    }

                    sites.get(i).setTimes(newTims);
                }
            }
            selectYuyue.setTimes(selectTimes);
        }
        selectYuyue.setSites(sites);

        return ActionResult.ok(null, selectYuyue);
    }

    @RequestMapping
    public ActionResult get_time(TimeParam query) {
        List<NanHaiPlaceTime> placeTimes = placeTimeService.getTimes(query);

        return ActionResult.ok(null, placeTimes);
    }

    public static void main(String[] args) {
        Date now = DateUtils.getDayStartTime(new Date());
        Integer nowWeek = DateUtils.getWeek(now);

        Date yuyueDate = DateUtils.formatStr(20170928 + "", "yyyyMMdd");
        System.out.println(yuyueDate.getTime() >= now.getTime());
    }

    // 预约场地
    @RequestMapping
    public ActionResult yuyue_json(String json) {
        String userid = UserSession.getUserid();
        if (StrUtils.empty(json)) {
            return ActionResult.fail("请选择预约时间哦");
        }

        List<NanHaiPlaceYuyue> yuyues = JsonUtils.parseList(json, NanHaiPlaceYuyue.class);

        List<String> errorMsgs = new ArrayList<>();

        for (NanHaiPlaceYuyue yuyue : yuyues) {

            NanHaiPlaceTime time = placeTimeService.getPriceTime(yuyue.getTimeId(), yuyue.getIsSingle());

            if (time != null && time.getTimeType() == 2) { //如果为固定场
                errorMsgs.add(time.toString() + "预约失败：此场地为固定场");
            }

            if (time != null && time.getStatus() != 1) {
                //比较预约时间和现在的时间
                Date now = DateUtils.getDayStartTime(new Date());
                Integer nowWeek = DateUtils.getWeek(now);

                Date yuyueDate = DateUtils.formatStr(yuyue.getDateId() + "", "yyyyMMdd");
                if (yuyueDate.getTime() < now.getTime()) {
                    errorMsgs.add(time.toString() + "预约失败：不能预约已过期的场地");
                } else {
                    if (yuyue.getPayType() == 1) {
                        yuyue.setPayPrice(time.getScore());
                    } else {
                        yuyue.setPayPrice(time.getPrice());
                    }
                    yuyue.setUserid(userid);

                    // 2017-10-10 去除验证
                    //String msg = verifyUtils.verify(yuyue);
                    String msg = null;

                    if (msg == null) {
                        yuyue.setAddTime(new Date());
                        yuyue.setStatus(1);

                        SimpleScore simpleScore = new SimpleScore();
                        simpleScore.setUserid(yuyue.getUserid());
                        //1的状态是获得
                        simpleScore.setStatus(1);

                        //扣除用户积分
                        if (yuyue.getPayType() == 1) {
                            //获取用户的场馆预约积分
                            Float yuyueSum = sumScore(ScoreType.PLACE.getType() + "," + ScoreType.GIVE.getType());
                            if (yuyue.getPayPrice() > yuyueSum) {
                                errorMsgs.add(time.toString() + "预约失败：场馆预约积分不足");
                            } else {
                                placeYuyueService.saveYuyue(yuyue);

                                //更新状态
//                                placeTimeService.updateStatus(time.getId(), 1);
                                placeTimeService.updateStatus(time.getId(), 1, yuyue.getIsSingle());
                                // todo 更新预约数量
                                placeSiteService.updateSiteNumb(yuyue.getSiteId());

                                simpleScore.setScore(-(float) yuyue.getPayPrice().doubleValue());
                                simpleScore.setInfo("场地预约成功，扣除" + yuyue.getPayPrice() + "积分");
                                scoreService.user(simpleScore, ScoreType.PLACE);
                            }
                        } else {  // 现金预约
                            placeYuyueService.saveYuyue(yuyue);
                            placeTimeService.updateStatus(time.getId(), 1, yuyue.getIsSingle());
                            // todo 更新预约数量
                            placeSiteService.updateSiteNumb(yuyue.getSiteId());
                        }
                    } else {
                        errorMsgs.add(time.toString() + "预约失败：" + msg);
                    }
                }
            } else {
                if (time != null && time.getStatus() == 1) {
                    errorMsgs.add(time.toString() + "预约失败：时间段已被预约哦");
                } else {
                    errorMsgs.add("未知预约时间");
                }
            }
        }

        if (errorMsgs.size() > 0) {
            String msg = "";
            for (String s : errorMsgs) {
                msg += s + "\n";
            }
            if (msg.length() > 0) {
                msg = msg.substring(0, msg.length() - 1);
            }
            return ActionResult.error(msg);
        }

        return ActionResult.ok();
    }

    @RequestMapping
    ActionResult pre_list2(Integer id, Integer cateId) {

        YuyueSheet yuyueSheet = new YuyueSheet();

        //获取系统预约列表
        List<YuyueList> yuyueLists = placeYuyueService.getYuyueList(id, cateId);
        Map<String, YuyueList> weekMap = new HashMap<>();
        for (YuyueList yuyueList : yuyueLists) {
            String key = yuyueList.getWeek() + "-" + yuyueList.getStartTime() + "-" + yuyueList.getEndTime() + "-" + yuyueList.getSiteId();
            weekMap.put(key, yuyueList);
        }

        Map<String, Integer> timeIdMap = placeTimeService.getTimeIdMap(id, cateId);

        //获取固定场
        GudingParam query = new GudingParam();
        query.setPlaceId(id);
        query.setStatus(1);
        List<GudingList> gudingLists = placeGudingService.getGudings(query);
        Map<Integer, GudingList> gudingMap = new HashMap<>();
        for (GudingList g : gudingLists) {
            gudingMap.put(g.getTimeId(), g);
        }

        //获取当前场馆下所有的场地
        List<NanHaiPlaceSite> placeSites = placeSiteService.getSites(id, cateId);
        List<Site> sites = new ArrayList<>();
        for (NanHaiPlaceSite site : placeSites) {
            sites.add(new Site(site.getId(), site.getName()));
        }
        yuyueSheet.setSites(sites);

        //获取当前场馆下该分类的全部可预约时间
        List<NanHaiPlaceTime> placeTimes = placeTimeService.getPlaceTimes(id, cateId);
        Map<Integer, List<NanHaiPlaceTime>> timeMap = new HashMap<>();
        for (NanHaiPlaceTime p : placeTimes) {
            List<NanHaiPlaceTime> tmp = timeMap.getOrDefault(p.getWeek(), null);
            if (tmp == null) {
                tmp = new ArrayList<>();
                tmp.add(p);
                timeMap.put(p.getWeek(), tmp);
            } else {
                timeMap.get(p.getWeek()).add(p);
            }
        }

        return ActionResult.ok(null, yuyueSheet);
    }

    @RequestMapping
    public ActionResult pre_list(Integer id, Integer cateId) { //分类ID

        YuyueSheet yuyueSheet = new YuyueSheet();

        //获取系统预约列表
        List<YuyueList> yuyueLists = placeYuyueService.getYuyueList(id, cateId);
        Map<String, YuyueList> weekMap = new HashMap<>();
        for (YuyueList yuyueList : yuyueLists) {
            String key = yuyueList.getWeek() + "-" + yuyueList.getStartTime() + "-" + yuyueList.getEndTime() + "-" + yuyueList.getSiteId();
            weekMap.put(key, yuyueList);
        }

        Map<String, Integer> timeIdMap = placeTimeService.getTimeIdMap(id, cateId);

        //获取固定场
        GudingParam query = new GudingParam();
        query.setPlaceId(id);
        query.setStatus(1);
        List<GudingList> gudingLists = placeGudingService.getGudings(query);
        Map<Integer, GudingList> gudingMap = new HashMap<>();
        for (GudingList g : gudingLists) {
            gudingMap.put(g.getTimeId(), g);
        }

        //获取当前场馆下所有的场地
        List<NanHaiPlaceSite> placeSites = placeSiteService.getSites(id, cateId);
        List<Site> sites = new ArrayList<>();
        for (NanHaiPlaceSite site : placeSites) {
            sites.add(new Site(site.getId(), site.getName()));
        }
        yuyueSheet.setSites(sites);

        //获取当前场馆下该分类的全部可预约时间
        List<NanHaiPlaceTime> placeTimes = placeTimeService.getPlaceTimes(id, cateId);
        Map<Integer, List<NanHaiPlaceTime>> timeMap = new HashMap<>();
        for (NanHaiPlaceTime p : placeTimes) {
            List<NanHaiPlaceTime> tmp = timeMap.getOrDefault(p.getWeek(), null);
            if (tmp == null) {
                tmp = new ArrayList<>();
                tmp.add(p);
                timeMap.put(p.getWeek(), tmp);
            } else {
                timeMap.get(p.getWeek()).add(p);
            }
        }

        if (!TypeUtils.empty(sites)) {
            List<WeekCell> weekCells = new ArrayList<>();
            yuyueSheet.setWeeks(weekCells);

            for (int i = 1; i <= 7; i++) {
                WeekCell weekCell = new WeekCell();
                weekCell.setWeekInt(i);
                weekCell.setWeek(getWeek(i));
                List<TimeCell> timeCells = new ArrayList<>();
                weekCell.setTimes(timeCells);

                List<NanHaiPlaceTime> timeList = getTimes(timeMap.get(i));
                if (!TypeUtils.empty(timeList)) {
                    Collections.sort(timeList, new Comparator<NanHaiPlaceTime>() {
                        @Override
                        public int compare(NanHaiPlaceTime o1, NanHaiPlaceTime o2) {
                            return o1.getStartTime().compareTo(o2.getStartTime());
                        }
                    });

                    for (NanHaiPlaceTime t : timeList) {
                        TimeCell timeCell = new TimeCell();
                        timeCell.setTime(t.getStartTime() + "-" + t.getEndTime());

                        List<ItemCell> itemCells = new ArrayList<>();
                        for (Site s : sites) {
                            String key = t.getWeek() + "-" + t.getStartTime() + "-" + t.getEndTime() + "-" + s.getId();
                            Integer timeId = timeIdMap.get(key);

                            YuyueList yuyueList = weekMap.get(key);
                            ItemCell itemCell = new ItemCell();

                            GudingList tmpGuding = gudingMap.get(timeId);
                            if (tmpGuding == null) {
                                if (yuyueList != null && yuyueList.getStatus().intValue() != 4) {
                                    itemCell.setMobile(yuyueList.getMobile());
                                    itemCell.setName(yuyueList.getName());
                                    if (yuyueList.getStatus() == 1) {
                                        itemCell.setStatus("未使用");
                                    } else if (yuyueList.getStatus() == 2) {
                                        itemCell.setStatus("已使用");
                                    } else {
                                        itemCell.setStatus("已失效");
                                    }
                                    itemCell.setUsername(yuyueList.getUsername());
                                    if (yuyueList.getPayType() == 1) {
                                        itemCell.setPayType("积分");
                                    } else {
                                        itemCell.setPayType("现金");
                                    }
                                } else {
                                    itemCell.setStatus("未预约");
                                }
                            } else {
                                itemCell.setMobile(tmpGuding.getMobile());
                                itemCell.setUsername(tmpGuding.getUsername());
                                itemCell.setName(tmpGuding.getDeptname());
                                itemCell.setStatus("固定场");
                                itemCell.setPayType("固定场");
                            }
                            itemCells.add(itemCell);
                        }
                        timeCell.setCells(itemCells);
                        timeCells.add(timeCell);
                    }
                }
                weekCells.add(weekCell);
                yuyueSheet.setWeeks(weekCells);
            }
        }
        System.out.print(yuyueSheet);
        return ActionResult.ok(null, yuyueSheet);
    }

    private String getWeek(int i) {
        if (i == 1) {
            return "星期一";
        } else if (i == 2) {
            return "星期二";
        } else if (i == 3) {
            return "星期三";
        } else if (i == 4) {
            return "星期四";
        } else if (i == 5) {
            return "星期五";
        } else if (i == 6) {
            return "星期六";
        } else if (i == 7) {
            return "星期日";
        }
        return "";
    }

    private List<NanHaiPlaceTime> getTimes(List<NanHaiPlaceTime> times) {

        Map<String, NanHaiPlaceTime> map = new HashMap<>();
        if (times != null)
            for (NanHaiPlaceTime t : times) {
                map.put(t.getStartTime() + "-" + t.getEndTime(), t);
            }

        List<NanHaiPlaceTime> tmp = new ArrayList<>();
        for (Map.Entry<String, NanHaiPlaceTime> entry : map.entrySet()) {
            tmp.add(entry.getValue());
        }
        return tmp;
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


}

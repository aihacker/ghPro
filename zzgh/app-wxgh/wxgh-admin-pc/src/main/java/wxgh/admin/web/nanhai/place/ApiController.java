package wxgh.admin.web.nanhai.place;

import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.DateUtils;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import pub.web.ServletUtils;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.excel.NanHaiYuyueExportApi;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.sys.utils.place.PlaceTansferUtils;
import wxgh.data.entertain.place.GudingList;
import wxgh.data.entertain.place.yuyue.YuyueDetail;
import wxgh.data.place.YuyueInfo;
import wxgh.data.pub.NameValue;
import wxgh.data.pub.push.Push;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.entertain.nanhai.place.*;
import wxgh.entity.entertain.place.*;
import wxgh.entity.pub.SysFile;
import wxgh.entity.pub.user.UserDept;
import wxgh.param.entertain.place.*;
import wxgh.param.pub.file.FileParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.query.time.TimeQuery;
import wxgh.sys.service.weixin.entertain.nanhai.place.*;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class ApiController {

    @Autowired
    private NanHaiYuyueExportApi yuyueApi;

    /**
     * 场馆项目列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult catelist(PlaceCateParam param) {
        List<NanHaiPlaceCate> placeCateList = placeCateService.getList(param);

        for (NanHaiPlaceCate placeCate : placeCateList) {
            if (placeCate.getImgPath() != null) {
                String sql = "select file_path from t_sys_file where file_id = ?";
                String img = pubDao.query(String.class, sql, placeCate.getImgPath());
                placeCate.setImgPath(PathUtils.getImagePath(img));
            }
        }
        return ActionResult.okAdmin(placeCateList, param);
    }

    /**
     * 项目删除
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult catedel(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            pubDao.execute(SQL.deleteByIds("nanhai_place_cate", i));
        }
        return ActionResult.ok();
    }

    /**
     * 场馆项目新增
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult cateadd(NanHaiPlaceCate param) {
        if (param.getImgPath() == null) {
            return ActionResult.error("请上传图标");
        }
        SQL sql = new SQL.SqlBuilder()
                .field("name,info,status,img_path")
                .value(":name,:info,:status,:imgPath")
                .insert("nanhai_place_cate")
                .build();
        pubDao.insertAndGetKey(sql.sql(), param);
        return ActionResult.ok();
    }

    /**
     * 场馆项目修改
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult catechange(NanHaiPlaceCate param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nanhai_place_cate").update().where("id = :id");
        if (param.getName() != null) {
            sql.set("name = :name");
        }
        if (param.getInfo() != null) {
            sql.set("info = :info");
        }
        if (param.getStatus() != null) {
            sql.set("status = :status");
        }
        if (param.getImgPath() != null) {
            sql.set("img_path = :imgPath");
        }


        pubDao.executeBean(sql.build().sql(), param);
        return ActionResult.ok();
    }

    /**
     * 场馆列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult placelist(PlaceParam param) {
        param.setPageIs(true);

        List<NanHaiPlace> list = placeService.getPlaces(param);

        return ActionResult.okAdmin(list, param);
    }

    /**
     * 删除场馆
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult placedel(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            pubDao.execute(SQL.deleteByIds("nanhai_place", i));
        }
        return ActionResult.ok();
    }

    /**
     * 预约列表
     *
     * @param query
     * @return
     */
    @RequestMapping
    public ActionResult yuyuelist(PlaceParam query) {
        if (query.getStatus() == null) {
            query.setStatus(1);
        }
        query.setPageIs(true);
        query.setRowsPerPage(7);

        SQL.SqlBuilder sql = new SQL.SqlBuilder().field("y.*,s.`name` AS siteName,u.mobile, u.`name` AS userName," +
                "t.`start_time` AS startTime, t.`end_time` AS endTime, t.`week` AS week," +
                "pc.name AS cateName," +
                "p.`title` AS placeTitle")
                .table("nanhai_place_yuyue y")
                .join("nanhai_place_site s", "y.site_id=s.id")
                .join("user u", "y.userid = u.userid")
                .join("nanhai_place_time t", "t.id=y.time_id")
                .join("nanhai_place_cate pc", "pc.id=s.cate_id")
                .join("nanhai_place p", "p.id=s.place_id")
                .where("y.status = :status");

        List<NanHaiPlaceYuyue> list = pubDao.queryList(sql.build().sql(), query, NanHaiPlaceYuyue.class);//placeTimeService.getYuYueList(query);

        return ActionResult.okAdmin(list, query);
    }

    /**
     * 删除预约
     *
     * @param id
     * @param notify
     * @return
     */
    @RequestMapping
    public ActionResult yuyuedel(Integer id, Boolean notify) {
        YuyueDetail yuyue = placeYuyueService.getYuyueDetail(id);
        if (yuyue != null) {
            if (yuyue.getStatus() == 3) {
                return ActionResult.error("预约已失效，无法取消");
            } else if (yuyue.getStatus() == 4) {
                return ActionResult.error("预约已被取消");
            }

            //更新预约状态
            placeYuyueService.updateStatus(id, 4);

            //更新预约时间ID
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .update("nanhai_place_time t")
                    .set("t.status = 0")
                    .where("id = ?");
            pubDao.execute(sql.build().sql(), yuyue.getTimeId());

            //如果付款方式为积分，则回退积分给用户
            if (yuyue.getPayType() == 1) {
                /*if (yuyue.getPayType().equals(1)) {
                    SimpleScore simpleScore = new SimpleScore();
                    simpleScore.setUserid(yuyue.getUserid());
                    //1的状态是获得
                    simpleScore.setStatus(1);
                    simpleScore.setScore((float) yuyue.getPayPrice().doubleValue());
                    simpleScore.setInfo("管理员取消预约，返回" + yuyue.getPayPrice() + "积分");
                    scoreService.user(simpleScore, ScoreType.PLACE);
                }*/
            }

            if (notify != null && notify) {
              /*  String str = "会员你好，你的预约已被取消";
                Text text = new Text(str);
                Message<Text> message = new Message<Text>(WeixinAgent.NANHAI_AGENT_YUYUE, text);
                if (yuyue.getUserid() != null) {
                    message.addUser(yuyue.getUserid());
                } else {
                    return ActionResult.error("出错了，没有预约用户ID！");
                }
                try {
                    MsgApi.send(message);
                } catch (WeixinException e) {
                    e.printStackTrace();
                }*/
                ReplyPush push = new ReplyPush(yuyue.getUserid(), 2);
                push.setHost(ServletUtils.getHostAddr());
                push.setAgentId(WeixinAgent.NANHAI_AGENT_YUYUE);
                push.setMsg("场馆预约已被管理员取消");
                weixinPush.reply(push);
            }
        } else {
            return ActionResult.error("预约不存在");
        }
        return ActionResult.ok();
    }

    /**
     * 导出预约列表
     *
     * @param query
     * @param response
     */
    @RequestMapping(value = "/admin/nanhai/place/yuyue/export.html")
    public void export(PlaceParam query, HttpServletResponse response) {
        if (query.getStartWeek() != null && query.getEndWeek() != null) {
            query.setWeek(true);
        }
        if (query.getId() != null && query.getId() == -1) {
            query.setId(null);
        }
        NanHaiYuyueExportApi yuyueExportApi = new NanHaiYuyueExportApi();
        List<NanHaiPlaceYuyue> list = placeYuyueService.getList(query);
        yuyueExportApi.toExcel(list);
        yuyueExportApi.response(response);
    }

    /**
     * 场馆信息更新
     *
     * @param place
     * @return
     */
    @RequestMapping
    public ActionResult placeedit(NanHaiPlace place) {
        ActionResult actionResult = ActionResult.ok();
        String typeName = "";
        //if(!com.libs.common.type.TypeUtils.empty(place.getTypeInt())){
        String[] typeInts = place.getTypeInt().split(",");
        for (int i = 0; i < typeInts.length; i++) {
            NanHaiPlaceCate placeCate = placeCateService.getCate(Integer.valueOf(typeInts[i]));
            if (i == typeInts.length - 1) {
                typeName = typeName + placeCate.getName();
            } else {
                typeName = typeName + placeCate.getName() + ",";
            }
        }
        place.setTypeName(typeName);
        //}

        Integer integer = placeService.updatePlace(place);
        return actionResult;
    }

    /**
     * 获取场地
     *
     * @param placeSiteQuery
     * @return
     */
    @RequestMapping
    public ActionResult getplacesite(PlaceSiteParam placeSiteQuery) {
        ActionResult actionResult = ActionResult.ok();
        actionResult.setData(placeSiteService.getData(placeSiteQuery));
        return actionResult;
    }

    /**
     * 添加场地
     *
     * @param placeSite
     * @return
     */
    @RequestMapping
    public ActionResult addplacecate(NanHaiPlaceSite placeSite) {
        ActionResult actionResult = ActionResult.ok();
        if (placeSite.getId() == null) {
            placeSite.setNumb(0);
            placeSiteService.save(placeSite);
        } else {
            placeSiteService.updateSite(placeSite);
        }
        return actionResult;
    }

    /**
     * 删除场地
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult delplacesite(Integer id) {
        ActionResult actionResult = ActionResult.ok();
        placeSiteService.del(id);
        return actionResult;
    }

    /**
     * 列出场馆图片
     *
     * @param placeImgQuery
     * @return
     */
    @RequestMapping
    public ActionResult getplaceimg(PlaceImgParam placeImgQuery) {
        ActionResult actionResult = ActionResult.ok();

       /* String sql = new SQL.SqlBuilder()
                .table("nanhai_place_img")
                .where("place_id = ?")
                .build().sql();

        List<NanHaiPlaceImg> imgs = pubDao.queryList(NanHaiPlaceImg.class, sql, placeImgQuery.getPlaceId());*/

        String sql = "SELECT p.*,f.file_path as path FROM t_nanhai_place_img p JOIN t_sys_file f on p.img_path = f.file_id WHERE p.place_id = :placeId";
        List<NanHaiPlaceImg> imgs = pubDao.queryList(sql,placeImgQuery,NanHaiPlaceImg.class);
        for(NanHaiPlaceImg img:imgs){
            img.setPath(PathUtils.getImagePath(img.getPath()));
        }

        actionResult.setData(imgs);
        return actionResult;
    }

    /**
     * 新增场馆图片
     *
     * @param placeImg
     * @return
     */
    @RequestMapping
    public ActionResult addplaceimg(NanHaiPlaceImg placeImg) {
        //, @RequestParam("placeImg") MultipartFile file
        /*if (!file.isEmpty()) {
            SysFile sysFile = fileApi.addFile(file, new FileParam());

            placeImg.setAddTime(new Date());
            placeImg.setImgPath(sysFile.getFilePath());
            placeImg.setThumbPath(sysFile.getThumbPath());
            placeImg.setStatus(1);
            placeImg.setId(placeimgService.addImg(placeImg));
        }*/
        placeImg.setAddTime(new Date());
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nanhai_place_img")
                .field("img_Path,place_id,add_time")
                .value(":imgPath,:placeId,:addTime")
                .insert();
        String sql2 = sql.build().sql();
        pubDao.executeBean(sql.build().sql(),placeImg);
        return ActionResult.okWithData(placeImg);
    }

    /**
     * 删除场馆图片
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult delplaceimg(Integer id) {
        String i = id.toString();
        ActionResult actionResult = ActionResult.ok();
        pubDao.execute(SQL.deleteByIds("nanhai_place_img", i));
        return actionResult;
    }

    /**
     * 获取场地排班
     *
     * @param timeQuery
     * @return
     */
    @RequestMapping
    public ActionResult getplacetime(TimeQuery timeQuery) {
        ActionResult actionResult = new ActionResult();
        RefData refData = new RefData();
       /* if (timeQuery.getWeekId() == null)
            timeQuery.setWeekId(1);

        // 获取本周 true单  false双
        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();
        // 1 为本周 2 位下周
        if (!timeQuery.getWeekId().equals(1))
            isSingle = !isSingle;

        String field = isSingle ? "t.d_status" : "t.s_status";
        field += " as status";*/

        for (int i = 1; i < 8; i++) {
            timeQuery.setWeek(i);
            SQL.SqlBuilder sql = new SQL.SqlBuilder().table("nanhai_place_time t")
                    .field("t.*")
                    //.field(field)
                    .order("start_time");
            if (timeQuery.getCateId() != null) {
                sql.where("t.cate_id = :cateId");
            }
            if (timeQuery.getSiteId() != null) {
                sql.where("t.site_id = :siteId");
            }
            if (timeQuery.getWeek() != null) {
                sql.where("t.week= :week");
            }
            List<NanHaiPlaceTime> placeTime = pubDao.queryList(sql.build().sql(), timeQuery, NanHaiPlaceTime.class);
            refData.put(String.valueOf(i), placeTime);
        }
        actionResult.setData(refData);
        return actionResult;
    }

    /**
     * 添加场地时间排班
     *
     * @param placeTime
     * @return
     */
    @RequestMapping
    public ActionResult addplacetime(NanHaiPlaceTime placeTime) {
        Date startTime = DateUtils.formatStr(placeTime.getStartTime(), "HH:mm");
        Date endTime = DateUtils.formatStr(placeTime.getEndTime(), "HH:mm");
        int res = startTime.compareTo(endTime);
        if (startTime != null && endTime != null) {
            if (res == 0) {
                return ActionResult.error("开始时间不能等于结束时间");
            } else if (res == 1) {
                return ActionResult.error("开始时间不能大于结束时间");
            }
        } else {
            return ActionResult.error("开始时间或结束时间不能为空");
        }
        placeTime.setStatus(0);
        placeTime.setTimeType(1);
//        String sql = "insert into t_place_time (status,cate_id,site_id,start_time,end_time,week,time_type)" +
//                "values (0,:cateId,:siteId,:startTime,:endTime,:week,:timeType)";
        String sql = "insert into t_nanhai_place_time (d_status,s_status,cate_id,site_id,start_time,end_time,week,time_type)" +
                "values (0,0,:cateId,:siteId,:startTime,:endTime,:week,:timeType)";
        pubDao.executeBean(sql, placeTime);
        return ActionResult.ok();
    }

    /**
     * 删除场地时间排班
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult delplacetime(Integer id) {
        //placeTimeService.del(id);
        ActionResult actionResult = ActionResult.ok();
        String i = id.toString();
        pubDao.execute(SQL.deleteByIds("nanhai_place_time",i));
        return actionResult;
    }

    /**
     * 固定场列表
     *
     * @param query
     * @return
     */
    @RequestMapping
    public ActionResult listguding(GudingParam query) {
        List<GudingList> lists = placeGudingService.getGudings(query);
        return ActionResult.ok(null, lists);
    }

    /**
     * 列出场地排班
     *
     * @param timeQuery
     * @return
     */
    @RequestMapping
    public ActionResult listtime(TimeParam timeQuery) {
        List<NanHaiPlaceTime> timeList = placeTimeService.getTimes(timeQuery);
        return ActionResult.ok(null, timeList);
    }

    /**
     * 新增固定场
     *
     * @param guding
     * @return
     */
    @RequestMapping
    public ActionResult addguding(NanHaiPlaceGuding guding) {
        if (guding.getTimeId() == null) {
            return ActionResult.error("请选择固定场时间段哦");
        }

        //判断当前时间是否已经是固定场？
        NanHaiPlaceGuding tmp = placeGudingService.getOne(guding.getTimeId());
        if (tmp != null) {
            return ActionResult.error("该时间段已经是固定场哦：" + tmp.toRemark());
        }

        if (guding.getUserType() == 1) { //系统用户
            if (StrUtils.empty(guding.getUserid())) {
                return ActionResult.error("请先选择用户哦");
            }

            String sql = "select u.*,d.name as deptname " +
                    "from t_user u LEFT JOIN t_dept d on u.deptid = d.id " +
                    "where u.userid = :userid";
            UserDept userDept = pubDao.query(sql, guding, UserDept.class);
            if (userDept != null) {
                guding.setUsername(userDept.getName());
                guding.setDeptname(userDept.getDeptname());
                guding.setMobile(userDept.getMobile());
            } else {
                return ActionResult.error("该用户不是系统用户");
            }
        }

        guding.setStatus(1);
        placeGudingService.save(guding);
        return ActionResult.ok();
    }

    /**
     * 更新固定场
     *
     * @param guding
     * @return
     */
    @RequestMapping
    public ActionResult editguding(NanHaiPlaceGuding guding) {
        if (guding.getStatus() != null) {
            Integer status = placeGudingService.getStatus(guding.getId());
            if (status == 0) {
                guding.setStatus(1);
            } else {
                guding.setStatus(0);
            }
        }
        placeGudingService.edit(guding);
        return ActionResult.ok(null, guding.getStatus());
    }

    /**
     * 删除固定场
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult delguding(Integer id) {
        placeGudingService.del(id);
        return ActionResult.ok();
    }

    /**
     * 通过ID获取固定场次信息
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult get_guding_by_id(Integer id) {
        ActionResult result = ActionResult.ok();
        NanHaiPlaceGuding placeGuding = placeGudingService.getOne(id);
        result.setData(placeGuding);
        return result;
    }

    @RequestMapping
    public ActionResult get_yuyue_by_id(Integer timeId) {
        //Integer week,
        ActionResult result = ActionResult.ok();
        //week,
        YuyueInfo yuyueInfo = placeYuyueService.getYuyueInfoById(timeId);
        result.setData(yuyueInfo);
        return result;
    }

    /**
     * 通过ID修改状态
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult change_status_by_id(Integer id, Integer state) {
        //Integer week,
        placeTimeService.changeStatusById(id, state);
        //week,
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult cancel_state(Integer timeId, String reason) {
        YuyueInfo yuyueInfo = placeYuyueService.getYuyueInfoById(timeId);
        //4是管理员取消状态
        placeYuyueService.updateStatus(yuyueInfo.getId(), 4);
        //3是管理员取消状态
        placeTimeService.updateStatus(yuyueInfo.getTimeId(), 3);
        //1是支付类型为积分
        if (yuyueInfo.getPayType().equals(1)) {
            SimpleScore simpleScore = new SimpleScore();
            simpleScore.setUserid(yuyueInfo.getUserid());
            //1的状态是获得
            simpleScore.setStatus(1);
            simpleScore.setScore((float) yuyueInfo.getPayPrice().doubleValue());
            simpleScore.setInfo("管理员取消预约，返回" + yuyueInfo.getPayPrice() + "积分");
            scoreService.user(simpleScore, ScoreType.PLACE);
        }
        String str = yuyueInfo.getUsername() + "会员你好，你的预约已被取消，取消的理由是" + reason;
        Text text = new Text(str);
        Message<Text> message = new Message<Text>(WeixinAgent.NANHAI_AGENT_YUYUE, text);
        if (yuyueInfo.getUserid() != null) {
            message.addUser(yuyueInfo.getUserid());
        } else {
            return ActionResult.error("出错了，没有预约用户ID！");
        }
        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            e.printStackTrace();
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult list_cate(Integer placeId) {
        List<NameValue> cates = placeService.listCate(placeId);
        return ActionResult.okWithData(cates);
    }

    /**
     * 第二版本接口
     *
     * @param place
     * @param timeId 1 上午 2 下午 3 晚上
     *               时间段 上午 0 - 12 下午 12 - 18 晚上 18 -24
     * @return
     */
    @RequestMapping
    public ActionResult close_v2(NanHaiPlace place, String timeId) {

        if (com.libs.common.type.TypeUtils.empty(timeId))
            return ActionResult.error("请选择时间段");
        if (place.getDate() == null)
            return ActionResult.error("请选择日期");

        Date closeDate = DateUtils.formatStr(place.getDate(), "yyyy-MM-dd");
        // 要停场的为单周还是双周
        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek(closeDate);
        // 获取当期日期为星期几
        place.setWeek(DateUtils.getWeek(closeDate));

        // 获取指定日期的指定时间段场地
        List<NanHaiPlaceTime> places = placeTimeService.getWeekTimeList(place.getWeek(), place.getId(), place.getType(), timeId);
        // 场地ids
        List<Integer> placeIds = new LinkedList<>();
        for (NanHaiPlaceTime placeTime : places) {
            placeIds.add(placeTime.getId());
        }
        // 更新场馆信息
        placeTimeService.updatePlaceTimeClose(TypeUtils.listToStr(placeIds), isSingle, DateUtils.formatDate(closeDate, "yyyyMMdd"));

        // 获取场馆信息
        PlaceParam placeParam = new PlaceParam();
        placeParam.setId(place.getId());
        NanHaiPlace place1 = placeService.getPlace(placeParam);
        NanHaiPlaceCate placeCate = placeCateService.getCate(place.getType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n场地：" + place1.getTitle() + placeCate.getName() + "\n");
        stringBuilder.append("时间：" + place.getDate() + " (" + PlaceTansferUtils.week(place.getWeek()) + ") " + PlaceTansferUtils.time(timeId) + "\n");
        stringBuilder.append("原因：" + place.getReason());

        // 测试
//        Push push = new Push();
//        List<String> tousers = new ArrayList<>();
//        tousers.add("15902064445");
//        push.setTousers(tousers);
//        push.setAgentId(WeixinAgent.AGENT_YUYUE);
//        pushAsync.sendByPlaceClose(stringBuilder.toString(), push);

        // 推送
        pushAsync.sendByPlaceClose(stringBuilder.toString(), Push.all(WeixinAgent.NANHAI_AGENT_YUYUE));

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult close(NanHaiPlace place) {
        StringBuilder sb = new StringBuilder();
        sb.append("因" + place.getReason() + ",");
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nanhai_place t")
                .field("t.title");
        if (place.getId() != -1) {
            sql.where("t.id = :id");
        }
        List<NanHaiPlace> places = pubDao.queryList(sql.select().build().sql(), place, NanHaiPlace.class);
        for (NanHaiPlace t : places) {
            sb.append(t.getTitle() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);

        if (place.getType() == null || place.getType() == -1) {
            sb.append("全部");
        } else {
            String sql2 = "select name from t_nanhai_place_cate where id = :type";
            sb.append("的" + pubDao.query(sql2, place, NanHaiPlaceCate.class).getName());
        }
        sb.append("场地将于" + place.getDate() + "全面关闭，请周知");


        SQL.SqlBuilder sql3 = new SQL.SqlBuilder()
                .table("nanhai_place_time t");
        if (place.getId() != -1) {
            sql3.where("s.place_id = :id")
                    .join("nanhai_place_site s", "s.id = t.site_id");
            if (place.getType() != -1) {
                sql3.where("t.cate_id = :type");
            }
        }
        Date ed = DateUtils.formatStr(place.getDate(), "yyyy-MM-dd");
        place.setWeek(DateUtils.getWeek(ed));
        sql3.where("t.week = :week").field("t.id as id");
        List<NanHaiPlaceTime> placeTimes = pubDao.queryList(sql3.select().build().sql(), place, NanHaiPlaceTime.class);

        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();
        String sql4 = isSingle ? "update t_nanhai_place_time set d_status = 4 where id = ?" : "update t_nanhai_place_time set s_status = 4 where id = ?";

        if (placeTimes.size() > 0) {
            for (NanHaiPlaceTime p : placeTimes) {
                pubDao.execute(sql4, p.getId());
            }
        }
        pushAsync.sendByPlaceClose(sb.toString(), Push.all(WeixinAgent.NANHAI_AGENT_YUYUE));
        return ActionResult.ok();
    }

    /**
     * 取消用户预约
     *
     * @return week, 1 本周 2 下周
     */
    @RequestMapping
    public ActionResult cancel_yuyue(Integer week, Integer timeId, String reason) {
        placeYuyueService.delteYuyueSe(week, timeId, 2, reason);
        return ActionResult.ok();
    }

    @Autowired
    private NanHaiPlaceService placeService;

    @Autowired
    private PubDao pubDao;

    @Autowired
    private NanHaiPlaceCateService placeCateService;

    @Autowired
    private NanHaiPlaceYuyueService placeYuyueService;

    @Autowired
    private NanHaiPlaceSiteService placeSiteService;

    @Autowired
    private NanHaiPlaceimgService placeimgService;

    @Autowired
    private NanHaiPlaceGudingService placeGudingService;

    @Autowired
    private NanHaiPlaceTimeService placeTimeService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private PushAsync pushAsync;

    @Autowired
    private FileApi fileApi;
}

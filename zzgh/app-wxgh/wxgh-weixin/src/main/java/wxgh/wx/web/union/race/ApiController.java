package wxgh.wx.web.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.race.Race;
import wxgh.entity.union.race.RaceJoin;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.param.union.race.RaceQuery;
import wxgh.sys.service.weixin.union.race.RaceJoinService;
import wxgh.sys.service.weixin.union.race.RaceService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-27  11:08
 * --------------------------------------------------------- *
 */
@Controller
public class ApiController {

    /**
     * 获取正在报名和进行中的活动，不包含已结束的活动
     *
     * @param query
     * @return
     */
    @RequestMapping
    public ActionResult list(RaceQuery query) {
        String today = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");

        query.setPageIs(true);
        query.setToday(today);

        System.out.println("racequery:"+query.toString());
        Integer count = raceService.getCount(query);
        query.setTotalCount(count);

        List<Race> races = raceService.getRaces(query);

//        RefData refData = new RefData();
//        refData.put("races", races);
//        refData.put("total", query.getPages());
//        refData.put("current", query.getCurrentPage());

//        return ActionResult.ok(null, refData);
        return ActionResult.okRefresh(races,query);
    }

    @RequestMapping
    public ActionResult join(Integer id, String userid) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        Race race = raceService.getRace(id);
        if (race == null) {
            return ActionResult.error("比赛已被取消哦");
        }

        //判断是否选择队友
        if (race.getRaceType() == 2) {
            if (StrUtils.empty(userid)) {
                return ActionResult.fail("比赛为双打，请选择一个队友哦");
            }

            if (user.getUserid().equals(userid)) {
                return ActionResult.fail("不能选择自己作为队友哦");
            }
        }

        Date today = new Date();
        if (!race.getEntryTime().after(today)) {
            if (race.getStartTime().before(today) && race.getEndTime().after(today)) {
                return ActionResult.error("比赛正在进行中，无法报名");
            } else {
                return ActionResult.error("比赛已结束哦");
            }
        }

        //判断用户是否报名
        boolean join = raceJoinService.isJoin(user.getUserid(), id);
        if (join) {
            return ActionResult.error("你已报名哦");
        }
        //判断队友是否报名
        if (race.getRaceType() == 2) {
            join = raceJoinService.isJoin(userid, id);
            if (join) {
                return ActionResult.error("你的队友已报名哦");
            }
        }

        if (race.getLimitNum() != -1) {
            RaceJoinQuery query = new RaceJoinQuery();
            query.setRaceId(id);
            Integer joinCount = raceJoinService.coutJoin(query);

            if (joinCount >= race.getLimitNum()) {
                return ActionResult.error("报名名额已满哦");
            }
        }

        RaceJoin raceJoin = new RaceJoin();
        if (race.getRaceType() == 2) {
            raceJoin.setUserid(user.getUserid() + "," + userid);
        } else {
            raceJoin.setUserid(user.getUserid());
        }
        raceJoin.setStatus(1);
        raceJoin.setRaceId(id);
        raceJoin.setJoinTime(today);

        raceJoinService.save(raceJoin);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(Race race) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户，尝试重新进入该页面哦");
        }
        if (!StrUtils.empty(race.getImg())) {
            fileApi.wxDownload(race.getImg(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    race.setImg(file.getFilePath());
                }
            });

//                File file = PathUtils.getUpload(PathUtils.PATH_ACTIVITIES, "", race.getImg(), true);
//                downWxImgTask.syncDownLoadImage(race.getImg(), file.getAbsolutePath());
//                race.setImg(PathUtils.getUploadPath(file));
        }

        race.setUserid(user.getUserid());
        race.setStatus(1);
        race.setDeptid(user.getDeptid());
        race.setBianpaiIs(0);
        raceService.save(race);

        return ActionResult.ok();
    }

    @Autowired
    private FileApi fileApi;

    @Autowired
    private RaceJoinService raceJoinService;

    @Autowired
    private RaceService raceService;

}

package wxgh.wx.web.union.race.score;


import com.google.common.reflect.TypeToken;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.DateUtils;
import wxgh.data.union.race.ArrangeData;
import wxgh.data.union.race.ArrangeUser;
import wxgh.entity.union.race.*;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.param.union.race.RaceQuery;
import wxgh.param.union.race.score.config.MatchScoreConst;
import wxgh.param.union.race.score.result.EditInfo;
import wxgh.param.union.race.score.result.RaceResult;
import wxgh.param.union.race.score.result.ResultDb;
import wxgh.sys.service.weixin.union.race.*;

import java.lang.reflect.Type;
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
 * @Date 2017-10-27  14:34
 * --------------------------------------------------------- *
 */
@Controller
public class ApiController {

    // list --------------------------------------
    @RequestMapping
    public ActionResult list(RaceQuery query) {
        String today = DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");

        if (query.getBianpaiIs() == null) {
            query.setBianpaiIs(1);
        }

        query.setNoEnd(true);
        query.setPageIs(true);
        query.setToday(today);

        Integer count = raceService.getCount(query);
        query.setTotalCount(count);

        List<Race> races = raceService.getRaces(query);

        RefData refData = new RefData();
        refData.put("races", races);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    // match2 --------------------------------
    @RequestMapping
    public ActionResult userlist2(Integer id) {
        RaceJoinQuery joinQuery = new RaceJoinQuery();
        joinQuery.setRaceId(id);
        List<RaceJoin> raceJoins = raceJoinService.getJoinsAndName(joinQuery);
        return ActionResult.ok(null, raceJoins);
    }

    @RequestMapping
    public ActionResult start2(RaceArrange arrange, Integer rule) { //开始计分，保存编排
        if (arrange.getId() == null) {
            arrange.setArrangeType(RaceArrange.ARRANGE_TYPE_RANDOM);

            //保存编排
            Integer arrangeId = raceArrangeService.saveOrUpdate(arrange);

            RaceScore raceScore = new RaceScore();
            raceScore.setRaceId(arrange.getRaceId());
            raceScore.setLunNum(arrange.getLunNum());
            raceScore.setArrangeId(arrangeId);
            raceScore.setRule(rule);
            raceScoreService.startScore(raceScore);

            return ActionResult.ok(null, arrangeId);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult info2(String user1, String user2, Integer lun, Integer raceId, Integer type) {
        RaceScore raceScore = raceScoreService.getScoreByArrange(user1, user2, lun, raceId, RaceArrange.ARRANGE_TYPE_RANDOM);

        if (raceScore == null) {
            return ActionResult.ok();
        }
        if (type == 2) { //双打比赛
            String score1 = raceScoreService.getUserIdsScore(user1, raceScore.getArrangeId(), lun);
            String score2 = raceScoreService.getUserIdsScore(user2, raceScore.getArrangeId(), lun);
            raceScore.setScore1(score1);
            raceScore.setScore2(score2);
        }
        return ActionResult.ok(null, raceScore);
    }

    // match ---------------------------------------

    /**
     * 获取编排列表
     */
    @RequestMapping
    public ActionResult userlist(Integer id) {
        List<ArrangeData> raceArranges = raceArrangeService.getArrangeAndLun(id, null);
        return ActionResult.ok(null, raceArranges);
    }

    /**
     * 开始计分
     *
     * @param raceScore
     * @return
     */
    @RequestMapping
    public ActionResult start(RaceScore raceScore) {
        raceScoreService.startScore(raceScore);
        return ActionResult.ok();
    }

    /**
     * 结束该轮比赛
     *
     * @param arrangeId
     * @return
     */
    @RequestMapping
    public ActionResult end(Integer arrangeId, Integer lunNum) {

        RaceScore raceScore = raceScoreService.getScore(arrangeId, lunNum);

        if (raceScore != null) {
            //只有某人分数达到指定分数后，才能结束计分
            if (raceScore.getRival1Score() >= raceScore.getRule() || raceScore.getRival2Score() >= raceScore.getRule()) {
                raceArrangeService.updateStatus(raceScore.getArrangeId(), RaceArrange.STATUS_END);
                raceScoreService.updateEnd(raceScore.getArrangeId(), raceScore.getLunNum(), 1);

                //修改个人积分
                ArrangeUser arrangeUser = raceArrangeService.getArrangeUser(arrangeId);
                if (arrangeUser != null) {
                    List<RaceUserScore> scores = new ArrayList<>();

                    String[] rival1s = arrangeUser.getRival1().split(",");
                    String[] rival2s = arrangeUser.getRival2().split(",");

                    if (raceScore.getRival1Score() > raceScore.getRival2Score()) {
                        for (String userid : rival1s) {
                            scores.add(getScore(userid, MatchScoreConst.WIN, raceScore));
                        }
                        for (String userid : rival2s) {
                            scores.add(getScore(userid, MatchScoreConst.SHU, raceScore));
                        }
                    } else if (raceScore.getRival1Score() < raceScore.getRival2Score()) {
                        for (String userid : rival1s) {
                            scores.add(getScore(userid, MatchScoreConst.SHU, raceScore));
                        }
                        for (String userid : rival2s) {
                            scores.add(getScore(userid, MatchScoreConst.WIN, raceScore));
                        }
                    }
                    raceUserScoreService.addScores(scores);
                }
            } else {
                return ActionResult.error("请按照比赛规则结束计分哦");
            }
        }
        return ActionResult.ok();
    }

    private RaceUserScore getScore(String userid, Float score, RaceScore raceScore) {
        RaceUserScore userScore = new RaceUserScore();
        userScore.setAddTime(new Date());
        userScore.setArrangeId(raceScore.getArrangeId());
        userScore.setRaceId(raceScore.getRaceId());
        userScore.setLunNum(raceScore.getLunNum());
        userScore.setUserid(userid);
        userScore.setScore(score);
        return userScore;
    }

    /**
     * 添加一条比赛记录
     *
     * @param detail
     * @return
     */
    @RequestMapping
    public ActionResult jilu(RaceScoreDetail detail, Integer raceId, Integer rule) {
        detail.setAddTime(new Date());
        raceScoreDetailService.save(detail, raceId, rule);
        return ActionResult.ok();
    }

    /**
     * 获取编排比赛信息，进行到第几轮
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult info(Integer id, String userid1, String userid2, Integer type) {
        RaceScore lastScore = raceScoreService.getLastScore(id);
        if (lastScore == null) {
            return ActionResult.ok();
        }
        if (type == 2) { //双打比赛
            String score1 = raceScoreService.getUserIdsScore(userid1, id, lastScore.getLunNum());
            String score2 = raceScoreService.getUserIdsScore(userid2, id, lastScore.getLunNum());
            lastScore.setScore1(score1);
            lastScore.setScore2(score2);
        }
        return ActionResult.ok(null, lastScore);
    }

    // result -------------------------------------
    @RequestMapping
    public ActionResult edit(String json) {
        Type type = new TypeToken<ArrayList<EditInfo>>() {
        }.getType();
        List<EditInfo> editInfos = JsonUtils.parseList(json, EditInfo.class);
        raceScoreService.editScores(editInfos);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult all_list(Integer id) {
        //获取全部的比赛计分记录
        List<ResultDb> results = raceScoreService.getRaceResults(id);
        Map<Integer, List<ResultDb>> resultMap = new LinkedHashMap<>();
        for (ResultDb r : results) {
            List<ResultDb> tmp = resultMap.get(r.getArrangeId());
            if (tmp == null) {
                List<ResultDb> rs = new ArrayList<>();
                rs.add(r);
                resultMap.put(r.getArrangeId(), rs);
            } else {
                resultMap.get(r.getArrangeId()).add(r);
            }
        }
        results.clear();

        List<RaceResult> raceResults = new ArrayList<>();

        Integer arrangeType = null;

        //获取全部的编排名单
        List<RaceArrange> arranges = raceArrangeService.getArranges(id, null, 0);
        if (!TypeUtils.empty(arranges)) {//如果比赛是编排方式
            Race race = raceService.getRace(id);

            for (RaceArrange a : arranges) {
                RaceResult r = new RaceResult();

                r.setJia(a.getName1().replace(",", "&"));
                r.setYi(a.getName2().replace(",", "&"));
                r.setSiteNum(getRaceType(race.getRaceType()) + a.getOrderNum());
                if (a.getArrangeType() == 2) {
                    r.setRemark(a.getRemark());
                } else {
                    r.setRemark("第" + a.getLunNum() + "轮");
                }
                r.setId(a.getId());
                List<ResultDb> tmps = resultMap.get(a.getId());

                setScore(r, tmps);

                raceResults.add(r);
                if (arrangeType == null) {
                    arrangeType = a.getArrangeType();
                }
            }
        } else {
            for (Map.Entry<Integer, List<ResultDb>> entry : resultMap.entrySet()) {
                List<ResultDb> resultDbs = entry.getValue();
                ResultDb tmp = resultDbs.get(0);
                RaceResult r = new RaceResult();
                r.setJia(tmp.getName1().replace(",", "&"));
                r.setYi(tmp.getName2().replace(",", "&"));
                r.setSiteNum(getRaceType(tmp.getCateType()) + tmp.getOrderNum());
                if (tmp.getArrangeType() == 2) {
                    r.setRemark(tmp.getRemark());
                } else {
                    r.setRemark("第" + tmp.getLun() + "轮");
                }
                r.setId(tmp.getArrangeId());
                setScore(r, resultDbs);

                raceResults.add(r);
                if (arrangeType == null) {
                    arrangeType = tmp.getArrangeType();
                }
            }
        }

        RefData refData = new RefData();
        refData.put("results", raceResults);
        refData.put("type", arrangeType);
        return ActionResult.ok(null, refData);
    }

    private void setScore(RaceResult r, List<ResultDb> tmps) {
        if (TypeUtils.empty(tmps)) {
            r.setScore1("0:0");
            r.setScore2("0:0");
            r.setScore3("0:0");
            r.setTotalScore("0:0");
        } else {
            Map<Integer, ResultDb> lunMap = new HashMap<>();
            for (ResultDb db : tmps) {
                lunMap.put(db.getLunNum(), db);
            }
            tmps.clear();

            r.setScore1(getScore(1, lunMap));
            r.setScore2(getScore(2, lunMap));
            r.setScore3(getScore(3, lunMap));
            r.setTotalScore(getTotalScore(r));
        }
    }

    private String getScore(Integer lun, Map<Integer, ResultDb> map) {
        ResultDb d = map.get(lun);
        if (d == null) {
            return "0:0";
        }
        return ((int) (float) d.getRival1Score()) + ":" + ((int) (float) d.getRival2Score());
    }

    private String getTotalScore(RaceResult r) {
        String[] score1 = r.getScore1().split(":");
        String[] score2 = r.getScore2().split(":");
        String[] score3 = r.getScore3().split(":");
        int a1 = 0, a2 = 0;
        if (score1[0].compareTo(score1[1]) > 0) {
            a1++;
        } else if (score1[0].compareTo(score1[1]) < 0) {
            a2++;
        }
        if (score2[0].compareTo(score2[1]) > 0) {
            a1++;
        } else if (score2[0].compareTo(score2[1]) < 0) {
            a2++;
        }
        if (score3[0].compareTo(score3[1]) > 0) {
            a1++;
        } else if (score3[0].compareTo(score3[1]) < 0) {
            a2++;
        }

        return a1 + ":" + a2;
    }

    private String getRaceType(Integer type) {
        if (type == 1) {
            return "羽毛球";
        } else if (type == 2) {
            return "乒乓球";
        } else if (type == 3) {
            return "篮球";
        } else if (type == 4) {
            return "网球";
        } else {
            return "未知";
        }
    }

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceJoinService raceJoinService;

    @Autowired
    private RaceArrangeService raceArrangeService;

    @Autowired
    private RaceScoreService raceScoreService;

    @Autowired
    private RaceScoreDetailService raceScoreDetailService;

    @Autowired
    private RaceUserScoreService raceUserScoreService;

}

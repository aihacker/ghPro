package wxgh.app.web.tv;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.web.tv.api.HttpApi;
import wxgh.app.web.tv.api.TVConfig;
import wxgh.data.entertain.tv.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2017/11/28
 * time：11:28
 * version：V1.0
 * Description：
 */
@Controller
public class MainController {

    @RequestMapping
    public void event(Model model) {
        List<LiveEvent> events = HttpApi.getLiveEvent();
        if (!TypeUtils.empty(events)) {
            for (int i = 0; i < events.size(); i++) {
                Date date = new Date(events.get(i).getStartTime());
                events.get(i).setTimeStr(DateUtils.formatDate(date));
                String str = events.get(i).getProjectName();
                events.get(i).setProjectName(str.substring(0,str.length()-2));
            }
            //全部赛事
            List<EventInfo> eventInfos = new ArrayList<>();
            List<Integer> matchs = new ArrayList<>();
            for (LiveEvent liveEvent : events) {
                EventInfo eventInfo = HttpApi.getEventInfo(liveEvent.getCode());
                eventInfos.add(eventInfo);
                matchs.add(eventInfo.getMatchId());

            }
            model.put("eventList", eventInfos);
            model.put("matchs", matchs);
        }
        model.put("events", events);
    }

    @RequestMapping
    public void query(Model model, Integer code) {
        EventInfo eventInfo = HttpApi.getEventInfo(code);
        eventInfo.setTimeStr(DateUtils.formatDate(new Date()));
        model.put("event", eventInfo);
    }

    @RequestMapping
    public ActionResult score(Integer matchId) {
        List<EventScore> eventScores = HttpApi.getEventScore(matchId);
        return ActionResult.ok(null, eventScores);
    }

    @RequestMapping
    public void allevent(Model model) {
//        List<RaceEvent> events = HttpApi.getEvents();
//        if (!TypeUtils.empty(events)) {
//            for (int i = 0; i < events.size(); i++) {
//                Date date = new Date(events.get(i).getCreateTime());
//                events.get(i).setTimeStr(DateUtils.formatDate(date));
//            }
//        }
//        model.put("events", events);
    }


    @RequestMapping
    public ActionResult allEvent(Integer status){
        List<RaceEvent> events = HttpApi.getEvents(status);
        if (!TypeUtils.empty(events)) {
            for (int i = 0; i < events.size(); i++) {
                Date date = new Date(events.get(i).getCreateTime());
                events.get(i).setTimeStr(DateUtils.formatDate(date));
            }
        }
        return ActionResult.ok(null, events);
    }

    @RequestMapping
    public void tevent(){

    }

    @RequestMapping
    public ActionResult todayEvent(Integer status){
        List<SpaceEvent> spaceEvents = HttpApi.getTodayEvent(status);
        return ActionResult.ok(null, spaceEvents);
    }

    @RequestMapping
    public void show(Model model, Integer id, Integer species) {
        StageResult result1 = HttpApi.getStageResult(id, 1); //第一阶段
        StageResult result2 = HttpApi.getStageResult(id, 2); //第二阶段
        StageResult result3 = HttpApi.getStageResult(id, 3); //第一阶段
        StageResult result4 = HttpApi.getStageResult(id, 4); //第二阶段
        boolean hasOne = false;
        boolean hasTwo = false;
        boolean hasThree = false;
        boolean hasFour = false;
        if (result1 != null && result1.getStage().getCourtCount() != null) {
            hasOne = true;
            Stage stage = result1.getStage();
            model.put("stateId1", stage.getId());
        }
        if (result2 != null && result2.getStage().getCourtCount() != null) {
            hasTwo = true;
            Stage stage = result2.getStage();
            model.put("stateId2", stage.getId());
        }
        if (result3 != null && result3.getStage() != null) {
            hasThree = true;
            Stage stage = result3.getStage();
            model.put("stateId3", stage.getId());
            model.put("cEventId1",stage.getEventId());
        }
        if (result4 != null && result4.getStage() != null) {
            hasFour = true;
            Stage stage = result4.getStage();
            model.put("stateId4", stage.getId());
            model.put("cEventId2",stage.getEventId());
        }
        model.put("HOST", TVConfig.HOST);
        model.put("hasOne", hasOne);
        model.put("hasTwo", hasTwo);
        model.put("hasThree", hasThree);
        model.put("hasFour", hasFour);
        model.put("species", species);
    }

    @RequestMapping
    public void team(Model model, Integer id) {
        List<EventTeam> stages = HttpApi.getTeams(id);
        model.put("stages", stages);
    }

    @RequestMapping
    public void team_info(Model model, Integer eventId, Integer id) {
        List<TeamInfo> teamInfos = HttpApi.getTeamInfo(eventId, id);
        if (!TypeUtils.empty(teamInfos)) {
            for (int i = 0, len = teamInfos.size(); i < len; i++) {
                TeamInfo temp = teamInfos.get(i);
                String type = temp.getType();
                if (!StrUtils.empty(type)) {
                    teamInfos.get(i).setType(TeamInfo.TYPES[Integer.valueOf(type)]);
                } else {
                    teamInfos.get(i).setType("未知身份");
                }
                if (!StrUtils.empty(temp.getAge())) {
                    teamInfos.get(i).setAge(temp.getAge() + "岁");
                }
            }
        }
        model.put("teams", teamInfos);
    }

}

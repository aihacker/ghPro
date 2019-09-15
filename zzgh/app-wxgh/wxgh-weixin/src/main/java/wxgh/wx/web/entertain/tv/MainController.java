package wxgh.wx.web.entertain.tv;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.utils.tv.HttpApi;
import wxgh.app.utils.tv.TVConfig;
import wxgh.data.entertain.tv.*;

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
        List<RaceEvent> events = HttpApi.getEvents();
        if (!TypeUtils.empty(events)) {
            for (int i = 0; i < events.size(); i++) {
                Date date = new Date(events.get(i).getCreateTime());
                events.get(i).setTimeStr(DateUtils.formatDate(date));
            }
        }
        model.put("events", events);
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        StageResult result1 = HttpApi.getStageResult(id, 1); //第一阶段
        StageResult result2 = HttpApi.getStageResult(id, 2); //第二阶段
        boolean hasOne = false;
        boolean hasTwo = false;
        if (result1 != null && result1.getStage() != null) {
            hasOne = true;
            Stage stage = result1.getStage();
            model.put("stateId1", stage.getId());
        }
        if (result2 != null && result2.getStage() != null) {
            hasTwo = true;
            Stage stage = result2.getStage();
            model.put("stateId2", stage.getId());
        }
        model.put("HOST", TVConfig.HOST);
        model.put("hasOne", hasOne);
        model.put("hasTwo", hasTwo);
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

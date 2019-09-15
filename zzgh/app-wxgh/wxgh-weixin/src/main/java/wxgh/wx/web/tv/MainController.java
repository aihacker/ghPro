package wxgh.wx.web.tv;


import com.fasterxml.jackson.databind.JavaType;
import com.libs.common.json.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.data.tv.EventTeam;
import wxgh.data.tv.RaceEvent;
import wxgh.data.tv.Result;
import wxgh.data.tv.Stage;
import wxgh.data.tv.StageResult;
import wxgh.data.tv.TeamInfo;
import wxgh.sys.service.weixin.tv.TVConfig;
import wxgh.sys.service.weixin.tv.api.HttpApi;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author Ape
 * --------------------------------------------------------- *
 * @Date 2017-11-02  11:47
 * --------------------------------------------------------- *
 */
@Controller
public class MainController {

    @RequestMapping
    public void event(Model model) {
        List<RaceEvent> events = HttpApi.getEvents();
        if (!TypeUtils.empty(events)) {
            for (int i = 0; i < events.size(); i++) {
                if(events.get(i) != null){
                    if(events.get(i).getCreateTime() != null){
                        Date date = new Date(events.get(i).getCreateTime());
                        events.get(i).setTimeStr(DateUtils.formatDate(date));
                    }
                }
            }
        }
        model.put("events", events);
    }

    @RequestMapping
    public void show(Model model, Integer id){
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

    public static void main(String[] args) throws IOException {
        String json = "{\"ok\":true,\"msg\":null,\"data\":[{\"id\":8864,\"name\":\"魏小强\",\"teamId\":2968,\"eventId\":258,\"phone\":null,\"type\":2,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8866,\"name\":\"傅青松\",\"teamId\":2968,\"eventId\":258,\"phone\":null,\"type\":3,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8868,\"name\":\"倪津泉\",\"teamId\":2968,\"eventId\":258,\"phone\":\"18929191818\",\"type\":1,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8870,\"name\":\"尚德玺\",\"teamId\":2968,\"eventId\":258,\"phone\":\"15802817388\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8872,\"name\":\"李强\",\"teamId\":2968,\"eventId\":258,\"phone\":\"13684054119\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8874,\"name\":\"胡嘉欣\",\"teamId\":2968,\"eventId\":258,\"phone\":\"15818230480\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":0,\"remark\":null},{\"id\":8876,\"name\":\"李玲\",\"teamId\":2968,\"eventId\":258,\"phone\":\"13377690075\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":0,\"remark\":null},{\"id\":8878,\"name\":\"杜庆锋\",\"teamId\":2968,\"eventId\":258,\"phone\":\"13377698244\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8880,\"name\":\"刘双\",\"teamId\":2968,\"eventId\":258,\"phone\":\"18938169139\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":0,\"remark\":null},{\"id\":8882,\"name\":\"李海\",\"teamId\":2968,\"eventId\":258,\"phone\":\"18512390966\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null},{\"id\":8884,\"name\":\"陈佩红\",\"teamId\":2968,\"eventId\":258,\"phone\":\"13652616211\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":0,\"remark\":null},{\"id\":8886,\"name\":\"谢惠强\",\"teamId\":2968,\"eventId\":258,\"phone\":\"13635402791\",\"type\":0,\"unit\":null,\"age\":null,\"sex\":1,\"remark\":null}],\"errors\":{}};";
//        Result<List<TeamInfo>> listResult = (Result<List<TeamInfo>>) JsonUtils.parse(json, Result.class, List.class, TeamInfo.class);
        JavaType javaType = JsonUtils.getCollectionType(List.class, TeamInfo.class);
        JavaType mtype = JsonUtils.getParseJson().getTypeFactory().constructParametricType(Result.class, javaType);
        Result<List<TeamInfo>> r = JsonUtils.getParseJson().readValue(json, mtype);
        System.out.println(r.getData().get(0).getId());
    }

}

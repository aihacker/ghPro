package wxgh.app.utils.tv;

import com.fasterxml.jackson.databind.JavaType;
import com.libs.common.http.HttpClient;
import com.libs.common.http.HttpException;
import com.libs.common.json.JsonUtils;
import pub.utils.StrUtils;
import wxgh.data.entertain.tv.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */
public class HttpApi {

    /**
     * 获取赛事列表
     *
     * @return
     */
    public static List<RaceEvent> getEvents() {
        try {
            String url = TVConfig.getUrl(TVConfig.QUERY_EVENT);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)) {
                EventResult<RaceEvent> eventResult = (EventResult<RaceEvent>) JsonUtils.parse(result, EventResult.class, RaceEvent.class);
                return eventResult.getRows();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取赛事参赛队伍
     *
     * @param eventId
     * @return
     */
    public static List<EventTeam> getTeams(int eventId) {
        try {
            String url = TVConfig.getUrl(TVConfig.QUERY_STAGE);
            url = String.format(url, eventId);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)) {
                EventResult<EventTeam> eventResult = (EventResult<EventTeam>) JsonUtils.parse(result, EventResult.class, EventTeam.class);
                return eventResult.getRows();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TeamInfo> getTeamInfo(int eventId, int teamId) {
        try {
            String url = TVConfig.getUrl(TVConfig.QUERY_TEAM);
            url = String.format(url, eventId, teamId);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)) {
                JavaType javaType = JsonUtils.getCollectionType(List.class, TeamInfo.class);
                JavaType mtype = JsonUtils.getParseJson().getTypeFactory().constructParametricType(Result.class, javaType);
                Result<List<TeamInfo>> listResult = null;
                try {
                    listResult = JsonUtils.getParseJson().readValue(result, mtype);
                } catch (IOException e) {
                    return null;
                }
//            Result<List<TeamInfo>> listResult = (Result<List<TeamInfo>>) JsonUtils.parse(result, Result.class, List.class, TeamInfo.class);
                return listResult.getData();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取赛事阶段详情
     *
     * @param eventId
     * @param stageNo
     * @return
     */
    public static StageResult getStageResult(int eventId, int stageNo) {
        try {
            String url = TVConfig.getUrl(TVConfig.QUERY_STAGE_DETAILS);
            url = String.format(url, eventId, stageNo);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)) {
                return JsonUtils.parseBean(result, StageResult.class);
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package wxgh.app.web.tv.api;

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
     * 获取正在直播的赛事
     * @return
     */
    public static List<LiveEvent> getLiveEvent(){
        try{
            String url = TVConfig.getUrl(TVConfig.QUERY_LIVE_EVENT);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)){
                List<LiveEvent> liveEvents = (List<LiveEvent>) JsonUtils.parse(result, List.class, LiveEvent.class);
                return liveEvents;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取直播赛事的详情
     * @return
     */
    public static EventInfo getEventInfo(Integer code){
        try{
            String url = TVConfig.getUrl(TVConfig.QUERY_EVENT_DETAILS);
            url = String.format(url, code);
            String result = HttpClient.get(url);
            if(!StrUtils.empty(result)){
                return JsonUtils.parseBean(result,EventInfo.class);
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取赛事的比分详情
     * @return
     */
    public static List<EventScore> getEventScore(Integer matchId) {
        try{
            String url = TVConfig.getUrl(TVConfig.QUERY_SCORE);
            url = String.format(url, matchId);
            String result = HttpClient.get(url);
            if(!StrUtils.empty(result)){
                List<EventScore> eventScores = (List<EventScore>) JsonUtils.parse(result, List.class, EventScore.class);
                return eventScores;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<RaceEvent> getEvents(Integer species) {
        //获取赛事列表
        try {
            String url = TVConfig.getUrl2(TVConfig.QUERY_EVENT);
            url = String.format(url, species);
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

    public static List<EventTeam> getTeams(int eventId) {
        //获取赛事参赛队伍
        try {
            String url = TVConfig.getUrl2(TVConfig.QUERY_STAGE);
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
            String url = TVConfig.getUrl2(TVConfig.QUERY_TEAM);
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

    public static StageResult getStageResult(int eventId, int stageNo) {
        //获取赛事阶段详情
        try {
            String url = TVConfig.getUrl2(TVConfig.QUERY_STAGE_DETAILS);
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

    public static List<SpaceEvent> getTodayEvent(int venueId){
        //根据场地获取今日赛事
        try {
            String url = TVConfig.getUrl(TVConfig.QUERY_TODAY_EVENT);
            url = String.format(url, venueId);
            String result = HttpClient.get(url);
            if (!StrUtils.empty(result)) {
                List<SpaceEvent> spaceEvents = (List<SpaceEvent>) JsonUtils.parse(result,List.class, SpaceEvent.class);
                return spaceEvents;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

}

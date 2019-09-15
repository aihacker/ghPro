package wxgh.app.web.tv.api;

/**
 * Created by Administrator on 2017/6/6.
 */
public class TVConfig {

    //public static final String HOST = "http://14.29.115.235:8081";
    public static final String HOST = "http://zncg.fsecity.com:8081";
    public static final String APP_NAME = "imatch";
    public static final String API_URL = HOST + "/" + APP_NAME + "/app/tv";

    //请求地址
    public static final String QUERY_LIVE_EVENT = "event.json"; //获得直播的所有比赛
    public static final String QUERY_EVENT_DETAILS = "query.json?code=%d"; //获得比赛的详情
    public static final String QUERY_SCORE = "score.json?match_id=%d"; //获得比赛的比分详情
    public static final String QUERY_TODAY_EVENT = "query_today_event.json?venueId=%d"; //获取今天赛事

    public static final String APP_NAME2 = "ivenue";
    public static final String API_URL2 = HOST + "/" + APP_NAME2 + "/api/wx";
    //请求地址
    public static final String QUERY_EVENT = "query_event.json?species=%d"; //获取所有赛事
    public static final String QUERY_STAGE = "query_stage.json?eventId=%d"; //获取参赛队伍
    public static final String QUERY_STAGE_DETAILS = "query_stage_details.json?eventId=%d&stageNo=%d"; //获取编排详情
    public static final String QUERY_TEAM = "query_team_players.json?eventId=%d&teamId=%d";

    /**
     * 获取API请求地址
     *
     * @param path
     * @return
     */
    public static String getUrl(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(API_URL);
        builder.append("/");
        builder.append(path);
        return builder.toString();
    }

    public static String getUrl2(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(API_URL2);
        builder.append("/");
        builder.append(path);
        return builder.toString();
    }

}

package wxgh.sys.service.weixin.tv;

/**
 * Created by Administrator on 2017/6/6.
 */
public class TVConfig {

    //public static final String HOST = "http://14.29.115.235:8081";
    public static final String HOST = "http://zncg.fsecity.com:8081";
    public static final String APP_NAME = "ivenue";
    public static final String API_URL = HOST + "/" + APP_NAME + "/api/wx";

    //请求地址
    public static final String QUERY_EVENT = "query_event.json"; //获取所有赛事
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
}

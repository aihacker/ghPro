package wxgh.app.sys.schedule;

/**
 * Created by Administrator on 2017/7/26.
 */
public enum JobGroup {

    ACT_WEEK("act_schedule", "定期活动"), //定期活动
    ACT_PUSH("act_push", "活动提醒"), //活动提醒推送
    EXAM_PUSH("exam_push", "考试提醒"), // 考试提醒
    EXAM_JOIN_PUSH("exam_join_push", "考试报名提醒"), //考试报名提醒推送
    PLACE_WEEK("place_expire_schedule", "场馆预约失效"), // 场馆预约失效定时
    PARTY_ARTICLE_PUSH("party_article_push", "党建文章定时推送"),  // 党建文章推送
    TRIBE_RESULT_PUSH("tribe_result_push", "青年部落定时推送"),
    NEWS_PUSH("news_push", "新闻频道定时推送"),
    SPORT_ACT("sport_act", "健步活动");

    private String type;
    private String name;

    JobGroup(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}

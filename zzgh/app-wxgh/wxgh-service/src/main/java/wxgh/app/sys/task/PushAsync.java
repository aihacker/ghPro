package wxgh.app.sys.task;


import wxgh.data.pub.push.Push;

/**
 * Created by Administrator on 2017/7/19.
 */
public interface PushAsync {

    /**
     * 推送活动消息
     *
     * @param actId
     * @param push
     */
    void send_act(String actId, Push push);

    /**
     * 场馆预约 停场推送
     *
     * @param text
     * @param push
     */
    void sendByPlaceClose(String text, Push push);


    /**
     * 魅美影像推送
     *
     * @param id
     * @param push
     */

    void sendByGarden(Integer id, Push push);/**
     * 支部园地推送
     *
     * @param id
     * @param push
     */
    void sendBySheYing(Integer id, Push push);



    /**
     * 总经理直通车转发
     *
     * @param userid
     * @param id
     * @param push
     */
    void sendByPartySugTran(String userid, Integer id, Push push);

    /**
     * 总经理直通车@
     *
     * @param
     * @param id
     * @param push
     */
    void sendByPartySugAite(Integer id, Push push);

    /**
     * 会员提案 转发
     *
     * @param userid
     * @param id
     * @param push
     */
    void sendBySuggestTran(String userid, Integer id, Push push);

    /**
     * 热点论坛 推送
     *
     * @param ids
     */
    void sendArticle(Integer ids);

    /**
     * 大型活动 推送
     *
     * @param ids
     */
    void sendActBig(Integer ids);
}
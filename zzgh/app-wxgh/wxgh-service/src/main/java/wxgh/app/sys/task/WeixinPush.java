package wxgh.app.sys.task;

import wxgh.data.entertain.sport.push.PushList;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.pub.push.ReplyPush;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public interface WeixinPush {

    /**
     * 推送审核消息给管理员
     *
     * @param push
     */
    void apply(ApplyPush push);

    /**
     * 推送审核消息给特定管理员
     *
     * @param push
     */
    void apply_to(ApplyPush push);

    /**
     * 管理员回复审核结果
     *
     * @param push
     */
    void reply(ReplyPush push);

    /**
     * 活动推送
     *
     * @param actId 活动ID
     * @param jobId
     * @param all   是否推送给全部用户
     */
    void act(String actId, String jobId, boolean all);

    void act(String actId, String jobId, boolean all, Integer agentId);

    /**
     * 活动成果推送
     *
     * @param pushType 推送人员（1协会成员，2全体人员）
     * @param resultId 活动成果ID
     */
    void act_result(Integer pushType, Integer resultId);

    /**
     * 党建支部活动成果推送
     *
     * @param pushType 推送人员（1支部成员，2全体人员）
     * @param resultId 活动成果ID
     */
    void party_act_result(Integer pushType, Integer resultId);

    /**
     * 意见征集推送
     * @param type
     * @param opinionId
     */
    void party_opinion(Integer type, String groupIds, Integer opinionId, String title);

    /**
     * 问卷调查推送
     * @param type
     * @param examId
     */
    void party_judge(Integer type, String groupIds, Integer examId, String title);

    /**
     * 明主问卷调查推送
     *
     * @param examId
     */
    void manage_judge(Integer examId, String title);

    /**
     * 直通车推送
     *
     * @param actId
     */
    void manage_act(Integer actId, String title);

    /**
     * 纪检考试发布推送
     *
     * @param examId
     */
    void di_exam(Integer examId);

    void di_exam_party(Integer examId);

    void bbs(Integer bbsId);

    /**
     * 青年部落成果推送
     *
     * @param ids
     */
    void tribe_result(List<Integer> ids, Integer safe);

    /**
     * 投票推文
     *
     * @param voteId
     */
    void vote(Integer voteId);

    /**
     * 公会宣传推送
     *
     * @param ids
     */

    void publicity(String ids);//已测试成功

    /**
     * 女工园地（课堂）
     *
     * @param id
     */
    void womanTeach(Integer id);

    /**
     * 支部活动推送
     *
     * @param actid
     */
    void partyAct(Integer actid);

    /**
     * 支部通知
     *
     * @param id
     */
    void partyNotice(Integer id);

    void sportToday(PushList push, Integer dateId);
    void pubInfoToUser(String content,Integer isParty,List<String> zhiBuUserList,List<String> dangWuUserList);

    /**
     * 台账通知
     *
     */
    void submitAccount(String news);

    /**
     * 饭堂活动成果推送
     *
     * @param pushType 推送人员（1饭堂成员，2全体人员）
     * @param resultId 活动成果ID
     */
    void act_canteen_result(Integer pushType, Integer resultId);
}

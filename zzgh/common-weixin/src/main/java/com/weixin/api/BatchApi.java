package com.weixin.api;

import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.batch.SyncWork;
import com.weixin.bean.result.batch.SyncResult;
import com.weixin.bean.result.batch.WorkResult;
import com.weixin.utils.WeixinHttp;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class BatchApi {

    /**
     * 增量更新成员
     *
     * @param work
     * @return
     * @throws WeixinException
     */
    public static String syncuser(SyncWork work) throws WeixinException {
        String url = Weixin.getContactURL("batch/syncuser");
        SyncResult result = WeixinHttp.post(url, work, SyncResult.class);
        return result.getJobid();
    }

    /**
     * 全量覆盖成员
     *
     * @param work
     * @return
     * @throws WeixinException
     */
    public static String replaceuser(SyncWork work) throws WeixinException {
        String url = Weixin.getContactURL("batch/replaceuser");
        SyncResult result = WeixinHttp.post(url, work, SyncResult.class);
        return result.getJobid();
    }

    /**
     * 全量覆盖部门
     *
     * @param work
     * @return
     * @throws WeixinException
     */
    public static String replaceparty(SyncWork work) throws WeixinException {
        String url = Weixin.getContactURL("batch/replaceparty");
        SyncResult result = WeixinHttp.post(url, work, SyncResult.class);
        return result.getJobid();
    }

    /**
     * 获取异步任务结果
     *
     * @param jobid 异步任务jobId
     * @return
     * @throws WeixinException
     */
    public static WorkResult get_user_result(String jobid) throws WeixinException {
        String url = Weixin.getContactURL("batch/getresult?jobid=%s", jobid);
        WorkResult result = WeixinHttp.get(url, WorkResult.class);
        return result;
    }

}

package wxgh.app.sys.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pub.functions.DateFuncs;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.entertain.sport.push.PushList;
import wxgh.sys.service.weixin.entertain.sport.SportPushService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：9:47
 * version：V1.0
 * Description：
 */
@Lazy(false)
@Component
@EnableScheduling
public class SportQuartz {

    /**
     * 每天晚上22点开始进行推送
     */
    @Scheduled(cron = "0 0 22 * * ?")
    public void push_sport() {
        Integer dateid = DateFuncs.getIntToday();
        List<PushList> users = sportPushService.listPush(dateid);
        for (PushList user : users) {
            weixinPush.sportToday(user, dateid);
        }
    }

    @Autowired
    private SportPushService sportPushService;

    @Autowired
    private WeixinPush weixinPush;

}

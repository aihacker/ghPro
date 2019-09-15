package wxgh.app.web.api;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.task.SportAsync;
import wxgh.data.entertain.sport.history.HistRecord;
import wxgh.data.entertain.sport.save.UserSport;
import wxgh.entity.pub.user.ApiUser;
import wxgh.sys.service.weixin.entertain.sport.SportService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SportController {

    /**
     * 保存步数记录
     *
     * @param map
     * @param createTime
     * @return
     */
    @RequestMapping
    public ActionResult save(@RequestBody Map<String, UserSport> map, Date createTime) {
        if (map != null) {
            sportAsync.saveSport(map, createTime);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    //获取指定日期后的全部23:59:59的数据
    public ActionResult history(Integer dateId, Integer count) {
        if (dateId == null) {
            dateId = Integer.valueOf(DateUtils.toDayStr(count == null ? -6 : count, "yyyyMMdd"));
        }
        List<HistRecord> histRecords = sportService.history_record(dateId);
        return ActionResult.ok(null, histRecords);
    }

    @RequestMapping
    public ActionResult user() {
        Map<String, ApiUser> map = userService.mapUser();
        return ActionResult.ok(null, map);
    }

    @Autowired
    private SportAsync sportAsync;

    @Autowired
    private SportService sportService;

    @Autowired
    private UserService userService;
}


package wxgh.wx.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.union.group.account.ActList;
import wxgh.data.union.group.act.UserList;
import wxgh.entity.entertain.act.ActJoin;
import wxgh.param.union.group.account.ActParam;
import wxgh.param.union.group.act.UserParam;
import wxgh.sys.service.weixin.union.group.act.AccountService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
@Controller
public class AccountController {

    /**
     * 结算时，列出协会成员列表
     *
     * @param id 活动ID
     */
    @RequestMapping
    public void users(Integer id) {
    }

    /**
     * 获取待结算的活动
     *
     * @param id 协会ID
     */
    @RequestMapping
    public void acts(Integer id) {

    }

    /**
     * 更新用户参与类型
     *
     * @param join
     * @return
     */
    @RequestMapping
    public ActionResult edit(ActJoin join) {
        accountService.editJoinType(join);
        return ActionResult.ok();
    }

    /**
     * 活动积分结算
     *
     * @param total 活动总费用
     * @param id    活动ID
     * @return
     */
    @RequestMapping
    public ActionResult start(Float total, Integer id) {
        accountService.addOutJoins(id); //新增缺席用户

        accountService.account(id, total);

        return ActionResult.ok();
    }

    /**
     * 获取指定活动报名参加人员
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list_user(UserParam param) {
        List<UserList> users = accountService.getUserList(param); //获取参加用户
        if (param.getPageIs()) {
            return ActionResult.okRefresh(users, param);
        }
        return ActionResult.okWithData(users);
    }

    @RequestMapping
    public ActionResult list_act(ActParam param) {
        param.setPageIs(true);
        List<ActList> acts = accountService.listAct(param);
        return ActionResult.okRefresh(acts, param);
    }

    @Autowired
    private AccountService accountService;

}

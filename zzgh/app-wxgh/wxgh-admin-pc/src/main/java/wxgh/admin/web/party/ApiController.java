package wxgh.admin.web.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import wxgh.entity.entertain.place.User;
import wxgh.entity.party.party.PartyUser;
import wxgh.param.party.PartyParam;
import wxgh.sys.service.admin.party.PartyUserService;

import java.util.List;

/**
 * Created by cby on 2017/8/15.
 */
@Controller
public class ApiController {

    @Autowired
    private PartyUserService partyUserService;

    @RequestMapping
    public ActionResult get(PartyParam param){
        Integer count = partyUserService.getCount(param);
        param.setPageIs(true);
        param.setTotalCount(count);
        List<PartyUser> list = partyUserService.getList(param);

        if (!StrUtils.empty(param.getSearchKey())) {
            param.setSearchKey("%" + param.getSearchKey() + "%");
        }
        return ActionResult.okAdmin(list,param);
    }

    @RequestMapping
    public ActionResult add(PartyUser partyUser) {

        if (StrUtils.empty(partyUser.getUserId())) {
            return ActionResult.error("请选择用户哦");
        }
        if (partyUser.getGroupId() == null) {
            //return ActionResult.error("请选择所属党支部");
        }

        if (partyUserService.hasAddUser(partyUser.getUserId(), partyUser.getGroupId())) {
            return ActionResult.error("你已经进入该党组织哦");
        }

        PartyUser partyUser1 = new PartyUser();
        partyUser1.setUserId(partyUser.getUserId());
        partyUser1.setPassword("123456");
        partyUser1.setScores(0);
        partyUser1.setGroupId(partyUser.getGroupId());
        partyUser1.setNativePlace(partyUser.getNativePlace());
        partyUser1.setNation(partyUser.getNation());
        partyUser1.setSex(partyUser.getSex());
        partyUser1.setBirthday(DateUtils.formatDate(partyUser.getBirthday(), "yyyy-MM-dd"));
        partyUser1.setIdentificationNum(partyUser.getIdentificationNum());
        partyUser1.setInDate(DateUtils.formatDate(partyUser.getInDate(), "yyyy-MM-dd"));
        partyUser1.setIsRepublican(partyUser.getIsRepublican());
        partyUser1.setZhuanzhengDate(DateUtils.formatDate(partyUser.getZhuanzhengDate(), "yyyy-MM-dd"));
        partyUser1.setEducation(partyUser.getEducation());
        partyUser1.setCompany(partyUser.getCompany());
        partyUser1.setWorkDate(DateUtils.formatDate(partyUser.getWorkDate(), "yyyy-MM-dd"));
        partyUser1.setRetiredDate(DateUtils.formatDate(partyUser.getRetiredDate(), "yyyy-MM-dd"));
        partyUser1.setFamilyPlace(partyUser.getFamilyPlace());
        partyUser1.setCcpdepart(partyUser.getCcpdepart());
        partyUser1.setPosition(partyUser.getPosition());
        partyUser1.setIsNew(partyUser.getIsNew());
        partyUser1.setRemark(partyUser.getRemark());

        partyUserService.addUser(partyUser1);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult search(String name) {
        RefData refData = new RefData();
        List<User> users = partyUserService.searchUser(name);
        refData.put("users",users);
        return ActionResult.ok(null,refData);
    }
}

package wxgh.admin.web.party.judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.model.Model;
import wxgh.admin.session.AdminSession;
import wxgh.data.party.member.PartyDept;
import wxgh.sys.service.admin.party.member.PartyDeptService;
import wxgh.sys.service.admin.party.opinion.PartyOpinionService;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PartyOpinionService partyOpinionService;

    @Autowired
    private PartyDeptService partyDeptService;

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void list(){

    }

    @RequestMapping
    public void add(Model model){
        model.put("gList", partyOpinionService.listGroup());
        model.put("depts", partyDeptService.partyDeptList(1));
        String pid = AdminSession.getAdmin().getExtraMap().get("PARTYID");
        List<PartyDept> partyDeptList = null;
        if(pid == null || pid == ""){
            String sql = "select id as deptid,name as deptname,parentid from t_party_dept where is_party=0";
            partyDeptList = pubDao.queryList(PartyDept.class, sql);
        }else {
            String sql = "select id as deptid,name as deptname,parentid from t_party_dept where parentid=? and is_party = 0";
            partyDeptList =  pubDao.queryList(PartyDept.class, sql, pid);
            if(partyDeptList == null || partyDeptList.size() == 0){
                sql = "select id as deptid,name as deptname,parentid from t_party_dept where id = ?";
                partyDeptList =  pubDao.queryList(PartyDept.class, sql, pid);
            }
        }
        model.put("branch", partyDeptList);
    }
}

package wxgh.wx.web.manage.judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.party.vote.PartyBranchVote;
import wxgh.param.party.ArticleParam;

import java.util.List;

@Controller
public class MainController {

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public ActionResult get_list(ArticleParam param){
        if(param.getStatus() == 1) {
            param.setPageIs(true);
            param.setRowsPerPage(10);
            return ActionResult.okRefresh(list(param), param);
        }else{
            return ActionResult.ok();
        }
    }

    public List<PartyBranchVote> list(Page page){

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("manage_branch_vote op")
                .field("*");
        return pubDao.queryPage(sql, page, PartyBranchVote.class);
    }

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public void show(Integer id,Model model){
        String sql = "select * from t_manage_branch_vote where id = ?";
        PartyBranchVote partyBranchVote = pubDao.query(PartyBranchVote.class,sql,id);
        model.put("s",partyBranchVote);
    }

    @RequestMapping
    public void result(Integer id,Model model){

        String sqlJoinType = "SELECT COUNT(userid) AS num FROM t_manage_branch_vote_record WHERE vote_id = ? GROUP BY userid";
        List<Integer> num = pubDao.queryList(Integer.class,sqlJoinType,id);

        model.put("num",num.size());

    }
}

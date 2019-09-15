package wxgh.wx.web.party.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Where;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.entity.chat.ChatGroup;
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

        String userid = UserSession.getUserid();

        /*String sqlUser = "select userid from t_party_dept_user where userid = ?";
        String u = pubDao.query(String.class, sqlUser, userid);

        String sqlBranch = "select branch_id from t_party_dept_user where userid = ?";
        String b = pubDao.query(String.class, sqlBranch, userid);

        String sqlParty = "select party_id from t_party_dept_user where userid = ?";
        String p = pubDao.query(String.class, sqlParty, userid);*/

//        String sqlGroup = new SQL.SqlBuilder()
//                .table("chat_user cu")
//                .field("g.id")
//                .join("chat_group g", "g.group_id = cu.group_id")
//                .where("cu.userid = ?")
//                .where("g.type = ?")
//                .groupBy("g.group_id")
//                .select()
//                .build().sql();
//        List<Integer> gId = pubDao.queryList(Integer.class, sqlGroup, userid, ChatGroup.TYPE_PARTY_GROUP);

        /*if(u == null && gId.size() == 0)
            return null;*/

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_branch_vote op")
                .field("*");
        /*if(u != null)
            sql.where("type = 1 and group_id = " + p, Where.Logic.OR);
            sql.where("type = 1 and group_id = " + b, Where.Logic.OR);
            sql.where("type = 3 and group_id = " + p, Where.Logic.OR);
            sql.where("type = 4 and group_id = " + b, Where.Logic.OR);*/
        /*for (Integer id : gId)
            sql.where("(type = 2 and find_in_set("+id+", group_id))", Where.Logic.OR);*/
        return pubDao.queryPage(sql, page, PartyBranchVote.class);
    }

    @Autowired
    private PubDao pubDao;
}

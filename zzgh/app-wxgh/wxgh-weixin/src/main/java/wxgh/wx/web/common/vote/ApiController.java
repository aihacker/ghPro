package wxgh.wx.web.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.common.vote.VoteDetail;

import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public ActionResult get_vote(VoteDetail param) {
        param.setPageIs(true);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("voted_join vj")
                .join("user u","u.userid = vj.userid", Join.Type.LEFT)
                .join("voted_option vo","vo.id = vj.option_id")
                .field("u.avatar, u.name ,vo.options")
                .where("vj.voted_id =:id")
                .order("vo.id");
        List<VoteDetail> list =  pubDao.queryPage(sql, param, VoteDetail.class);
        return ActionResult.okRefresh(list, param);
    }
}

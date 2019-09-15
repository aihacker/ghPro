package wxgh.sys.service.weixin.pub.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.entity.pub.score.ScoreTransfer;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;

/**
 * Created by Administrator on 2017/8/22.
 */
@Service
@Transactional(readOnly = true)
public class ScoreTransferService {

    @Transactional
    public void addTransfer(ScoreTransfer scoreTransfer) {
        //保存积分转移记录
        pubDao.executeBean(JdbcSQL.save(ScoreTransfer.class), scoreTransfer);

        String sql = "select name from t_user where userid = ?";
        String toUser = pubDao.query(String.class, sql, scoreTransfer.getToUserid());
        String username = UserSession.getUser().getName();

        SimpleScore score = new SimpleScore();
        score.setStatus(Status.NORMAL.getStatus());

        //添加积分
        score.setUserid(scoreTransfer.getToUserid());
        score.setScore(scoreTransfer.getScore());
        score.setInfo("来自“" + username + "”的转移积分");
        scoreService.user(score, ScoreType.USER_TRAN);

        //减少积分
        score.setUserid(scoreTransfer.getUserid());
        score.setScore(-scoreTransfer.getScore());
        score.setInfo("积分转移给“" + toUser + "”");
        scoreService.user(score, ScoreType.USER_TRAN);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ScoreService scoreService;
}

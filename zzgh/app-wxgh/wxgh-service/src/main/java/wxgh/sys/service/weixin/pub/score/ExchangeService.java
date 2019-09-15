package wxgh.sys.service.weixin.pub.score;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.pcadmin.score.UserExchangeRecord;
import wxgh.data.pub.score.ExchangeList;
import wxgh.data.pub.score.GoodsCountList;
import wxgh.data.pub.score.MyExchangeList;
import wxgh.data.pub.score.UnExchangeList;
import wxgh.entity.score.Exchange;
import wxgh.entity.score.IntegralGoods;
import wxgh.param.pcadmin.score.ExchangeListParam;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.param.score.ExchangeParam;
import wxgh.param.score.GoodsCountParam;
import wxgh.param.user.UserParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service
@Transactional(readOnly = true)
public class ExchangeService {

    public List<ExchangeList> listGoods(ExchangeParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_integral_goods g")
                .field("g.id, g.name, g.points, g.info")
                .sys_file("g.img")
                .order("g.points", Order.Type.DESC);
        return pubDao.queryPage(sql, param, ExchangeList.class);
    }

    public ExchangeList get(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_integral_goods g")
                .field("g.id, g.name, g.points, g.info")
                .where("g.id = ?")
                .sys_file("g.img");
        return pubDao.query(ExchangeList.class, sql.select().build().sql(), id);
    }

    /**
     * 获取用户积分（非场馆预约积分）
     *
     * @param userid
     * @return
     */
    public Float getExchangeScore(String userid) {
        ScoreParam param = new ScoreParam();
        param.setGroup(ScoreGroup.USER);
        param.setNotTypes(ScoreType.PLACE.getType() + "," + ScoreType.GIVE.getType());
        param.setStatus(Status.NORMAL.getStatus());
        param.setUserid(userid);
        Float score = scoreService.sumScoreExchange(param);
        return score == null ? 0 : score;
    }

    @Transactional
    public void exchange(Exchange exchange) {
        String goodSql = "select * from t_integral_goods where id= ?";
        IntegralGoods goods = pubDao.query(IntegralGoods.class, goodSql, exchange.getGoodsId());
        if (goods == null) {
            throw new ValidationError("您兑换的商品已经没有啦~");
        }

        String userid = UserSession.getUserid();
        //获取非场馆预约积分
        Float score = getExchangeScore(userid);
        if (score < goods.getPoints()) {
            throw new ValidationError("您的积分不足，请换其他商品吧~");
        }

        // 检查用户是否已兑换
//        String hasSql = new SQL.SqlBuilder()
//                .table("t_exchange")
//                .field("id")
//                .where("userid = ?")
//                .where("goods_id = ?")
//                .select()
//                .build()
//                .sql();
//        String has = pubDao.query(String.class, hasSql, userid, exchange.getGoodsId());
//        if(has!=null)
//            throw new ValidationError("您已兑换过改商品不能重复兑换!");

        //新增兑换记录
        exchange.setScore(goods.getPoints());
        exchange.setUserid(userid);
        exchange.setStatus(0);
        exchange.setAddTime(new Date());
        Integer exchangeId = pubDao.insertAndGetKey(JdbcSQL.save(Exchange.class), exchange);

        //扣除用户积分
        SimpleScore simpleScore = new SimpleScore();
        simpleScore.setInfo("【商品兑换】-" + goods.getName());
        simpleScore.setStatus(1);
        simpleScore.setUserid(userid);
        simpleScore.setScore(-goods.getPoints());
        simpleScore.setById(exchangeId.toString());
        scoreService.user(simpleScore, ScoreType.USER_EXCHANGE);
    }

    /**
     * 我的兑换的商品
     * @param param
     * @return
     */
    public List<MyExchangeList> getMyExchangeList(UserParam param){
        param.setUserid(UserSession.getUserid());
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_exchange e")
                .field("e.id, g.id as goodsId, e.add_time, e.remark, e.status, e.score, g.name as goodsName, e.confirm")
                .join("t_integral_goods g", "g.id = e.goods_id")
                .sys_file("g.img")
                .where(" e.userid = :userid");
        return pubDao.queryPage(sql, param, MyExchangeList.class);
    }

    /**
     * 未兑换的商品
     * @param param
     * @return
     */
    public List<UnExchangeList> getUnExchangeList(UserParam param){
        param.setUserid(UserSession.getUserid());
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_integral_goods g")
                .sys_file("g.img")
                .field("g.*")
                .where("g.id not in (select e.goods_id from t_exchange e where e.userid = :userid)")
                ;
        return pubDao.queryPage(sql, param, UnExchangeList.class);
    }

    @Transactional
    public void cancelExchange(Integer id){

        String sql = new SQL.SqlBuilder()
                .table("t_exchange e")
                .field("e.id, g.id as goodsId, e.add_time, e.remark, e.status, e.score, g.name as goodsName, e.confirm")
                .join("t_integral_goods g", "g.id = e.goods_id")
                .where("e.id = ?")
                .where("(e.confirm != ? or e.confirm is null)")
                .select()
                .build()
                .sql();
        MyExchangeList exchangeList = pubDao.query(MyExchangeList.class, sql, id, Exchange.CONFIRM_YES);

        if(exchangeList == null)
            throw new ValidationError("不存在的兑换记录");

        // 退回积分
        SimpleScore simpleScore = new SimpleScore();
        simpleScore.setInfo("【取消商品兑换】-" + exchangeList.getGoodsName());
        simpleScore.setStatus(1);
        simpleScore.setUserid(UserSession.getUserid());
        simpleScore.setScore((float) exchangeList.getScore());
        simpleScore.setById(id.toString());
        scoreService.user(simpleScore, ScoreType.USER_EXCHANGE);

        pubDao.execute(SQL.deleteByIds("t_exchange", id.toString()));
    }

    // 确认已兑换
    @Transactional
    public void confirmYes(Integer id){
        String sql = new SQL.SqlBuilder()
                .table("exchange")
                .set("confirm = ?")
                .where("id = ?")
                .where("userid = ?")
                .update()
                .build()
                .sql();
        pubDao.execute(sql, Exchange.CONFIRM_YES, id, UserSession.getUserid());
    }

    // 兑换详情
    public MyExchangeList exchangeDetail(Integer id){
        String sql = new SQL.SqlBuilder()
                .table("t_exchange e")
                .field("e.id, g.id as goodsId, g.info, e.add_time, e.remark, e.status, e.score, g.name as goodsName, e.confirm")
                .join("t_integral_goods g", "g.id = e.goods_id")
                .sys_file("g.img")
                .where(" e.userid = ?")
                .where(" e.id = ?")
                .select()
                .build()
                .sql();
        return pubDao.query(MyExchangeList.class, sql, UserSession.getUserid(), id);
    }

    // 获取记录
    public List<UserExchangeRecord> getList(ExchangeListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("exchange e")
                .join("user u", "u.userid = e.userid")
                .join("integral_goods g", "g.id = e.goods_id")
                .field("e.id, e.userid, g.type, u.name as username, u.mobile, g.name, g.id as goodsId, e.add_time, e.score, e.confirm, e.status");
        if(param.getKey() != null)
            sql.where("(u.name like  concat('%', :key , '%') or g.name like  concat('%', :key , '%'))");
        if(param.getStatus() != null)
            sql.where("e.status = :status");
        return pubDao.queryPage(sql, param, UserExchangeRecord.class);
    }



    // 审核
    @Transactional
    public void apply(String id, Integer status){
        String sql = "update t_exchange set status = ? where id = ?";
        pubDao.execute(sql, status, id);
    }

    // 删除记录
    @Transactional
    public void delete(String id){
        SQL.deleteByIds("t_exchange", id);
    }

    public List<GoodsCountList> count(GoodsCountParam param){
        if(!TypeUtils.empty(param.getYear())){
            if(!TypeUtils.empty(param.getMonth())){
                String sql = "select a.id, a.name, a.type, IFNULL(b.sum, 0) as sum from t_integral_goods a left join (select g.id, count(*) as sum, DATE_FORMAT(e.add_time,'%Y%m') as month from t_integral_goods g left join t_exchange e on e.goods_id = g.id where DATE_FORMAT(e.add_time,'%Y%m')=:month group by g.id,month) b on b.id = a.id";
                param.setMonth(param.getYear()+param.getMonth());
                return pubDao.queryList(sql, param, GoodsCountList.class);
            }else{
                String sql = "select a.id, a.name, a.type, IFNULL(b.sum, 0) as sum from t_integral_goods a left join (select g.id, count(*) as sum, DATE_FORMAT(e.add_time,'%Y') as month from t_integral_goods g left join t_exchange e on e.goods_id = g.id where DATE_FORMAT(e.add_time,'%Y')=:year group by g.id,month) b on b.id = a.id";
                return pubDao.queryList(sql, param, GoodsCountList.class);
            }
        }
        return null;
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ScoreService scoreService;
}

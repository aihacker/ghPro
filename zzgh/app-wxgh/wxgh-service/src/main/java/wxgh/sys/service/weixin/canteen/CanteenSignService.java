package wxgh.sys.service.weixin.canteen;

import com.libs.common.map.DistanceUtils;
import com.libs.common.map.GeoHash;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.entertain.act.SignInfo;
import wxgh.data.entertain.act.SignList;
import wxgh.entity.canteen.Canteen;
import wxgh.entity.canteen.CanteenActSign;
import wxgh.entity.pub.SysFile;
import wxgh.entity.union.group.ActSign;
import wxgh.param.entertain.act.SignParam;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
@Service
@Transactional(readOnly = true)
public class CanteenSignService {

    @Transactional
    public void sign(ActSign sign) {
        sign.setUserid(UserSession.getUserid());
        // todo 判断用户是否已经报名参加活动
        String signSql = new SQL.SqlBuilder()
                .table("canteen_act a")
                .field("a.lat, a.lng, a.sign_is, a.act_id")
                .field("(select type from t_canteen_act_join where act_id = a.act_id and userid = ?) as joinType")
                .where("a.id = ?")
                .build().sql();
        SignInfo signInfo = pubDao.query(SignInfo.class, signSql, sign.getUserid(), sign.getActId());
        if (signInfo != null) {
            if (signInfo.getJoinType() == null || signInfo.getJoinType() > 1) {
                throw new ValidationError("请报名参加活动！");
            }

            if (!TypeUtils.empty(sign.getImgs())) {
                String[] imgs = sign.getImgs().split(",");
                List<String> imgList = new ArrayList<>();
                for (String img : imgs) {
                    fileApi.wxDownload(img, new SuccessCallBack() {
                        @Override
                        public void onSuccess(SysFile file, File toFile) {
                            imgList.add(file.getFileId());
                        }
                    });
                }
                sign.setImgs(TypeUtils.listToStr(imgList));
            }

            sign.setAddTime(new Date());
            sign.setHash(GeoHash.encode(sign.getLat(), sign.getLng()));

            double distan = DistanceUtils.getShortDistance(sign.getLng(), sign.getLat(), signInfo.getLng(), signInfo.getLat());
            BigDecimal bg = new BigDecimal(distan);
            sign.setDistan(bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            if (distan > 2000) {
                sign.setStatus(2);
            } else {
                sign.setStatus(1);
            }
            sign.setActId(signInfo.getActId());

            pubDao.executeBean(JdbcSQL.save(CanteenActSign.class), sign);
        }
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;

    public List<SignList> listSign(SignParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("canteen_act_sign s")
                .field("s.add_time, s.userid, s.address, s.distan")
                .field("u.name as username, u.avatar")
                .join("canteen_act a", "s.act_id = a.act_id")
                .join("user u", "s.userid = u.userid")
                .where("a.id = :actId")
                .order("s.add_time");
        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }
        if (param.getDateid() != null) {
            sql.where("s.dateid = :dateid");
        }
        return pubDao.queryPage(sql, param, SignList.class);
    }
}

package wxgh.sys.service.weixin.party.branch;

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
import wxgh.data.party.branch.PartySignInfo;
import wxgh.data.party.branch.PartySignList;
import wxgh.entity.party.party.PartyAct;
import wxgh.entity.party.party.PartyActSign;
import wxgh.entity.pub.SysFile;
import wxgh.param.entertain.act.SignParam;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PartySignService {

    @Transactional
    public void sign(PartyActSign sign) {

        // 判断是否在签到时间
        PartyAct partyAct = pubDao.query(PartyAct.class, "select * from t_party_act where id = ?", sign.getActId());
        Date now = new Date();
        boolean isBetweenTime = false;
        if(partyAct.getStartTime().before(now) && partyAct.getEndTime().after(now)){
            isBetweenTime = true;
        }
        if(!isBetweenTime)
            throw new ValidationError("签到失败，不在活动时间！");

        sign.setUserid(UserSession.getUserid());
        // todo 判断用户是否已经报名参加活动
        String signSql = new SQL.SqlBuilder()
                .table("party_act a")
                .field("a.lat, a.lng, a.id as actId")
                .field("(select status from t_party_act_join where act_id = a.id and userid = ?) as joinType")
                .where("a.id = ?")
                .build().sql();
        PartySignInfo signInfo = pubDao.query(PartySignInfo.class, signSql, sign.getUserid(), sign.getActId());
        if (signInfo != null) {
            if (signInfo.getJoinType() == null || signInfo.getJoinType() != 1) {
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
            if(sign.getLat() == null || sign.getLng() == null)
                throw new ValidationError("签到失败，请先定位！");
            // todo 活动地址没有经纬度数据（实际中活动地址会填具体会议室等，手动填写没有经纬度信息） 签到无须判断经纬差
            if(signInfo.getLat()!=null && signInfo.getLng() !=null) {
                sign.setHash(GeoHash.encode(sign.getLat(), sign.getLng()));
                double distan = DistanceUtils.getShortDistance(sign.getLng(), sign.getLat(), signInfo.getLng(), signInfo.getLat());
                BigDecimal bg = new BigDecimal(distan);
                sign.setDistan(bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

                if (distan > 2000) {
                    sign.setStatus(2);
                } else {
                    sign.setStatus(1);
                }
            } else {
                sign.setDistan(0f);
                sign.setStatus(1);
            }
            sign.setActId(signInfo.getActId());
            pubDao.executeBean(JdbcSQL.save(PartyActSign.class), sign);
        }else
            throw new ValidationError("请报名参加活动！");
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;

    public List<PartySignList> listSign(SignParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_act_sign s")
                .field("s.add_time, s.userid, s.address, s.distan")
                .field("u.name as username, u.avatar")
                .join("party_act a", "s.act_id = a.id")
                .join("user u", "s.userid = u.userid")
                .where("a.id = :actId")
                .order("s.add_time");
        if (param.getStatus() != null) {
            sql.where("s.status = :status");
        }
        return pubDao.queryPage(sql, param, PartySignList.class);
    }
}
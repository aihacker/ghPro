package wxgh.wx.web.common.female;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.female.FemaleMamaJoin;
import wxgh.param.common.female.QueryFemaleMamaJoin;
import wxgh.sys.service.weixin.common.female.FemaleMamaJoinService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 10:14
 *----------------------------------------------------------
 */
@Controller
public class MamaJoinController {


    @Autowired
    private FemaleMamaJoinService femaleMamaJoinService;

    @RequestMapping
    public void index(Model model){

    }

    @RequestMapping
    public ActionResult getListData(Integer indexID, boolean isFirst, boolean isRefresh) throws UnsupportedEncodingException, ParseException {

        SeUser user = UserSession.getUser();

        QueryFemaleMamaJoin queryFemaleMamaJoin = new QueryFemaleMamaJoin();
        queryFemaleMamaJoin.setUserid(user.getUserid());

        if (!isFirst && !isRefresh){
            queryFemaleMamaJoin.setIndexID(indexID);
        }

        List<FemaleMamaJoin> femaleMamaJoinList = femaleMamaJoinService.getDataWX(queryFemaleMamaJoin);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("HH");
        String dateNowStr = simpleDateFormat.format(date);
        Integer dateNowHour = Integer.valueOf(simpleDateFormatHour.format(date));

        Date dateNow = simpleDateFormat.parse(dateNowStr);

        for (FemaleMamaJoin femaleMamaJoin: femaleMamaJoinList){
            femaleMamaJoin.setName(URLDecoder.decode(femaleMamaJoin.getName(), "UTF-8"));
            femaleMamaJoin.setCover(PathUtils.getImagePath(femaleMamaJoin.getCover()));
            femaleMamaJoin.setDateStr(DateUtils.formtYMD(femaleMamaJoin.getDate()));

            if (femaleMamaJoin.getDate().getTime() > dateNow.getTime())
                femaleMamaJoin.setStatus(2);
            else {
                if (femaleMamaJoin.getStartTime() >= dateNowHour )
                    femaleMamaJoin.setStatus(2);
                else {
                    femaleMamaJoin.setStatus(1);
                }
            }
        }

        return ActionResult.ok(null, femaleMamaJoinList);
    }
    
}


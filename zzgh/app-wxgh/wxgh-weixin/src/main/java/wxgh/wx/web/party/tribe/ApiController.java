package wxgh.wx.web.party.tribe;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.party.tribe.CommInfo;
import wxgh.entity.pub.SysFile;
import wxgh.entity.pub.score.Score;
import wxgh.entity.tribe.TribeAct;
import wxgh.entity.tribe.TribeActComment;
import wxgh.entity.tribe.TribeActJoin;
import wxgh.param.party.tribe.JoinParam;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.query.tribe.TribeActQuery;
import wxgh.query.tribe.TribeActQuery2;
import wxgh.sys.service.weixin.pub.score.ScoreService;
import wxgh.sys.service.weixin.tribe.TribeActCommentService;
import wxgh.sys.service.weixin.tribe.TribeActJoinService;
import wxgh.sys.service.weixin.tribe.TribeActService;

import java.io.File;
import java.util.*;

/**
 * Created by Administrator on 2017/8/17.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_act(TribeActQuery2 tribeActQuery2) {
        //设置条数
        if (tribeActQuery2.getStatus() == null) {
            tribeActQuery2.setStatus(1);
        }

        tribeActQuery2.setRowsPerPage(8);
        tribeActQuery2.setPageIs(true);

        List<TribeAct> yugaoTotal = tribeActService.getData2(tribeActQuery2);
        Integer count = 0;
        if (null != yugaoTotal && yugaoTotal.size() > 0) {
            count = yugaoTotal.size();
        }
        tribeActQuery2.setTotalCount(count);

        //查出数据
        TribeActQuery tribeActQuery = new TribeActQuery();
        tribeActQuery.setStatus(tribeActQuery2.getStatus());

        List<TribeAct> yugao = tribeActService.getData(tribeActQuery);
        if (null != yugao && yugao.size() > 0) {
            for (TribeAct tribeAct : yugao) {
                tribeAct.setCoverImg(PathUtils.getImagePath(tribeAct.getCoverImg()));
            }
        }

        RefData refData = new RefData();
        refData.put("list", yugao);
        refData.put("total", tribeActQuery2.getPages());
        refData.put("current", tribeActQuery2.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult list_score(ScoreParam param) {
        param.setPageIs(true);
        param.setStatus(Status.NORMAL.getStatus());
        param.setUserid(UserSession.getUserid());
        param.setGroup(ScoreGroup.TRIBE);

        List<Score> scores = scoreService.listScore(param);

        return ActionResult.okRefresh(scores, param);
    }

    /**
     * 报名参加
     *
     * @param actId
     * @return
     */
    @RequestMapping
    public ActionResult join(Integer actId) {
        TribeActJoin actJoin = new TribeActJoin();
        actJoin.setUserid(UserSession.getUserid());
        actJoin.setActId(actId);
        actJoin.setStatus(1);
        actJoin.setIntegral(1);
        actJoin.setJoinTime(new Date());
        tribeActJoinService.add(actJoin);
        return ActionResult.ok();
    }

    /**
     * 评论列表
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult comm_list(Integer id) {
        List<CommInfo> commInfos = tribeActService.listComm(id);
        return ActionResult.okWithData(commInfos);
    }

    @RequestMapping
    public ActionResult join_list(JoinParam param) {
        param.setPageIs(true);
        param.setStatus(1);
        List<TribeActJoin> joins = tribeActJoinService.listJoin(param);
        return ActionResult.okRefresh(joins, param);
    }

    @RequestMapping
    public ActionResult add_comment(TribeActComment tribeActComment) {
        //判断档案是否符合条件
        if (StrUtils.empty(tribeActComment.getContent())) {
            return ActionResult.error("评论不能为空哦");
        }

        if (tribeActComment.getImg() != null) {
            //分割字符串
            String[] imgs = tribeActComment.getImg().split(",");
            //倒序
            Collections.reverse(Arrays.asList(imgs));
            //清空imgList
            List<String> imgsStrs = new ArrayList<>();
            for (String mediaId : imgs) {
                fileApi.wxDownload(mediaId, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        imgsStrs.add(file.getFileId());
                    }
                });
            }

            tribeActComment.setImg(TypeUtils.listToStr(imgsStrs));
        } else {
            tribeActComment.setImg(null);
        }

        SeUser user = UserSession.getUser();
        tribeActComment.setUserid(user.getUserid());
        tribeActComment.setAddTime(new Date());

        tribeActCommentService.add(tribeActComment);

        return ActionResult.ok();
    }

    @Autowired
    private TribeActService tribeActService;

    @Autowired
    private TribeActJoinService tribeActJoinService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private TribeActCommentService tribeActCommentService;

    @Autowired
    private FileApi fileApi;

}

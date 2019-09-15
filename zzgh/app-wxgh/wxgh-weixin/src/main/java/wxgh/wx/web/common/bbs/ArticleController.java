package wxgh.wx.web.common.bbs;


import com.libs.common.data.DateUtils;
import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.Article;
import wxgh.entity.common.Comment;
import wxgh.entity.common.Zan;
import wxgh.entity.pub.SysFile;
import wxgh.param.common.bbs.CommParam;
import wxgh.sys.service.weixin.common.bbs.ArticleService;
import wxgh.sys.service.weixin.common.bbs.CommentService;
import wxgh.sys.service.weixin.common.bbs.ZanService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-26 11:11
 * ----------------------------------------------------------
 */
@Controller
public class ArticleController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ZanService zanService;

    @Autowired
    private FileApi fileApi;

    /**
     * 详情页
     *
     * @param id
     * @param model
     * @param session
     */
    @RequestMapping
    public void show(Integer id, Model model, HttpSession session) {
        //更新浏览记录
        Boolean isSee = (Boolean) session.getAttribute("article_see_session_" + id);
        if (isSee == null || !isSee) {
            articleService.updateSeeNum(id);
            session.setAttribute("article_see_session_" + id, true);
        }

        Article article = articleService.get(id);
        article.setCreateFormatDate(DateUtils.formatDate(article.getCreateTime()));

        //是否点赞
        boolean isZan = true;
        SeUser user = UserSession.getUser();
        if (user != null) {
            Integer zanId = articleService.isZan(id, user.getUserid());
            isZan = (zanId != null);
        }

        model.put("isZan", isZan);
        model.put("a", article);
    }

    /**
     * 评论列表
     *
     * @param query
     * @return
     */
    @RequestMapping
    public ActionResult commList(CommParam query) {
        if (null == query.getType()) {
            query.setType(Comment.TYPE_BBS);
        }
        Integer count = commentService.countComm(query);

        query.setPageIs(true);
        query.setTotalCount(count);
        query.setRowsPerPage(10);

        SeUser user = UserSession.getUser();
        if (user != null) {
            query.setUserid(user.getUserid());
        }
        List<Comment> comments = commentService.listComm(query);
        if (!TypeUtils.empty(comments)) {
            for (int i = 0; i < comments.size(); i++) {
                comments.get(i).setCreateFormatDate(
                        DateUtils.formatDate(comments.get(i).getCreateTime()));
            }
        }

        RefData refData = new RefData();
        refData.put("comments", comments);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }


    /**
     * 点赞
     *
     * @param id
     * @param type 1文章，2评论
     * @return
     */
    @RequestMapping
    public ActionResult add_zan(Integer id, Integer type) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }
        Integer num = 0;
        Integer zanType = 0;


        synchronized (this) {
            if (type == 1) { //文章点赞
                num = articleService.getNum(id, "zan_num");
                zanType = articleService.updateZanNum(id, user.getUserid());
            } else { //评论点赞
                num = commentService.getCommNum(id);
                zanType = commentService.updateCommNum(id, user.getUserid());
            }

            if (zanType == 1) {
                num++;
                Zan zan = new Zan();
                zan.setUserid(user.getUserid());
                zan.setZanId(id);
                zan.setZanType(type == 1 ? Zan.TYPE_ARTICLE : Zan.TYPE_ARTICLE_COMM);
                zanService.save(zan);
            } else {
                num--;
            }
        }
        RefData refData = new RefData();
        refData.put("type", zanType);
        refData.put("num", num);
        return ActionResult.ok(null, refData);
    }

    /**
     * 添加文章评论
     *
     * @param comment
     * @return
     */
    @RequestMapping
    public ActionResult add_comm(Comment comment) {

        if (StrUtils.empty(comment.getAtlComment())) {
            return ActionResult.error("评论内容不能为空哦");
        }

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        comment.setUserid(user.getUserid());

        commentService.addComm(comment);

        if (comment.getType() == Comment.TYPE_PARTYSUG) {
            Integer sugId = comment.getAtlId();
//            PartySug partySug = partySugService.getSug(sugId);
//            if (partySug != null) {
//                AdminMsg msg = MsgBuilder.partysug_reply(user.getUserid(), comment.getAtlId(), partySug.getUserid());
//                adminMsgTask.send(msg);
//            }
        }

        return ActionResult.ok();
    }

    /**
     * 发布文章
     *
     * @param article
     * @return
     */
    @RequestMapping
    public ActionResult add(Article article) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        if (article.getAtlName().length() > 50) {
            return ActionResult.error("请填写50个字符以内的标题哦");
        }

        if (article.getAtlContent().length() > 200) {
            return ActionResult.error("请填写200个字符以内的内容哦");
        }

        String mediaIds = article.getFileIds();

        StringBuilder fileIds = new StringBuilder();
        if (!StrUtils.empty(mediaIds)) {
            String[] mids = mediaIds.split(",");
            for (String id : mids) {

                // 微信图片下载
                fileApi.wxDownload(id, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        fileIds.append(file.getFileId()).append(",");
                    }
                });

            }
        }
        if (fileIds.length() > 0) {
            article.setFileIds(fileIds.substring(0, fileIds.length() - 1));
        }

        article.setUserid(user.getUserid());
        article.setIsdel(1);
        article.setStatus(0); //需要审核
        article.setType(1);
        article.setSeeNum(0);
        article.setZanNum(0);
        article.setDeptid(user.getDeptid());

        articleService.save(article);

        //推送管理员消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.BBS, user.getUserid(), article.getAtlId().toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("工会活动发布申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

    @Autowired
    private WeixinPush weixinPush;

}


package wxgh.admin.web.four;

import com.libs.common.type.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import pub.utils.PathUtils;
import wxgh.app.consts.Status;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.four.AddParam;
import wxgh.data.four.AddRes;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.common.ActApply;
import wxgh.entity.entertain.place.PlaceImg;
import wxgh.entity.four.FourDetails;
import wxgh.entity.four.FourPropagate;
import wxgh.entity.four.Marketing;
import wxgh.entity.pub.SysFile;
import wxgh.param.four.*;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.admin.four.FourService;
import wxgh.sys.service.weixin.four.FourAddService;
import wxgh.sys.service.weixin.four.FourDetailsService;
import wxgh.sys.service.weixin.four.MarketingService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult fourlist(PropagateParam query) {
//        if (query.getStatus() == null) {
//            query.setStatus(0);
//        }
        query.setPageIs(true);
        query.setRowsPerPage(9);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("p.*, d.name AS deptName, pt.name AS fpName, fpc.name AS fpcName, m.`name` AS marketing,u.name As username")
                .table("four_propagate p")
                .join("dept d", "p.dept_id = d.deptid", Join.Type.LEFT)
                .join("user u", "p.userid = u.userid")
                .join("four_project pt", "p.fp_id = pt.id")
                .join("four_project_content fpc", "p.fpc_id = fpc.id")
                .join("marketing m", "m.`id` = p.`mid`")
                .order("p.apply_time", Order.Type.DESC);

        if (query.getStatus() != null) {
            sql.where("p.status = :status");
        }
        if (query.getDeviceStatus() != null) {
            sql.where("p.device_status = :deviceStatus");
        }

        if (query.getId() != null) {
            sql.where("p.id = :id");
        }

        if (query.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), query);
            query.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<FourPropagate> list = pubDao.queryList(sql.select().build().sql(), query, FourPropagate.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(query));

        return ActionResult.ok(null, refData);
    }

    // 入库申请
    @RequestMapping
    public ActionResult ruku_apply(Integer id, Integer status, Boolean notify) {
        fourDetailsService.apply(id, status);
        // 推送
        if (notify != null && notify) {
            FourDetails fourDetails = fourDetailsService.getDetailByPush(id);
            if(fourDetails != null){
                String toUserid = fourDetails.getUserId();
                //回复消息，通知用户审核已通过
                ReplyPush push = new ReplyPush(toUserid, status == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
                push.setMsg("您提交的入库申请结果:\n" + fourDetails.getMarketName()
                        + "\n" + fourDetails.getFpName() + "-" + fourDetails.getFpcName() + "x"
                        + fourDetails.getNumb() + fourDetails.getUnit());
                weixinPush.reply(push);
            }
        }
        return ActionResult.ok();
    }

    // 新购 更换 申请
    @RequestMapping
    public ActionResult fourchange(Integer id, Integer status, Boolean notify) {
        String sql = "UPDATE t_four_propagate SET status = ? WHERE id = ?";
        pubDao.execute(sql, status, id);

        if (notify != null && notify) {
            String sql2 = new SQL.SqlBuilder()
                    .field("p.*, d.name AS deptName, pt.name AS fpName, fpc.name AS fpcName, m.`name` AS marketing,u.name As username")
                    .table("four_propagate p")
                    .join("dept d", "p.dept_id = d.deptid", Join.Type.LEFT)
                    .join("user u", "p.userid = u.userid")
                    .join("four_project pt", "p.fp_id = pt.id")
                    .join("four_project_content fpc", "p.fpc_id = fpc.id")
                    .join("marketing m", "m.`id` = p.`mid`")
                    .where("p.id = ?")
                    .select()
                    .build().sql();

            FourPropagate propagation = pubDao.query(FourPropagate.class, sql2, id);
//            ReplyPush push = new ReplyPush(propagation.getUserid(), propagation.getStatus());
//            StringBuffer cnt = new StringBuffer();
//            cnt.append("您提交的申请：\n" + propagation.getMarketing()
//                    + "\n" + propagation.getFpName() + "-" + propagation.getFpcName() + "x"
//                    + propagation.getNumb());
//            push.setHost(ServletUtils.getHostAddr());
//            push.setAgentId(1000012);
//            push.setMsg(cnt.toString());
//            weixinPush.reply(push);

            String toUserid = propagation.getUserid();
            //回复消息，通知用户审核已通过
            ReplyPush push = new ReplyPush(toUserid, status == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
            if(propagation.getDeviceStatus().equals(FourPropagate.Buy))
                push.setMsg("您提交的新购申请结果:\n" + propagation.getMarketing()
                    + "\n" + propagation.getFpName() + "-" + propagation.getFpcName() + "x"
                    + propagation.getNumb() + propagation.getUnit());
            else
                push.setMsg("您提交的 维修/更换 申请结果:\n" + propagation.getMarketing()
                        + "\n" + propagation.getFpName() + "-" + propagation.getFpcName() + "x"
                        + propagation.getNumb() + propagation.getUnit());
            weixinPush.reply(push);

        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult fourdel(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            pubDao.execute(SQL.deleteByIds("four_propagate", i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    private ActionResult applyget(Integer id) {
        String sql = new SQL.SqlBuilder().table("act_apply").where("id =?").select()
                .build().sql();
        ActApply actApply = pubDao.query(ActApply.class, sql, id);

        RefData refData = new RefData();
        refData.put("datas", actApply);
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult list_market(Integer deptid) {
        QueryMarketingParam queryMarketing = new QueryMarketingParam();
        queryMarketing.setDeptid(deptid);
        List<Marketing> marketingList = marketingService.getList(queryMarketing);
        return ActionResult.ok(null, marketingList);
    }

    @RequestMapping
    public ActionResult list_fp(AddParam param) {
        List<AddRes> res = fourAddService.byMarket(param.getMid());
        return ActionResult.ok(null, res);
    }

    @RequestMapping
    public ActionResult list_fpc(AddParam param) {
        List<AddRes> res = null;
        if (param.getName() != null)
            res = fourAddService.byFpAndMarket(param.getMid(), param.getFpId(), param.getName());
        else
            res = fourAddService.byFpAndMarket(param.getMid(), param.getFpId());
        return ActionResult.ok(null, res);
    }

    @RequestMapping
    public ActionResult export_list(FourExportListParam param) {
        if(param.getMid() == null || param.getMid().equals(""))
            param.setMid(null);
        param.setPageIs(true);
        param.setRowsPerPage(10);
        return ActionResult.okAdmin(fourDetailsService.getExportList(param), param);
    }

    @RequestMapping
    public ActionResult export_delete(String id) {
        fourDetailsService.deleteExport(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult ruku(FourRukuListParam param) {
        if(param.getMid() == null || param.getMid().equals(""))
            param.setMid(null);
        param.setPageIs(true);
        param.setRowsPerPage(10);
        return ActionResult.okAdmin(fourDetailsService.getRukuList(param), param);
    }

    @RequestMapping
    public ActionResult add_export_pic(Integer id, @RequestParam("img") MultipartFile[] multipartFile) {
        if (multipartFile != null && multipartFile.length > 0) {
            List<String> list = new ArrayList<>();
            for (MultipartFile m : multipartFile) {
                SysFile sysFile = fileApi.addFile(m, new FileParam());
                list.add(sysFile.getFileId());
            }
            fourDetailsService.savePic(id, TypeUtils.listToStr(list));
            return ActionResult.ok();
        }
        return ActionResult.error("没有上传图片");
    }

    @RequestMapping
    public ActionResult markets(MarketListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        return ActionResult.okAdmin(marketingService.getMarkets(param), param);
    }

    @RequestMapping
    public ActionResult add_market(Marketing marketing, @RequestParam("img") MultipartFile multipartFile) {
        if(marketing.getId() == null){
            if (marketingService.getMarketing(marketing.getName(), marketing.getDeptid()) != null) {
                return ActionResult.error("该营销中心已存在");
            }
        }else{
            if (marketingService.getMarketing(marketing.getName(), marketing.getDeptid(), marketing.getId()) != null) {
                return ActionResult.error("该营销中心已存在");
            }
        }

        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());
            marketing.setAvatar(sysFile.getFileId());
        }
        marketingService.addOneMaeketing(marketing);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult getfourlist(FourDetailsParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(8);
        if (param.getDeptId() == null) {
            param.setDeptId(32);
        }
        param.setStatus(1);
        List<FourDetails> list = fourService.getFourResult(param);
        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult getfourlistrepair(FourDetailsParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(8);
        if (param.getDeptId() == null) {
            param.setDeptId(32);
        }
        param.setStatus(1);
        param.setCondit(param.getCondit());
        List<FourDetails> list = fourService.getFourResult(param);
        RefData refData = new RefData();
        refData.put("datas",list);
        refData.put("page", new PageBen(param));
        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult fourlistdel(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            pubDao.execute(SQL.deleteByIds("four_details", i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult fourrepairdel(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            pubDao.execute(SQL.deleteByIds("four_repair", i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult edit_four(FourDetails fourDetails, HttpServletRequest request) {
        ActionResult result = ActionResult.ok();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> multipartFiles = multipartRequest.getFiles("fourImgs");
            if (TypeUtils.empty(multipartFiles)) {
                return ActionResult.error("请上传台账图片");
            }
            List<String> imgs = new ArrayList<>();
            List<String> imgIds = new ArrayList<>();
            for (MultipartFile file : multipartFiles) {
                SysFile sysFile = fileApi.addFile(file, new FileParam());
                imgs.add(sysFile.getFilePath());
                imgIds.add(sysFile.getFileId());
            }

            String imgsStr = TypeUtils.listToStr(imgs),
                    imgIdsStr = TypeUtils.listToStr(imgIds);
            FourDetails oldImgs = fourDetailsService.getImgs(fourDetails.getId());
            if (oldImgs != null) {
                if (!TypeUtils.empty(oldImgs.getImgs())) {
                    imgsStr = oldImgs.getImgs() + "," + imgsStr;
                }
                if (!TypeUtils.empty(oldImgs.getImgIds())) {
                    imgIdsStr = oldImgs.getImgIds() + "," + imgIdsStr;
                }
            }
            fourDetails.setImgs(imgsStr);
            fourDetails.setImgIds(imgIdsStr);
            result.setData(imgs);
        }
        fourDetailsService.updateDetails(fourDetails);
        return result;
    }

    @Autowired
    private FourDetailsService fourDetailsService;

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private FourAddService fourAddService;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private FourService fourService;

    @Autowired
    private FileApi fileApi;

}

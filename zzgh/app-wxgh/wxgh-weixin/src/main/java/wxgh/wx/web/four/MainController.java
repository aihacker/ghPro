package wxgh.wx.web.four;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.DateUtils;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.four.AddParam;
import wxgh.data.four.AddRes;
import wxgh.data.four.DetailInfo;
import wxgh.data.four.MarketId;
import wxgh.entity.entertain.place.User;
import wxgh.entity.four.*;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.SysFile;
import wxgh.param.four.*;
import wxgh.param.pub.dept.WxDeptQuery;
import wxgh.sys.service.weixin.four.*;
import wxgh.sys.service.weixin.four.excel.FourExcelService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.WeixinDeptService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by cby on 2017/7/31.
 */
@Controller
public class MainController {
    @RequestMapping
    public void add(Model model) {
        List<Dept> depts = deptService.getCompanyList();
        model.put("depts", depts);
    }

    @RequestMapping
    public void add1(Model model) {
        List<Dept> depts = deptService.getCompanyList();
        model.put("depts", depts);
    }

    @RequestMapping
    public void all_details(Model model, FourAllDetailsParam fourAllDetailsQuery) {
        FourAllDetails allDetails = fourAllDetailsService.getAllDetails(fourAllDetailsQuery);

        List<String> imgList = new ArrayList<>();
        String imgs = allDetails.getPicture();
        if (!StrUtils.empty(imgs)) {
            String[] s = imgs.split(",");
            for (String st : s) {
                imgList.add(PathUtils.getImagePath(st));
            }
            allDetails.setPicture(null);
            allDetails.setImgs(imgList);
        }

        model.put("detailsList", allDetails);
    }

    @RequestMapping
    public void collecting3(Model model, Integer deptId, Integer type, Integer mid, Integer foshanDeptid, Integer year) {

        List<String> years = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        if (year == null) {
            year = calendar1.get(Calendar.YEAR);
        }
        for (int i = -3; i < 4; i++) {
            years.add(DateFuncs.dateTimeToStr(DateFuncs.addYear(calendar1.getTime(), i), "yyyy"));
        }

        model.put("years", years);
        model.put("year", year);

        FourDetailsParam fourDetailsQuery = new FourDetailsParam();
        fourDetailsQuery.setDeptId(deptId);
        fourDetailsQuery.setMid(mid);

        List<FourDetails> fourDetailsList = fourDetailsService.getList(fourDetailsQuery);
        List<FourDetails> newList = new ArrayList<>();

        for (FourDetails fourDetails : fourDetailsList) {
            String condit = fourDetails.getCondit();
            if(condit.equals("需要更换")){
                newList.add(fourDetails);
            }
//            String buyTime = fourDetails.getBuyTime();

            //计划更新的年限
//            if(buyTime != null){
//                String changeYear = DateUtils.getPlanTime(buyTime, fourDetails.getUsefulLife(), "yyyy");
//
////                if (changeYear.equals(String.valueOf(year))) { //如果计划更换的时间等于今年时间
////                    newList.add(fourDetails);
////                }
//                newList.add(fourDetails);
//            }
        }

        FourDetailsParam fourDetailsQuery1 = new FourDetailsParam();

        if (null == type) {
            type = 1;
        }
        fourDetailsQuery1.setType(type);

        List<DetailInfo> infoList = null;
        if (null != foshanDeptid) {
            String[] strings = weixinDeptService.getChildDeptid(32).split(",");
            List<Integer> integerList = new ArrayList<Integer>();
            for (int i = 1; i < strings.length; i++) {
                integerList.add(Integer.valueOf(strings[i]));
            }
            List<Integer> idList = new ArrayList<Integer>();
            for (FourDetails fourDetails : newList) {
                if (integerList.contains(fourDetails.getDeptId())) {
                    idList.add(fourDetails.getId());
                } else {
                    continue;
                }
            }
            if (idList.size() <= 0) {
                idList.add(-1);
            }
            fourDetailsQuery1.setIdList(idList);
            fourDetailsQuery1.setStatus(1);
            infoList = fourDetailsService.groupDetailByListIn(fourDetailsQuery1);
        } else {
            List<Integer> idList = new ArrayList<Integer>();
            for (FourDetails fourDetails : newList) {
                idList.add(fourDetails.getId());
            }
            System.out.println(idList);
            if (idList.size() > 0) {
                fourDetailsQuery1.setIdList(idList);
                fourDetailsQuery1.setStatus(1);
                infoList = fourDetailsService.groupDetailByListIn(fourDetailsQuery1);
            }
        }
        if (infoList != null) {
            double count = 0;
            double zbCount = 0;
            double flCount = 0;
            double ghCount = 0;
            for (DetailInfo info : infoList) {
                double tmp = info.getNumb() * info.getPrice();
                count += tmp;
                if ("资本投资".equals(info.getPriceSource())) {
                    zbCount += tmp;
                } else if ("福利费".equals(info.getPriceSource())) {
                    flCount += tmp;
                } else {
                    ghCount += tmp;
                }
            }
            model.put("fourCount", count);
            model.put("zbCount", zbCount);
            model.put("flCount", flCount);
            model.put("ghCount", ghCount);
        }
        model.put("infos", infoList);
    }

    @RequestMapping
    public void collecting2(Model model, Integer deptId, Integer type, Integer mid, Integer foshanDeptid, Integer year) {

        List<String> years = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        if (year == null) {
            year = calendar1.get(Calendar.YEAR);
        }
        for (int i = -3; i < 4; i++) {
            years.add(DateFuncs.dateTimeToStr(DateFuncs.addYear(calendar1.getTime(), i), "yyyy"));
        }

        model.put("years", years);
        model.put("year", year);

        FourDetailsParam fourDetailsQuery = new FourDetailsParam();
        fourDetailsQuery.setDeptId(deptId);
        fourDetailsQuery.setMid(mid);

        List<FourDetails> fourDetailsList = fourDetailsService.getList(fourDetailsQuery);
        List<FourDetails> newList = new ArrayList<>();

        for (FourDetails fourDetails : fourDetailsList) {
            String buyTime = fourDetails.getBuyTime();

            //计划更新的年限
            if(buyTime != null){
                String changeYear = DateUtils.getPlanTime(buyTime, fourDetails.getUsefulLife(), "yyyy");

                if (changeYear.equals(String.valueOf(year))) { //如果计划更换的时间等于今年时间
                    newList.add(fourDetails);
                }
            }
        }

        FourDetailsParam fourDetailsQuery1 = new FourDetailsParam();

        if (null == type) {
            type = 1;
        }
        fourDetailsQuery1.setType(type);

        List<DetailInfo> infoList = null;
        if (null != foshanDeptid) {
            String[] strings = weixinDeptService.getChildDeptid(32).split(",");
            List<Integer> integerList = new ArrayList<Integer>();
            for (int i = 1; i < strings.length; i++) {
                integerList.add(Integer.valueOf(strings[i]));
            }
            List<Integer> idList = new ArrayList<Integer>();
            for (FourDetails fourDetails : newList) {
                if (integerList.contains(fourDetails.getDeptId())) {
                    idList.add(fourDetails.getId());
                } else {
                    continue;
                }
            }
            if (idList.size() <= 0) {
                idList.add(-1);
            }
            fourDetailsQuery1.setIdList(idList);
            fourDetailsQuery1.setStatus(1);
            infoList = fourDetailsService.groupDetailByListIn(fourDetailsQuery1);
        } else {
            List<Integer> idList = new ArrayList<Integer>();
            for (FourDetails fourDetails : newList) {
                idList.add(fourDetails.getId());
            }
            System.out.println(idList);
            if (idList.size() > 0) {
                fourDetailsQuery1.setIdList(idList);
                fourDetailsQuery1.setStatus(1);
                infoList = fourDetailsService.groupDetailByListIn(fourDetailsQuery1);
            }
        }
        if (infoList != null) {
            double count = 0;
            double zbCount = 0;
            double flCount = 0;
            double ghCount = 0;
            for (DetailInfo info : infoList) {
                double tmp = info.getNumb() * info.getPrice();
                count += tmp;
                if ("资本投资".equals(info.getPriceSource())) {
                    zbCount += tmp;
                } else if ("福利费".equals(info.getPriceSource())) {
                    flCount += tmp;
                } else {
                    ghCount += tmp;
                }
            }
            model.put("fourCount", count);
            model.put("zbCount", zbCount);
            model.put("flCount", flCount);
            model.put("ghCount", ghCount);
        }
        model.put("infos", infoList);
    }

    @RequestMapping
    public ActionResult collecting(Model model, Integer deptId, Integer type, Integer mid, Integer foshanDeptid, Integer fpID) {
        List<DetailInfo> infos = null;
        Integer nums = null;
        if (mid != null) {
            if(fpID == null) {
                infos = fourDetailsService.groupDetailByMid(mid, type);
                nums = fourDetailsService.groupNumsByMid(mid, type);
            }
            else{
                infos = fourDetailsService.groupDetailByMidFpid(mid, type, fpID);
                nums = fourDetailsService.groupNumsByMidFpid(mid, type, fpID);
            }
        } else if (deptId != null) {
            if (type == null) type = 1;
            infos = fourDetailsService.groupDetail(deptId, type);
            nums = fourDetailsService.groupNumsDetail(deptId, type);
        } else if (foshanDeptid != null) {
            infos = fourDetailsService.groupDetailByFoshan(foshanDeptid, type);
            nums = fourDetailsService.getNumsByFoshan(foshanDeptid,type);
        }

        if (deptId != null) {
//            List<DeptNameId> deptNameIds = marketingService.getDepts();
//            model.put("depts", deptNameIds);
            model.put("depts", deptService.getCompanyList());
        }

        if (mid != null) {
            List<MarketId> marketIds = marketingService.getMarketIds(null);
            model.put("markets", marketIds);
        }

        model.put("infos", infos);
        model.put("nums",nums);
        return ActionResult.ok(null, infos);

    }

    @RequestMapping
    public void demand(Model model) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return;
        }

        List<Integer> depts = new ArrayList<>();
       /* if (user.getCompanyid() != null) {
            depts.add(user.getCompanyid());
        }
        if (user.getAreaid() != null) {
            depts.add(user.getAreaid());
        }*/
        if (user.getDeptid() != null) {
            depts.add(user.getDeptid());
        }

        model.put("demands", fourSmallService.getSmalls(new FourSmallParam(depts)));
    }

    @RequestMapping
    public void detailelist1(Model model) {
        List<Dept> list = deptService.getCompanyList();
        WxDeptQuery query = new WxDeptQuery();
        query.setDeptid(1);
        Dept dept = weixinDeptService.getDept(query);
        list.add(dept);
        model.put("list", list);
    }

    @RequestMapping
    public void detailed(Model model) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return;
        }

        FourDetailsParam query = new FourDetailsParam();
        query.setDeptId(user.getDeptid());
        model.put("details", fourDetailsService.getDetails(query));
    }

    @RequestMapping
    public void detailelist(Model model) {
        WxDeptQuery query = new WxDeptQuery();
        query.setParentid(1);
        model.put("depts", weixinDeptService.getDepts(query));
    }

    @RequestMapping
    public void detailelist2(Model model, QueryMarketingParam queryMarketing) {
        List<Marketing> marketings = marketingService.getList(queryMarketing);
        for (int i = 0; i < marketings.size(); i++) {
            String avatar = marketings.get(i).getAvatar();
            if (!StrUtils.empty(avatar)) {
                avatar = PathUtils.getImagePath(avatar);
            }
            marketings.get(i).setAvatar(avatar);
        }
        model.put("list", marketings);
        WxDeptQuery wxDeptQuery = new WxDeptQuery();
        wxDeptQuery.setDeptid(queryMarketing.getDeptid());
        model.put("deptname", weixinDeptService.getDept(wxDeptQuery).getName());
        model.put("deptid", queryMarketing.getDeptid());
    }

    @RequestMapping
    public void detailelist3(Model model, FourDetailsParam fourDetailsQuery) {
        model.put("list", fourDetailsService.getMarketFour(fourDetailsQuery));
        model.put("getmarketName",fourDetailsService.getMarketName(fourDetailsQuery));
        model.put("mid", fourDetailsQuery.getMid());
    }

    @RequestMapping
    public void details_item2(Model model, FourDetailsParam fourDetailsQuery) {
        List<FourDetails> detailses = new ArrayList<FourDetails>();
        Integer type = fourDetailsQuery.getType();

        fourDetailsQuery.setStatus(1);
        if (type == null) type = 5;
        switch (type) {
            case 1:
                fourDetailsQuery.setFpcName(fourDetailsQuery.getTypeName());
                detailses = fourDetailsService.getList(fourDetailsQuery);
                break;
            case 2:
                fourDetailsQuery.setBrand(fourDetailsQuery.getTypeName());
                detailses = fourDetailsService.getList(fourDetailsQuery);
                break;
            case 3:
                fourDetailsQuery.setCondit(fourDetailsQuery.getTypeName());
                detailses = fourDetailsService.getList(fourDetailsQuery);
                break;
            case 4:
                fourDetailsQuery.setCondStr(fourDetailsQuery.getTypeName());
                detailses = fourDetailsService.getList(fourDetailsQuery);
                break;
            default:
                detailses = fourDetailsService.getPerFourProject(fourDetailsQuery);
                break;
        }

        model.put("list", detailses);
    }

    @RequestMapping
    public void index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("userList");
        if (user == null) {
            return;
        }

        List<Integer> depts = new ArrayList<>();
        if (user.getCompanyid() != null) {
            depts.add(user.getCompanyid());
        }
        if (user.getAreaid() != null) {
            depts.add(user.getAreaid());
        }
        if (user.getDeptid() != null) {
            depts.add(user.getDeptid());
        }

        FourDetailsParam query = new FourDetailsParam(depts);
        query.setIsLimit(true);
        model.put("fours", fourDetailsService.getDetails(query));
    }

    @RequestMapping
    public void repair(Model model, FourDetailsParam fourDetailsQuery, String year) {
        Integer type = fourDetailsQuery.getType();
        if (null == type) {
            type = 1;
        }
        switch (type) {
            case 1:
                fourDetailsQuery.setFpcName(fourDetailsQuery.getTypeName());
                break;
            case 2:
                fourDetailsQuery.setBrand(fourDetailsQuery.getTypeName());
                break;
            case 3:
                fourDetailsQuery.setCondit(fourDetailsQuery.getTypeName());
                break;
            case 4:
                fourDetailsQuery.setCondStr(fourDetailsQuery.getTypeName());
                break;
            default:
                break;
        }
        List<FourDetails> fourDetailsList = fourDetailsService.getList(fourDetailsQuery);
        List<FourDetails> newList = new ArrayList<FourDetails>();
        Calendar calendar = Calendar.getInstance();
        if (StrUtils.empty(year)) {
            year = String.valueOf(calendar.get(Calendar.YEAR));
        }

        for (FourDetails fourDetails : fourDetailsList) {
            String buyTime = fourDetails.getBuyTime();

            //计划更新的年限
            String changeYear = DateUtils.getPlanTime(buyTime, fourDetails.getUsefulLife(), "yyyy");

            if (changeYear.equals(String.valueOf(year))) { //如果计划更换的时间等于今年时间
                newList.add(fourDetails);
            }
        }

        if (null != fourDetailsQuery.getFoshanDeptid()) {
            List<FourDetails> fourDetailsList1 = new ArrayList<FourDetails>();
            String[] strings = weixinDeptService.getChildDeptid(32).split(",");
            List<Integer> integerList = new ArrayList<Integer>();
            for (int i = 1; i < strings.length; i++) {
                integerList.add(Integer.valueOf(strings[i]));
            }
            for (FourDetails fourDetails : newList) {
                if (integerList.contains(fourDetails.getDeptId())) {
                    fourDetailsList1.add(fourDetails);
                } else {
                    continue;
                }
            }
            model.put("list", fourDetailsList1);
        } else {
            model.put("list", newList);
        }
    }

    @RequestMapping
    public void ruku(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
        List<Dept> depts = deptService.getCompanyList();
        model.put("depts", depts);
    }

    @RequestMapping
    public void search_dept(Model model) {
//        model.put("list", fourDetailsService.getdeptStrList());
    }

    @RequestMapping
    public void search_item(Model model, String deptStr) {
        model.put("deptStr", deptStr);
    }

    @RequestMapping
    public void show(Model model, FourDetailsParam fourDetailsQuery) {
        FourDetails fourDetails = fourDetailsService.getOne(fourDetailsQuery);

        if(fourDetails != null){
            if (fourDetails.getBuyTime() != null) {
                fourDetails.setPanUpdateStr(DateUtils.getPlanTime(fourDetails.getBuyTime(), fourDetails.getUsefulLife(), "yyyy"));
            }

            String imgs = fourDetails.getPath();
            List<String> imgList = TypeUtils.strToList(imgs);

            System.out.println(fourDetails.toString());

            model.put("four", fourDetails);
            model.put("imgList", imgList);
            if(fourDetails.getThumb() != null)
                model.put("thumbList", TypeUtils.strToList(fourDetails.getThumb()));
        }
    }

    @RequestMapping
    public ActionResult search(FourDetailsParam fourDetailsQuery) {
//        fourDetailsQuery.setDeptId(null);
        return ActionResult.ok(null, fourDetailsService.getList(fourDetailsQuery));
    }

    @RequestMapping
    public ActionResult add_propagate(FourPropagate propagate) {
        SeUser user = UserSession.getUser();
        if (user != null) {
            propagate.setApplyTime(new Date());
            propagate.setUserid(user.getUserid());
            propagate.setStatus(0);
            propagate.setDeptId(user.getDeptid());
            propagate.setAddIs(0);

            if (propagate.getFpcId() == null && !StrUtils.empty(propagate.getFpcName())) {
                FourProjectContent content = new FourProjectContent();
                content.setFpId(propagate.getFpId());
                content.setName(propagate.getFpcName());
                projectContentService.insertOrUpdateCnt(content);

                propagate.setFpcId(content.getId());
            }

            if(propagate.getFpId() == 0)
                return ActionResult.error("项目内容不能为空");

            // 项目内容id为空
            if(propagate.getFpcId() == 0){
                if(com.libs.common.type.TypeUtils.empty(propagate.getFpcName()))
                    return ActionResult.error("项目内容为空");

                Integer fpcId = fourExcelService.insertProjectContent(propagate.getFpId(), propagate.getFpcName());
                propagate.setFpcId(fpcId);
            }

            fourPropagateService.addPropagete(propagate);
        } else {
            return ActionResult.error("请登录后再试哦");
        }
        return ActionResult.ok();
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
        if(param.getName() != null)
            res = fourAddService.byFpAndMarket(param.getMid(), param.getFpId(), param.getName());
        else
            res = fourAddService.byFpAndMarket(param.getMid(), param.getFpId());
        return ActionResult.ok(null, res);
    }

    @RequestMapping
    public ActionResult list_brand(AddParam param) {
        List<AddRes> res = fourAddService.byFpAndMarktAndFpc(param);
        return ActionResult.ok(null, res);
    }

    @RequestMapping
    public ActionResult list_modal(AddParam param) {
        List<AddRes> res = fourAddService.byFpAndMarktAndFpcAndBrand(param);
        return ActionResult.ok(null, res);
    }

    @RequestMapping
    public ActionResult add_fpc(FourProjectContent content) {
        content.setName(content.getName().trim());
        projectContentService.insertOrUpdateCnt(content);
        return ActionResult.ok(null, content.getId());
    }

    @RequestMapping
    public ActionResult list_out(Integer id) {
        FourListParam query = new FourListParam();
        query.setMid(id);
        List<FourDetails> detailss = fourDetailsService.getRepair(query);
        List<FourDetails> newList = new ArrayList<>();

        Calendar calendar1 = Calendar.getInstance();
        Integer year = calendar1.get(Calendar.YEAR);

        for (FourDetails fourDetails : detailss) {
            String buyTime = fourDetails.getBuyTime();

            //计划更新的年限
            String changeYear = DateUtils.getPlanTime(buyTime, fourDetails.getUsefulLife(), "yyyy");

            if (changeYear.equals(String.valueOf(year))) { //如果计划更换的时间等于今年时间
                newList.add(fourDetails);
            }
        }
        return ActionResult.ok(null, newList);
    }

    @RequestMapping
    public ActionResult detaile_list(Integer deptid) {
        FourDetailsParam query = new FourDetailsParam();
        query.setThedeptid(deptid);
        List<FourDetails> detailses = fourDetailsService.getDetails(query);
        return ActionResult.ok(null, detailses);
    }

    @RequestMapping
    public ActionResult get(Integer parentid) {
        WxDeptQuery wxDeptQuery = new WxDeptQuery();
        wxDeptQuery.setParentid(parentid);
        List<Dept> depts = weixinDeptService.getDepts(wxDeptQuery);
        return ActionResult.ok(null, depts);
    }

    @RequestMapping
    public ActionResult search_d(Model model, String deptName) {
        ActionResult result = ActionResult.ok();
        result.setData(fourDetailsService.searchDeptByDeptName(deptName));
//        model.put("list", fourDetailsService.searchDeptByDeptName(deptName));
        return result;
    }

    @RequestMapping
    public ActionResult search_i(Model model, String name, String deptStr) {
        ActionResult result = ActionResult.ok();
        result.setData(fourDetailsService.searchItemByName(name, deptStr));
//        model.put("list", fourDetailsService.searchDeptByDeptName(deptName));
        return result;
    }

    @RequestMapping
    public ActionResult add_ruku(FourDetails details) throws IOException {

        if(details.getFpId() == 0)
            return ActionResult.error("项目内容不能为空");

        if (com.libs.common.type.TypeUtils.empty(details.getDeptName()))
            return ActionResult.error("分公司不能为空");

        //上传四小图片
        SeUser user = UserSession.getUser();
        if (!StrUtils.empty(details.getImgs())) {
            String[] imgs = details.getImgs().split(",");
            List<String> upImgs = new ArrayList<>();
            for (String img : imgs) {
                fileApi.wxDownload(img, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        upImgs.add(file.getFileId());
                    }
                });

        }
            details.setImgs(TypeUtils.listToStr(upImgs));
        }

        details.setRepairCount(0);
        details.setUserId(user.getUserid());

//        Dept dept = deptService.getComparny(user.getDeptid());
//        if(dept != null)
//            details.setDeptId(dept.getDeptid());

        details.setDeptId(Integer.valueOf(details.getDeptName()));
        details.setTime(new Date());
        details.setStatus(0); //未审核

        // 项目内容id为空
        if(details.getFpcId() == 0){

            if(com.libs.common.type.TypeUtils.empty(details.getFpcName()))
                return ActionResult.error("项目内容为空");

            Integer fpcId = fourExcelService.insertProjectContent(details.getFpId(), details.getFpcName());
            details.setFpcId(fpcId);
        }

        fourDetailsService.addFourDetail(details);
        return ActionResult.ok();
    }

    @Autowired
    private DeptService deptService;

    @Autowired
    private WeixinDeptService weixinDeptService;

    @Autowired
    private MarketingService marketingService;

    @Autowired
    private FourAddService fourAddService;

    @Autowired
    private ProjectContentService projectContentService;

    @Autowired
    private FourPropagateService fourPropagateService;

    @Autowired
    private FourDetailsService fourDetailsService;

    @Autowired
    private FourExcelService fourExcelService;

    @Autowired
    private FourAllDetailsService fourAllDetailsService;

    @Autowired
    private FourSmallService fourSmallService;

    @Autowired
    private FileApi fileApi;

}

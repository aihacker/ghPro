package wxgh.wx.web.union.innovation.result;


import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.entity.union.innovation.InnovateMicro;
import wxgh.entity.union.innovation.InnovateShop;
import wxgh.entity.union.innovation.WorkResult;
import wxgh.param.union.innovation.QueryInnovateMicro;
import wxgh.param.union.innovation.QueryInnovateShop;
import wxgh.param.union.innovation.work.WorkResultParam;
import wxgh.param.union.innovation.work.WorkResultQuery;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.union.innovation.InnovateMicroService;
import wxgh.sys.service.weixin.union.innovation.InnovateShopService;
import wxgh.sys.service.weixin.union.innovation.WorkResultService;
import wxgh.sys.service.weixin.union.innovation.WorkUserService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *----------------------------------------------------------
 * @Description 成果展示
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 16:31
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private WorkResultService workResultService;

    @Autowired
    private WorkUserService workUserService;

    @Autowired
    private InnovateMicroService innovateMicroService;

    @Autowired
    private InnovateShopService innovateShopService;

    /**
     * 展示首页
     * @param model
     */
    @RequestMapping
    public void index(Model model, WorkResultQuery query){
        query.setWorkType(3);
        query.setStatus(1);
        List<WorkResult> workResultList = workResultService.resultList(query);
        if(workResultList != null)
        for (WorkResult workResult : workResultList) {
            String content = workResult.getContent();
            Map map = JsonUtils.parseMap(content, String.class, Object.class);
            ArrayList imgs = (ArrayList) map.get("imgs");
            String img = "";
            if (imgs.size() > 0) {
                Map map2 = (Map) imgs.get(0);
                img = (String) map2.get("url");
            }
            workResult.setImgAvatar(PathUtils.getImagePath(img));
        }
        model.put("results", workResultList);
    }

    /**
     * 分页数据u
     * @param workResultParam
     * @return
     */
    @RequestMapping
    public ActionResult list(WorkResultParam workResultParam){
        workResultParam.setPageIs(true);
        workResultParam.setRowsPerPage(10);
        return ActionResult.okRefresh(workResultService.resultList(workResultParam), workResultParam);
    }

    /**
     * 成果详情
     * @param model
     * @param query
     */
    @RequestMapping
    public void detail(Model model, QueryInnovateMicro query) throws UnsupportedEncodingException {
        InnovateMicro innovateMicro = innovateMicroService.getOne(query);
        if(innovateMicro == null)
            return ;
        model.put("data", innovateMicro);
        WorkUserQuery workUserQuery = new WorkUserQuery();
        workUserQuery.setWorkId(innovateMicro.getId());
        workUserQuery.setStatus(null);
        workUserQuery.setWorkType(3);
        model.put("users", workUserService.getUsers(workUserQuery));

        String content = innovateMicro.getContent();
        Map map = JsonUtils.parseMap(content, String.class, Object.class);
        ArrayList imgs = (ArrayList) map.get("imgs");
        String txt = (String) map.get("txt");
        List<String> imgList = new ArrayList<String>();
        for (int i = 0; i < imgs.size(); i++) {
            Map map2 = (Map) imgs.get(i);
            String img = (String) map2.get("url");
            imgList.add(URLDecoder.decode(img, "UTF-8"));
        }
        model.put("imgList", imgList);
        model.put("txt", URLDecoder.decode(txt, "UTF-8"));
        model.put("showType", query.getShowType());
    }

    /**
     * 详情成员
     * @param model
     * @param id
     * @param workType
     * @throws UnsupportedEncodingException
     */
    @RequestMapping
    public void member(Model model, Integer id , Integer workType) throws UnsupportedEncodingException {

        if (workType == 2){
            QueryInnovateShop queryInnovateShop = new QueryInnovateShop();
            queryInnovateShop.setId(id);
            InnovateShop innovateShop = innovateShopService.getOne(queryInnovateShop);
            model.put("data", innovateShop);
            WorkUserQuery workUserQuery = new WorkUserQuery();
            workUserQuery.setWorkId(innovateShop.getId());
            workUserQuery.setStatus(null);
            workUserQuery.setWorkType(2);
            model.put("users", workUserService.getUsers(workUserQuery));

        }else if (workType == 3){
            QueryInnovateMicro queryInnovateMicro = new QueryInnovateMicro();
            queryInnovateMicro.setId(id);
            InnovateMicro innovateMicro = innovateMicroService.getOne(queryInnovateMicro);
            model.put("data", innovateMicro);
            WorkUserQuery workUserQuery = new WorkUserQuery();
            workUserQuery.setWorkId(innovateMicro.getId());
            workUserQuery.setStatus(null);
            workUserQuery.setWorkType(3);
            model.put("users", workUserService.getUsers(workUserQuery));
        }

    }

}

package wxgh.wx.web.union.innovation.honor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.data.union.innovation.honor.HonorData;
import wxgh.entity.tribe.TribeActJoin;
import wxgh.param.union.innovation.honor.HonorParam;
import wxgh.sys.service.weixin.union.innovation.InnovationHonorService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 荣誉墙
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
    private InnovationHonorService honorService;

    @RequestMapping
    public void index(Model model, HonorParam honorParam){
        model.put("results", honorService.list(honorParam));
    }

    /**
     * 测试列表数据
     * @param honorParam
     * @return
     */
    @RequestMapping
    public ActionResult list(HonorParam honorParam){
        honorParam.setPageIs(true);
        honorParam.setRowsPerPage(10);
        List<HonorData> joins = honorService.list(honorParam);
        return ActionResult.okRefresh(joins, honorParam);
    }

}

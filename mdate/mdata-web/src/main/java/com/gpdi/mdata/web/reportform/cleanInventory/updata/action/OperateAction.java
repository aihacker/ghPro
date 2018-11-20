package com.gpdi.mdata.web.reportform.cleanInventory.updata.action;

import com.gpdi.mdata.sys.service.reportform.cleanInventory.InventoryExamineService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/7 20:52
 * @description:根据一段时间更新数据
 */
@Controller
public class OperateAction {
    protected static final Logger log = Logger.getLogger(OperateAction.class);
    @Autowired
    InventoryExamineService inventService;
    @RequestMapping
    public ActionResult updata(HttpServletRequest request){
       String startTime = request.getParameter("startTime");
       String endTime = request.getParameter("endTime");
       System.out.println("startTime:"+startTime+"+++endTime:"+endTime);

       if (startTime !=null && endTime !=null){
           inventService.save(startTime,endTime);
       }else {
           log.info("没有获取到相应的参数!");
           return ActionResult.fail("没获取到相应的参数");
       }
        return ActionResult.ok("正在更新数据");
    }
}

package com.gpdi.mdata.web.reportform.daoexcel.updata.action;

import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.types.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:根据一段时间更新数据
 * @author: WangXiaoGang
 * @data: Created in 2018/7/28 14:08
 * @modifier:
 */
@Controller
public class OperateAction {
    protected static final Logger log = Logger.getLogger(OperateAction.class);
    @Autowired
    ExtractTextFromXLSService extract;
    @RequestMapping
    public ActionResult updata(HttpServletRequest request){
       String startTime = request.getParameter("startTime");
       String endTime = request.getParameter("endTime");
       System.out.println("startTime:"+startTime+"+++endTime:"+endTime);


       if (startTime !=null && endTime !=null){
           extract.save(startTime,endTime);
       }else {
           log.info("没有获取到相应的参数!");
           return ActionResult.fail("没获取到相应的参数");
       }
        return ActionResult.ok("正在更新数据");
    }
}

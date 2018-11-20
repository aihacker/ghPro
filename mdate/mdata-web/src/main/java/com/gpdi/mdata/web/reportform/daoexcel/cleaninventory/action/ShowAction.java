package com.gpdi.mdata.web.reportform.daoexcel.cleaninventory.action;

import com.gpdi.mdata.sys.entity.report.CleanResult;
import com.gpdi.mdata.sys.entity.report.ResultCode;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanCallBackService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode.CallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

import java.util.List;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/31 3:32
 * @modifier:即时清结模板二回调
 */
@Controller
public class ShowAction {
    private static final Logger logger = LoggerFactory.getLogger(ShowAction.class);
    @Autowired
    private CleanCallBackService cleanCallBackService;

    @RequestMapping
    public void execute() {
        //
    }

    @RequestMapping
    public ActionResult save(String code) {
        System.out.println("机器人传过来的参数:"+code);
      // String str = java.net.URLDecoder.decode(code,"UTF-8");;
       // System.out.println("机器人传过来的参数中文乱码处理:"+str);
        if (code !=null && code !=""){
            int num = Integer.parseInt(code);
            List<CleanResult> list = cleanCallBackService.getCompanys(num);
            if (list !=null && list.size()>0){
                logger.info("从中间表取数成功");
             boolean result =cleanCallBackService.saveRsult(list);
             if (result){
                 logger.info("保存中间表成功!");
             }
            }
        }else {
            logger.error("未接受到传过来的参数或者参数为空，取数编号："+code);
            return ActionResult.fail("取数失败，未接受到机器人传过来的参数或者参数为空");
        }
        return ActionResult.ok("取数成功");
    }


}


//http://127.0.0.1:8090/mdata/reportform/daoexcel/purchase/show.json?action=save(1212)
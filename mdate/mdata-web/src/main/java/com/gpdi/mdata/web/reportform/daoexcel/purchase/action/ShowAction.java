package com.gpdi.mdata.web.reportform.daoexcel.purchase.action;

import com.gpdi.mdata.sys.dao.report.ResultCodeDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.report.ResultCode;
import com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode.CallBackService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/31 3:32
 * @modifier:模板二回调
 */
@Controller
public class ShowAction {
    private static final Logger logger = LoggerFactory.getLogger(ShowAction.class);
    @Autowired
    private CallBackService callBackService;

    @RequestMapping
    public void execute() {
        //
    }

    @RequestMapping
    public ActionResult save(String code) {
        System.out.println("机器人传过来的参数:"+code);
      // String str = java.net.URLDecoder.decode(code,"UTF-8");;
       // System.out.println("机器人传过来的参数中文乱码处理:"+str);
        if (code !=null ){
            List<ResultCode> list = callBackService.getCompanys(code);
            if (list !=null && list.size()>0){
                logger.info("从中间表取数成功");
             //保存候选供应商
             boolean result =callBackService.saveRsult(list);

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
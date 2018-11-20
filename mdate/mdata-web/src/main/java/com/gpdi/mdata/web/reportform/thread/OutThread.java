package com.gpdi.mdata.web.reportform.thread;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ThreadService;
import com.gpdi.mdata.web.utils.HttpClientsUtil;
import com.gpdi.mdata.web.utils.UrlStringUtil;
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

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:外网线程
 * @author: WangXiaoGang
 * @data: Created in 2018/7/30 6:00
 * @modifier:
 */
public class OutThread extends Thread{
   private static final Logger logger = LoggerFactory.getLogger(OutThread.class);
    @Autowired
    ThreadService threadService;
    private List<PurchaseContract> list = new LinkedList<>();

    public OutThread(List<PurchaseContract> list) {
        this.list = list;
    }

    public void run(){
        System.out.println("连接外网线程");
        for (PurchaseContract purchase : list) {
            String companys = purchase.getSupplierName();//获取供应商名称
            System.out.println("输出供应商名称:"+companys);

            //String adss = "http://127.0.0.1:99/pachong";
            String adss = "http://10.17.138.249:99/pachong";
            String param = 	"comName="+companys;
            String result = HttpClientsUtil.get(param,adss);
            //String result = UrlStringUtil.getURLContent(url);
            logger.info("返回外网爬虫取数结果:"+result);
            if (result !=null && (result.equals("Ok") || result.equals("Fail") )){
                logger.info("外网机器人取数成功: "+companys);
                continue;
             }else{
                logger.info("外网机器人取数失败: "+companys);
                break;
             }
        }
      logger.info("外网机器人完成全部取数!");

    }




}

package com.gpdi.mdata.web.reportform.thread;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * @description:根据结果单号获取候选供应商
 * @author: WangXiaoGang
 * @data: Created in 2018/7/30 6:00
 * @modifier:
 */
public class ResultCodeThread extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(ResultCodeThread.class);
    @Autowired
    ThreadService threadService;

    @Autowired
    ExtractTextFromXLSService extractTextFromXLSService;
    private List<PurchaseContract> list = new LinkedList<>();

    public ResultCodeThread(List<PurchaseContract> list) {
        this.list = list;
    }

    public void run(){
        for (PurchaseContract purchase : list) {
            String resultCode = purchase.getPurchaseResultCode();//获取采购结果单号
            boolean result=true;
            try {
                 result = threadService.insertData(resultCode);
                 //result = extractTextFromXLSService.insertData(resultCode);
                Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("插入机器人中间表失败!");
            }
            if (result){
                continue;
            }else {
                break;
            }


        }
    }


}

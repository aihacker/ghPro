package com.gpdi.mdata.web.reportform.task.accountimport;


import com.gpdi.mdata.sys.entity.report.BidCompanyInfo;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import com.gpdi.mdata.sys.service.reportform.daoexcel.PurchaseParentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.spring.bean.BeanUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/7/24 16:18
 * @description:公开采购投标公司表导入excel后处理数据任务
 */
public class ExcelBidCompanyHandleTask implements Callable<Boolean>,Cloneable {

    private static final Logger logger = LoggerFactory.getLogger(ExcelBidCompanyHandleTask.class);

    private List<BidCompanyInfo> list = new LinkedList<>();
    private Integer parentId;

    public ExcelBidCompanyHandleTask(List<BidCompanyInfo> list, Integer parentId) {
        this.list = list;
        this.parentId = parentId;
    }

    @Override
    public ExcelBidCompanyHandleTask clone() throws CloneNotSupportedException {
        return (ExcelBidCompanyHandleTask)super.clone();
    }


    private void processFail(){
        PurchaseParentService parentService = BeanUtils.getBean(PurchaseParentService.class);
        if(parentService != null){
            PurchaseParent parent = parentService.get(parentId);
            if(parent != null){
                parent.setState(PurchaseParent.STATE_FAILED);
                parent.setRemark("导入失败,服务器异常.");
                parentService.save(parent);
            }
        }
    }

    private void processSucess(){
        PurchaseParentService parentService = BeanUtils.getBean(PurchaseParentService.class);
        if(parentService != null){
            PurchaseParent parent = parentService.get(parentId);
            if(parent != null){
                parent.setState(PurchaseParent.STATE_SUCCESS);
                parent.setRemark("导入成功");
                parentService.save(parent);
            }
        }
    }

    /*
    * 保存excel表中的数据，并且根据规则进行分工
    * */
    @Override
    public Boolean call() {
        try{
            PurchaseParentService parentService = BeanUtils.getBean(PurchaseParentService.class);
            if(parentService != null){
                boolean result = parentService.saveBidCompanyInfoHandle(list,parentId);
                if(result){
                    //parentService.analyseWithRegular(parentId);
                    processSucess();
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(this.getClass()+".saveBidCompanyInfoHandle() -- 处理任务失败.");
        }
        processFail();
        return false;
    }

}

package com.gpdi.mdata.web.reportform.daoexcel.entities.bidcominfo;
import com.gpdi.mdata.sys.entity.report.BidCompanyInfo;
import org.apache.log4j.Logger;
import java.sql.Date;
import java.util.List;
/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/7 15:00
 * @description:
 * List<ExcelColumn> : 要读取的列索引
 * 将对应的列索引的数据保存到ExcelColumnToBidCompanyInfo对象中
 */

public class ExcelColumnToBidCompanyInfo {

    private static Logger logger = Logger.getLogger(BidCompanyInfo.class);

    private List<ExcelColumn> list;

    public ExcelColumnToBidCompanyInfo(List<ExcelColumn> list) {
        this.list = list;
    }
    ExcelBidCompanyInfo excBid = new ExcelBidCompanyInfo();
    public BidCompanyInfo parse(List<String> column){//获取每一列的数据
        BidCompanyInfo bidInfo = new BidCompanyInfo();

        for (int i=0;i<list.size();i++){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);
            //System.out.println("value的值:"+value);
            ExcelColumn excelColumn= list.get(i);
            if(excelColumn == ExcelColumn.project_name){//电信项目名称
                if (value !=null && !value.equals("")){
                    excBid.setProjectName(value);
                    bidInfo.setProjectName(value);
                }else {
                    bidInfo.setProjectName(excBid.getProjectName());
                }
            }else if(excelColumn == ExcelColumn.project_code){//采购项目编码
                if (value !=null && !value.equals("")){
                    excBid.setProjectCode(value);
                    bidInfo.setProjectCode(value);
                }else {
                    bidInfo.setProjectCode(excBid.getProjectCode());
                }
            }else if(excelColumn == ExcelColumn.purchase_way){//采购方式
                if (value !=null && !value.equals("")){
                    excBid.setPurchaseWay(value);
                    bidInfo.setPurchaseWay(value);
                }else {
                    bidInfo.setPurchaseWay(excBid.getPurchaseWay());
                }
            }else if(excelColumn == ExcelColumn.opening_time){//开标时间
                if (value !=null && !value.equals("")){
                    excBid.setOpeningTime(value);
                    bidInfo.setOpeningTimeStr(value);
                }else {
                    bidInfo.setOpeningTimeStr(excBid.getOpeningTime());
                }
            }else if(excelColumn == ExcelColumn.tendering_company){//投标公司名称
                bidInfo.setTenderingCompany(value);
            }else if(excelColumn == ExcelColumn.credit_code){//公司社会信用编码
                bidInfo.setCreditCode(value);
            }else if(excelColumn == ExcelColumn.is_it_eligible){//是否符合条件
                bidInfo.setIsItEligible(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return bidInfo;
    }
}

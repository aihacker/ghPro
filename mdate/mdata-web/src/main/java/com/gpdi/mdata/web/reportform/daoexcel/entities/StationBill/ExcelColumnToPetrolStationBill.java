package com.gpdi.mdata.web.reportform.daoexcel.entities.StationBill;


import com.gpdi.mdata.sys.entity.report.MachineBill;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.daoexcel.refuelbill.action.QueryAction;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.NEW;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gan
 * @date 2018/4/19
 * @using
 *      List<ExcelColumn> : 要读取的列索引
 *      将对应的列索引的数据保存到ExcelPurchaseContract对象中
 */
public class ExcelColumnToPetrolStationBill {

    private static Logger logger = Logger.getLogger(ExcelPurchaseBill.class);

    private List<ExcelColumnBill> list;


    public ExcelColumnToPetrolStationBill(List<ExcelColumnBill> list) {
        this.list = list;
    }

        public MachineBill parse(List<String> column){
        MachineBill purchase = new MachineBill();
        for (int i=0;i<list.size();i++){
        //for(ExcelColumn excelColumn:list){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);
            /*int kh_value=Integer.valueOf(column.get(i)).intValue();*/

            System.out.println("value的值:"+value);
            ExcelColumnBill excelColumn= list.get(i);
            if(excelColumn == ExcelColumnBill.card_number){//卡号
                purchase.setCardNumber(value);
            }else if(excelColumn == ExcelColumnBill.date){//时间
                purchase.setDate(value);
            }else if(excelColumn == ExcelColumnBill.business_type){//业务类型
                purchase.setBusinessType(value);
            }else if(excelColumn == ExcelColumnBill.variety){//品种
                purchase.setVariety(value);
            }else if(excelColumn == ExcelColumnBill.number){//数量
                purchase.setNumber(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.unit_price){//单价
                purchase.setUnitPrice(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.sum){//金额
                purchase.setSum(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.reward_points){//奖励分值
                purchase.setRewardPoints(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.preferential_price){//优惠价
                purchase.setPreferentialPrice(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.balance){//余额
                purchase.setBalance(Double.parseDouble(value));
            }else if(excelColumn == ExcelColumnBill.site){//地点
                purchase.setSite(value);
            }else if(excelColumn == ExcelColumnBill.operator){//操作员
                purchase.setOperator(value);
            }else if(excelColumn == ExcelColumnBill.remark){//备注
                purchase.setRemark(value);
            }else if(excelColumn == ExcelColumnBill.itle_id){//title_id
                purchase.setTitleId(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return purchase;
    }
}

package com.gpdi.mdata.web.reportform.daoexcel.entities.clean;


import com.gpdi.mdata.sys.entity.report.CleanInventory;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author wangxiaogang
 * @date 2018/4/19
 * @using
 *      List<ExcelColumn> : 要读取的列索引
 *      将对应的列索引的数据保存到ExcelPurchaseContract对象中
 */
public class ExcelColumnToCleanInventory {

    private static Logger logger = Logger.getLogger(ExcelCleanInventory.class);

    private List<ExcelColumn> list;

    public ExcelColumnToCleanInventory(List<ExcelColumn> list) {
        this.list = list;
    }

    public CleanInventory parse(List<String> column){//获取每一列的数据
        CleanInventory purchase = new CleanInventory();
        for (int i=0;i<list.size();i++){
        //for(ExcelColumn excelColumn:list){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);
            //System.out.println("value的值:"+value);
            ExcelColumn excelColumn= list.get(i);
            if(excelColumn == ExcelColumn.flow_path){//合同编号
                purchase.setFlowPath(value);
            }else if(excelColumn == ExcelColumn.title){//定稿时间
                purchase.setTitle(value);
            }else if(excelColumn == ExcelColumn.iscm_number){//合同名称
                purchase.setIscmNumber(value);
            }else if(excelColumn == ExcelColumn.flow_path_number){//合同金额
                if (value ==null || value.equals("") || value.length()<0){ value="0"; }
                purchase.setFlowPathNumber(value);
            }else if(excelColumn == ExcelColumn.clean_advance){//承办单位
                purchase.setCleanAdvance(value);
            }else if(excelColumn == ExcelColumn.supplier_code){//承办部门
                purchase.setSupplierCode(value);
            }else if(excelColumn == ExcelColumn.supplier_name){//承办人
                purchase.setSupplierName(value);
            }else if(excelColumn == ExcelColumn.material_code){//供应商
                purchase.setMaterialCode(value);
            }else if(excelColumn == ExcelColumn.material_name){//采购方式
                if (value ==null || value.equals("") || value.length()<0){
                    value="0";
                }else{
                    int a = value.indexOf(";");
                    if (a !=-1){
                        value = value.split("\\;")[0];
                    }
                }
                purchase.setMaterialName(value);
            }else if(excelColumn == ExcelColumn.unit_measurement){//采购类型
                purchase.setUnitMeasurement(value);
            }else if(excelColumn == ExcelColumn.transaction_price){//公司代码
                purchase.setTransactionPrice(Double.valueOf(value));
            }else if(excelColumn == ExcelColumn.turnover){//采购种类
                purchase.setTurnover(Double.valueOf(value));
            }else if(excelColumn == ExcelColumn.trading_volume){//设备类型/服务
                purchase.setTradingVolume(Double.valueOf(value));
            }else if(excelColumn == ExcelColumn.draft_unit){//设备类型第一层
                purchase.setDraftUnit(value);
            }else if(excelColumn == ExcelColumn.draft_people){//设备类型第二层
                purchase.setDraftPeople(value);
            }else if(excelColumn == ExcelColumn.draft_department){//签约类型
                purchase.setDraftDepartment(value);
            }else if(excelColumn == ExcelColumn.purchase_amount){//是否关联交易
                purchase.setPurchaseAmount(value);
            }else if(excelColumn == ExcelColumn.draft_time){//关联交易类型
                purchase.setDraftTime(value);
            }else if(excelColumn == ExcelColumn.procurement_method){//是否itc项目
                purchase.setProcurementMethod(value);
            }else if(excelColumn == ExcelColumn.whether_open_purchasing){//是否属于关键合同
                purchase.setWhetherOpenPurchasing(value);
            }else if(excelColumn == ExcelColumn.procurement_classification){//
                purchase.setProcurementClassification(value);
            } else if(excelColumn == ExcelColumn.capital_source){//收付类型
                purchase.setCapitalSource(value);
            }else if(excelColumn == ExcelColumn.newest_time){//合同类型
                purchase.setNewestTime(value);
            }else if(excelColumn == ExcelColumn.procurement_submit_time){//采购结果编号
                purchase.setProcurementSubmitTime(value);
            }else if(excelColumn == ExcelColumn.current_state){//合同属性
                purchase.setCurrentState(value);
            }else if(excelColumn == ExcelColumn.current_handler){//归档日期
                purchase.setCurrentHandler(value);
            }else if(excelColumn == ExcelColumn.product_service_type){//合同状态
                purchase.setProductServiceType(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return purchase;
    }
}

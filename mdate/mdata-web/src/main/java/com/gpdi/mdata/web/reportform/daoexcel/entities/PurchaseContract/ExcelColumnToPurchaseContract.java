package com.gpdi.mdata.web.reportform.daoexcel.entities.PurchaseContract;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author wangxiaogang
 * @date 2018/4/19
 * @using
 *      List<ExcelColumn> : 要读取的列索引
 *      将对应的列索引的数据保存到ExcelPurchaseContract对象中
 */
public class ExcelColumnToPurchaseContract {

    private static Logger logger = Logger.getLogger(ExcelPurchaseContract.class);

    private List<ExcelColumn> list;

    public ExcelColumnToPurchaseContract(List<ExcelColumn> list) {
        this.list = list;
    }

    public PurchaseContract parse(List<String> column){//获取每一列的数据
        PurchaseContract purchase = new PurchaseContract();
        for (int i=0;i<list.size();i++){
        //for(ExcelColumn excelColumn:list){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);
            //System.out.println("value的值:"+value);
            ExcelColumn excelColumn= list.get(i);
            if(excelColumn == ExcelColumn.contract_code){//合同编号
                purchase.setContractCode(value);
            }else if(excelColumn == ExcelColumn.purchase_time){//定稿时间
                purchase.setPurchaseTimeStr(value);
            }else if(excelColumn == ExcelColumn.contract_name){//合同名称
                purchase.setContractName(value);
            }else if(excelColumn == ExcelColumn.contract_amount){//合同金额
                if (value ==null || value.equals("") || value.length()<0){ value="0"; }
                purchase.setAmount(value);
            }else if(excelColumn == ExcelColumn.undertake_org){//承办单位
                purchase.setUndertakeOrg(value);
            }else if(excelColumn == ExcelColumn.undertake_dept){//承办部门
                purchase.setUndertakeDept(value);
            }else if(excelColumn == ExcelColumn.undertake_man){//承办人
                purchase.setUndertakeMan(value);
            }else if(excelColumn == ExcelColumn.supplier_name){//供应商
                purchase.setSupplierName(value);
            }else if(excelColumn == ExcelColumn.purchase_way){//采购方式
                if (value ==null || value.equals("") || value.length()<0){
                    value="0";
                }else{
                    int a = value.indexOf(";");
                    if (a !=-1){
                        value = value.split("\\;")[0];
                    }
                }
                purchase.setPurchaseWay(value);
            }else if(excelColumn == ExcelColumn.purchase_type){//采购类型
                purchase.setPurchaseType(value);
            }else if(excelColumn == ExcelColumn.company_code){//公司代码
                purchase.setCompanyCode(value);
            }else if(excelColumn == ExcelColumn.purchase_kind){//采购种类
                purchase.setPurchaseKind(value);
            }else if(excelColumn == ExcelColumn.set_service_type){//设备类型/服务
                purchase.setSetServiceType(value);
            }else if(excelColumn == ExcelColumn.set_type_lv1){//设备类型第一层
                purchase.setSetTypeLv1(value);
            }else if(excelColumn == ExcelColumn.set_type_lv2){//设备类型第二层
                purchase.setSetTypeLv2(value);
            }else if(excelColumn == ExcelColumn.sign_type){//签约类型
                purchase.setSignType(value);
            }else if(excelColumn == ExcelColumn.is_correlation_trade){//是否关联交易
                purchase.setIsCorrelationTrade(value);
            }else if(excelColumn == ExcelColumn.correlation_trade_type){//关联交易类型
                purchase.setCorrelationTradeType(value);
            }else if(excelColumn == ExcelColumn.is_itc_project){//是否itc项目
                purchase.setIsItcProject(value);
            }else if(excelColumn == ExcelColumn.is_pivotal_contract){//是否属于关键合同
                purchase.setIsPivotalContract(value);
            }else if(excelColumn == ExcelColumn.kong){//空列
                purchase.setKong(value);
            } else if(excelColumn == ExcelColumn.receipt_pay_type){//收付类型
                purchase.setReceiptPayType(value);
            }else if(excelColumn == ExcelColumn.contract_type){//合同类型
                purchase.setContractType(value);
                if(value !=null && !value.equals("")){
                    String[] bbr = value.split("[\\u4e00-\\u9fa5]");
                    String co = bbr[0];
                    String[] bbr2 = value.split("\\d+");
                    String na = bbr2[1];
                    purchase.setContractTypeCode(co);
                    purchase.setContractTypeName(na);
                }
            }else if(excelColumn == ExcelColumn.purchase_result_code){//采购结果编号
                purchase.setPurchaseResultCode(value);
            }else if(excelColumn == ExcelColumn.contract_prop){//合同属性
                purchase.setContractProp(value);
            }else if(excelColumn == ExcelColumn.archive_date){//归档日期
                purchase.setArchiveDateStr(value);
            }else if(excelColumn == ExcelColumn.contract_status){//合同状态
                purchase.setContractStatus(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return purchase;
    }
}

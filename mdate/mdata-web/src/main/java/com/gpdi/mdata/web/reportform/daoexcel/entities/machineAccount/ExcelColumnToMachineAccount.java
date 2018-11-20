package com.gpdi.mdata.web.reportform.daoexcel.entities.machineAccount;



import com.gpdi.mdata.sys.entity.report.MachineAccount;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author wangxiaogang
 * @date 2018/4/19
 * @using
 *      List<ExcelColumn> : 要读取的列索引
 *      将对应的列索引的数据保存到ExcelColumnToMachineAccount对象中
 */
public class ExcelColumnToMachineAccount {

    private static Logger logger = Logger.getLogger(ExcelMachineAccount.class);

    private List<ExcelColumn> list;

    public ExcelColumnToMachineAccount(List<ExcelColumn> list) {
        this.list = list;
    }

    public MachineAccount parse(List<String> column){//获取每一列的数据
        MachineAccount machine = new MachineAccount();
        for (int i=0;i<list.size();i++){
        //for(ExcelColumn excelColumn:list){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);
            //System.out.println("value的值:"+value);
            ExcelColumn excelColumn= list.get(i);
            if(excelColumn == ExcelColumn.contract_code){//合同编号
                machine.setContractCode(value);
            }else if(excelColumn == ExcelColumn.contract_name){//合同名称
                machine.setContractName(value);
            }else if(excelColumn == ExcelColumn.commany_name){//我方名称
                machine.setCommanyName(value);
            }else if(excelColumn == ExcelColumn.contract_type){//合同类型
                machine.setContractType(value);
            }else if(excelColumn == ExcelColumn.receipt_pay_type){//收付类型
                machine.setReceiptPayType(value);
            }else if(excelColumn == ExcelColumn.contract_amount){//合同金额(元)
                machine.setContractAmountStr(value);
            }else if(excelColumn == ExcelColumn.contract_status){//合同状态
                machine.setContractStatus(value);
            }else if(excelColumn == ExcelColumn.undertake_dept){//承办部门
                machine.setUndertakeDept(value);
            }else if(excelColumn == ExcelColumn.undertake_man){//承办人
                machine.setUndertakeMan(value);
            }else if(excelColumn == ExcelColumn.current_step){//当前办理步骤
                machine.setCurrentStep(value);
            }else if(excelColumn == ExcelColumn.sign_basis){//签约依据
                machine.setSignBasis(value);
            }else if(excelColumn == ExcelColumn.is_busi_outsourcing){//是否业务外包合同
                machine.setIsBusiOutsourcing(value);
            }else if(excelColumn == ExcelColumn.is_itc_project){//是否ICT项目
                machine.setIsItcProject(value);
            }else if(excelColumn == ExcelColumn.draft_date){//拟稿日期
                machine.setDraftDateStr(value);
            }else if(excelColumn == ExcelColumn.finalize_date){//定稿日期
                machine.setFinalizeDateStr(value);
            }else if(excelColumn == ExcelColumn.stamp_date){//盖章日期
                machine.setStampDateStr(value);
            }else if(excelColumn == ExcelColumn.sign_type){//签约类型
                machine.setSignType(value);
            }else if(excelColumn == ExcelColumn.is_correlation_contract){//是否关联合同
                machine.setIsCorrelationContract(value);
            }else if(excelColumn == ExcelColumn.correlation_contract_info){//关联合同信息
                machine.setCorrelationContractInfo(value);
            }else if(excelColumn == ExcelColumn.is_cooperate_shared){//是否合作分成
                machine.setIsCooperateShared(value);
            }else if(excelColumn == ExcelColumn.supplier_name){//对方名称
                machine.setSupplierName(value);
            }else if(excelColumn == ExcelColumn.is_correlation_trade){//是否关联交易
                machine.setIsCorrelationTrade(value);
            }else if(excelColumn == ExcelColumn.is_correlation_trade_type){//关联交易类型
                machine.setIsCorrelationTradeType(value);
            }else if(excelColumn == ExcelColumn.is_original_suppliey){//是否原合同对方
                machine.setIsOriginalSuppliey(value);
            }else if(excelColumn == ExcelColumn.suppliey_legal_rep){//对方法人代表
                machine.setSupplieyLegalRep(value);
            }else if(excelColumn == ExcelColumn.performance_begin){//履行期限起
                machine.setPerformanceBeginStr(value);
            }else if(excelColumn == ExcelColumn.performance_end){//履行期限止
                machine.setPerformanceEndStr(value);
            }else if(excelColumn == ExcelColumn.purchase_type){//采购类型
                machine.setPurchaseType(value);
            }else if(excelColumn == ExcelColumn.set_service_type){//设备/服务类型
                machine.setSetServiceType(value);
            }else if(excelColumn == ExcelColumn.purchase_way){//采购方式
                machine.setPurchaseWay(value);
            }else if(excelColumn == ExcelColumn.purchase_kind){//采购种类
                machine.setPurchaseKind(value);
            }else if(excelColumn == ExcelColumn.set_type_lv1){//设备类型第一层
                machine.setSetTypeLv1(value);
            }else if(excelColumn == ExcelColumn.set_type_lv2){//设备类型第二层
                machine.setSetTypeLv2(value);
            }else if(excelColumn == ExcelColumn.contract_prop){//合同属性
                machine.setContractProp(value);
            }else if(excelColumn == ExcelColumn.fee_prop){//费用属性
                machine.setFeeProp(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return machine;
    }
}

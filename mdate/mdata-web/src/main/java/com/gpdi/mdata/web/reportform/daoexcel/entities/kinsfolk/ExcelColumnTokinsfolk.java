package com.gpdi.mdata.web.reportform.daoexcel.entities.kinsfolk;


import com.gpdi.mdata.sys.entity.report.KinsfolkBusiness;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author wangxiaogang
 * @date 2018/4/19
 * @using
 *      List<ExcelColumn> : 要读取的列索引
 *      将对应的列索引的数据保存到ExcelPurchaseContract对象中
 */
public class ExcelColumnTokinsfolk {

    private static Logger logger = Logger.getLogger(KinsfolkBusiness.class);

    private List<ExcelColumn> list;

    public ExcelColumnTokinsfolk(List<ExcelColumn> list) {
        this.list = list;
    }

    public KinsfolkBusiness parse(List<String> column){
        KinsfolkBusiness kinsfolk = new KinsfolkBusiness();
        for (int i=0;i<list.size();i++){
            //for(ExcelColumn excelColumn:list){
            if(i >= column.size()){
                continue;
            }
            String value = column.get(i);//获取当前列的值
            ExcelColumn excelColumn= list.get(i);//获取标题名称
            if(excelColumn == ExcelColumn.enterprise_name){//企业名称
                kinsfolk.setEnterpriseName(value);
            }else if(excelColumn == ExcelColumn.enterprise_code){//企业代码
                kinsfolk.setEnterpriseCode(value);
            }else if(excelColumn == ExcelColumn.kinsfolk_name){//亲属姓名
                kinsfolk.setKinsfolkName(value);
            }else if(excelColumn == ExcelColumn.sex){//性别
                kinsfolk.setSex(value);
            }else if(excelColumn == ExcelColumn.identity_card_number){//身份证号码
                kinsfolk.setIdentityCardNumber(value);
            }else if(excelColumn == ExcelColumn.duty){//职务
                kinsfolk.setDuty(value);
            }else if(excelColumn == ExcelColumn.data_update_time){//数据更新时间
                if (value !=null && !value.equals("")) {
                    kinsfolk.setDataUpdateTime(Timestamp.valueOf(value));
                }
            }else if(excelColumn == ExcelColumn.data_update_way){//数据更新方式
                kinsfolk.setDataUpdateWay(value);
            }else{
                logger.error("未处理的列: "+excelColumn.getName());
            }
        }
        return kinsfolk;
    }
}

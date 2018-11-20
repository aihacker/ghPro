package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.UndisclosedService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.math.BigDecimal;
import java.util.*;

import static jdk.nashorn.internal.objects.NativeString.substring;

@Service
@Transactional(readOnly = false)
public class UndisclosedServiceImpl implements UndisclosedService{
    @Autowired
    GeneralDao generalDao;
    @Override
    public QueryResult queryUnOpen(QueryData queryData, QuerySettings settings) {
        Query query =new   PagedQuery<>(settings);
         String hid = queryData.getHid();
         System.out.println(hid);
         if (hid !=null && hid.equals("业务代理(含农村统包)")){
             query.select("a.contract_name,a.supplier_name,a.contract_type_code,a.contract_type_name,a.purchase_way,a.contract_amount,b.title_three,b.money,b.title_one,b.roles_number").from("t_purchase_contract as a,t_contract_type_way as b,t_file_rules as c").where("a.contract_type_code=b.codes AND a.contract_amount>=b.money  AND b.roles_number=c.rule_number AND a.purchase_time BETWEEN c.start_date AND c.end_date AND a.purchase_way not in('单一来源采购（公示）','单一来源采购（非公示）','公开招标') AND a.contract_type_code not in('A-8','A-11','B-14','B-15','C-9')");
         }else{
             query.select("a.contract_name,a.supplier_name,a.contract_type_code,a.contract_type_name,a.purchase_way,a.contract_amount,b.title_three,b.money,b.title_one,b.roles_number").from("t_purchase_contract as a,t_contract_type_way as b,t_file_rules as c").where("a.contract_type_code=b.codes AND a.contract_amount>=b.money  AND b.roles_number=c.rule_number AND a.purchase_time BETWEEN c.start_date AND c.end_date AND a.purchase_way not in('单一来源采购（公示）','单一来源采购（非公示）','公开招标') AND a.contract_type_code not in('A-8','A-11','B-14','B-15')");
         }

        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        List<PurchaseContract> temp = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            for (int b = 0; b < temp2.size();b++) {
                ((Map)temp2.get(0)).put("contract_amount",intChange2Str((BigDecimal)((Map)temp2.get(0)).get("contract_amount")));
            }
        }
        return queryResult;
    }
    private static String intChange2Str(BigDecimal number) {

        String str = "";
        if (number.compareTo(BigDecimal.ZERO)==-1||number.compareTo(BigDecimal.ZERO)==0) {
            str = "";
        } else if (number.compareTo(new BigDecimal(10000))==-1) {
            str = number + "阅读";
        } else {
            double d = number.doubleValue();
            double num = d / 10000;//1.将数字转换成以万为单位的数字

            BigDecimal b = new BigDecimal(num);
            double f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//2.转换后的数字四舍五入保留小数点后一位;
            str = f1 + "万";
        }
        return str;
    }

}

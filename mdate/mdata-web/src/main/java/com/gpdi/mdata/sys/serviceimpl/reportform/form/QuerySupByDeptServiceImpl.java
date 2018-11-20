package com.gpdi.mdata.sys.serviceimpl.reportform.form;

import com.gpdi.mdata.sys.dao.report.RepPurchaseDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.QuerySupByDeptService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.utils.PercentUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.GeneralDao;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.dao.query.Query;
import pub.functions.StrFuncs;

import java.text.SimpleDateFormat;
import java.util.List;
@Service
@Transactional(readOnly = false)  //该注解表示该类里面的所有方法或者这个方法的事务由spring处理,来保证事务的原子性
public class QuerySupByDeptServiceImpl implements QuerySupByDeptService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private RepPurchaseDao repPurchaseDao;

    @Override
    public List<PurchaseContract> getAllDept() {//获取所有经办部门
        return repPurchaseDao.getAllDept();
    }

    @Override
    public List<String> getAllPurchaseWay() {//获取所有采购方式
        return repPurchaseDao.getAllPurchaseWay();
    }

    @Override
    public List<PurchaseContract> getCountByDept(QueryData queryData) {
        String supplier = queryData.getSupperName();
        String dept = queryData.getDept();
        String sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                "FROM t_purchase_contract WHERE supplier_name='"+supplier+"' and undertake_dept='"+dept+"'";
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(PurchaseContract pur : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("supplier_name",pur.getSupplierName());
            jsonObject.put("contract_code",pur.getContractCode());
            jsonObject.put("contract_name",pur.getContractName());
            jsonObject.put("contract_type", PercentUtil.getChinese(pur.getContractType()));
            jsonObject.put("contract_amount",pur.getContractAmount());
            jsonObject.put("undertake_dept",pur.getUndertakeDept());
            jsonObject.put("purchase_way",pur.getPurchaseWay());
            jsonObject.put("purchase_time",sdf.format(pur.getPurchaseTime()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}

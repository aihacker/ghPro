package com.gpdi.mdata.sys.service.reportform.daoexcel;

import com.gpdi.mdata.sys.dao.report.*;
import com.gpdi.mdata.sys.entity.report.*;
import com.gpdi.mdata.sys.service.reportform.daoexcel.purchaseparent.QueryData;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;
import pub.web.ServletUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class PurchaseParentService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private PurchaseParentDao purchaseParentDao;

    @Autowired
    private PurchaseDao purchaseDao;
     @Autowired
    private CleanDao cleanDao;

    @Autowired
    private MachineDao machineDao;

    @Autowired
    private KinsfolkDao kinsfolkDao;

    @Autowired
    private BidCompanyInfoDao bidCompanyInfoDao;

    public QueryResult query(QueryData queryData, QuerySettings settings) {

        Query query = new PagedQuery(settings);

        StringBuilder where = new StringBuilder();

        if (StrFuncs.notEmpty(queryData.getCode())) {
            where.append(" and  code like :code");
            query.put("code", StrFuncs.anyLike(queryData.getCode()));
        }
        if (StrFuncs.notEmpty(queryData.getMd5())) {
            where.append(" and md5 like :md5");
            query.put("md5", StrFuncs.anyLike(queryData.getMd5()));
        }
        if (StrFuncs.notEmpty(queryData.getDate())) {
            where.append(" and date_format(create_date,'%Y%m') = :date");
            query.put("date", StrFuncs.anyLike(queryData.getDate()));
        }
        String select = "*,(case when state = 0 then '导入中' " +
                "   when state = 1 then '导入成功,正在处理'" +
                "   when state = 2 then '导入失败'" +
                "   when state = 3 then '处理成功'" +
                "   else '未知' end) stateString ";
        query.select(select)
                .from("t_purchase_parent ")
                .where(where.toString())
                .orderBy("id desc");

        generalDao.execute(query);
        return query.getResult();
    }

    public PurchaseParent get(Integer id) {
        return purchaseParentDao.get(id);
    }

    public PurchaseParent getByCode(String code) {
        String sql = "select * from t_purchase_parent where code = ?";//t_purchase_parent:导入表
        return generalDao.queryValue(PurchaseParent.class, sql, code);
    }

    /**
     * 创建导入excel的记录
     * t_purchase_parent(导入表)
     * */
    @Transactional
    public PurchaseParent create(MultipartFile file, Integer type) {
        HttpSession session = ServletUtils.getSession();
        String userName = String.valueOf(session.getAttribute("userName"));
        String fileName = file.getOriginalFilename();//获取文件名称
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(file.getInputStream());//根据数据生成唯一标识
        } catch (IOException e) {
            e.printStackTrace();
        }
        PurchaseParent orderParent = new PurchaseParent();
        orderParent.setMd5(md5);//设置唯一标识
        orderParent.setCode(String.valueOf(System.currentTimeMillis()));//生成批次号
        orderParent.setCreateDate(new Date());//导入时间
        orderParent.setFile_name(fileName);//设置文件名
        orderParent.setUser(userName);//设置导入的用户
        orderParent.setType(type);//文件类型:,0:采购合同报表;1.合同台账报表;2:领导亲属经商表
        orderParent.setState(PurchaseParent.STATE_READY);//导入状态:导入处理中
        purchaseParentDao.save(orderParent);
       /* if(orderParent.getId() != null){
            purchaseOrderParentDao.updateCodeById(orderParent.getId(),
            String.format("AO-%010d", orderParent.getId()));
            return orderParent;
        }*/
        return orderParent;
        //return null;
    }


    public PurchaseParent getByMd5(String Md5) {
        return purchaseParentDao.getByMd5(Md5);
    }

    /**
     * 保存采购合同台账表数据，关联导入日志记录
     * t_purchase_contract(导入数据表)
     * */
    @Transactional
    public boolean saveOrderHandle(List<PurchaseContract> list, Integer parentId) {
        PurchaseParent parent = purchaseParentDao.get(parentId);
        if (parent == null || !parent.getState().equals(PurchaseParent.STATE_READY)) {
            return false;
        }
        String sql = "SELECT DISTINCT contract_code FROM t_purchase_contract";
        List<String> codes = generalDao.queryList(String.class,sql);

        for (PurchaseContract handle : list) {
            String co = handle.getContractCode();
            if(codes !=null && codes.contains(co)){
                continue;
            }
            handle.setPId(parentId);
            purchaseDao.save(handle);
        }
        return true;
    }
/**
 * 保存及时清洁数据
 */

@Transactional
public boolean saveCleanlneventory(List<CleanInventory> list) {

    for (CleanInventory handle : list) {
        cleanDao.save(handle);
    }
    return true;
}

    /**
     * 保存合同台账表数据，关联导入日志记录
     * t_purchase_contract(导入数据表)
     * */
    @Transactional
    public boolean saveMachineHandle(List<MachineAccount> list, Integer parentId) {
        PurchaseParent parent = purchaseParentDao.get(parentId);
        if (parent == null || !parent.getState().equals(PurchaseParent.STATE_READY)) {
            return false;
        }
        for (MachineAccount handle : list) {
            handle.setParentId(parentId);
            machineDao.save(handle);
        }
        return true;
    }


    /**
     * @param list
     * @param parentId
     * 保存领导亲属数据，关联导入日志记录
     * t_kinsfolk_business(导入数据表)
     */
    @Transactional//事务管理对于企业应用来说是至关重要的，即使出现异常情况，它也可以保证数据的一致性
    public boolean saveKinsfolkHandle(List<KinsfolkBusiness> list, Integer parentId) {
        PurchaseParent parent = purchaseParentDao.get(parentId);
        if (parent == null || !parent.getState().equals(PurchaseParent.STATE_READY)) {
            return false;
        }
        for (KinsfolkBusiness kinsfolk : list) {
            kinsfolk.setPId(parentId);
            kinsfolkDao.save(kinsfolk);
        }
        return true;
    }

    /**
     * @param list
     * @param parentId
     * 保存公开采购投标公司信息，关联导入日志记录
     * t_public_company_infoes(导入数据表)
     */
    @Transactional//事务管理对于企业应用来说是至关重要的，即使出现异常情况，它也可以保证数据的一致性
    public boolean saveBidCompanyInfoHandle(List<BidCompanyInfo> list, Integer parentId) {
        PurchaseParent parent = purchaseParentDao.get(parentId);
        if (parent == null || !parent.getState().equals(PurchaseParent.STATE_READY)) {
            return false;
        }
        for (BidCompanyInfo bidInfo : list) {
            bidInfo.setParentId(parentId);
            bidCompanyInfoDao.save(bidInfo);
        }
        return true;
    }



    @Transactional
    public int save(PurchaseParent orderParent) {
        purchaseParentDao.save(orderParent);
        return orderParent.getId();
    }


}

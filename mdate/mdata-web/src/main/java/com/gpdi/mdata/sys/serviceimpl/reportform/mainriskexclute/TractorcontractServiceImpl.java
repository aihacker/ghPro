package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.entity.report.LeadDepartment;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.TractorcontractService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.*;

/**
 * @description:拖拉机合同
 * @author: WangXiaoGang
 * @data: Created in 2018/10/17 11:15
 * @modifier:
 */
@Service
@Transactional(readOnly = true)
public class TractorcontractServiceImpl implements TractorcontractService {
    @Autowired
    GeneralDao generalDao;

    @Override
    public  List<LeadDepartment>  queryleadDepartment() {

        //1.查询出每个领导所任职的部门
        String sql1 = "SELECT x.leader_name, x.work_department, x.start_time, x.end_time,GROUP_CONCAT(x.supplier_name) allSuplier, GROUP_CONCAT(x.sup) allSuplierNum\n" +
                " FROM\n" +
                "(SELECT a.leader_name,a.work_department,min(a.start_time) start_time, max(a.end_time) end_time,b.supplier_name,COUNT(b.supplier_name) as coun,\n" +
                "                       CONCAT(b.supplier_name,'-',COUNT(b.supplier_name)) as sup\n" +
                "                  FROM t_purchase_contract b, t_leader_work_dept a,t_candidater_option c\n" +
                "                 WHERE c.approver = a.leader_name  AND b.purchase_result_code = c.canditater_result_code AND  b.purchase_time BETWEEN a.start_time AND a.end_time \n" +
                "                 GROUP BY a.leader_name,a.work_department, b.supplier_name\n" +
                "                 ORDER BY a.leader_name,coun DESC) x\n" +
                "group by x.leader_name, x.work_department, x.start_time, x.end_time";
        //================1.获取所有的领导及部门和签订的供应商数据
        List<LeadDepartment> leadDepartmentList = generalDao.queryList(LeadDepartment.class, sql1);
        List<LeadDepartment> list = new ArrayList<>();
        if (leadDepartmentList != null && leadDepartmentList.size() > 0) {
            //============2.取出所有领导名称==========
            List<String> allLead = new ArrayList<>();
            for (int a = 0; a < leadDepartmentList.size(); a++) {
                allLead.add(leadDepartmentList.get(a).getLeaderName());
            }

            if (allLead != null && allLead.size() > 0) {
                //===========2.1对领导名称进行去重 通过HashSet踢除重复元素
                HashSet h = new HashSet(allLead);
                allLead.clear();
                allLead.addAll(h);
            }
            if (allLead != null && allLead.size() > 0) {
                for (String leander : allLead) {
                    List<String> supplierByLead = new ArrayList<>();//根据领导取出所有的供应商
                    List<String> aLike = new ArrayList<>();//获取相同的供应商
                    for (int b = 0; b < leadDepartmentList.size(); b++) {
                        LeadDepartment neLead = leadDepartmentList.get(b);
                        //====================3.根据领导人名称取出该领导下所有的部门下的所有供应商集合======
                        if (leander.equals(neLead.getLeaderName())) {
                            String allSuppler = neLead.getAllSuplier();//取出当前部门下的所有供应商
                            supplierByLead.add(allSuppler);
                        }
                    }
                    //==========3.1根据领导对取出所有的供应商去重,找出各部门中相同的供应商===========
                    if (supplierByLead != null && supplierByLead.size() > 0) {
                        for (int c = 0; c < supplierByLead.size(); c++) {
                            String sup1 = supplierByLead.get(c);
                            String[] supli1 = sup1.split(",");
                            List<String> _supli1 = Arrays.asList(supli1);
                            for (int d = c + 1; d < supplierByLead.size(); d++) {

                                String sup2 = supplierByLead.get(d);
                                String[] supli2 = sup2.split(",");
                                //将数组转换为list
                                List<String> _supli2 = Arrays.asList(supli2);
                                //创建集合,求交集
                                Collection realA = new ArrayList<String>(_supli1);
                                Collection realB = new ArrayList<String>(_supli2);
                                //求2个集合的交集,即找出2个部门中的相同供应商
                                realA.retainAll(realB);

                                if (realA != null && realA.size() > 0) {
                                    for (Iterator ite = realA.iterator(); ite.hasNext(); ) {
                                        aLike.add(String.valueOf(ite.next()));
                                    }
                                }
                            }


                        }
                    }
                    //=========4.根据每个领导下的相同供应商去找出签订数量
                    if (aLike != null && aLike.size() > 0) {
                        HashSet hash = new HashSet(aLike);
                        aLike.clear();
                        aLike.addAll(hash);
                        for (int e = 0; e < leadDepartmentList.size(); e++) {
                            if (leander.equals(leadDepartmentList.get(e).getLeaderName())) {
                                LeadDepartment leadList = leadDepartmentList.get(e);
                                String allSupplerNum = leadList.getAllSuplierNum();//取出当前部门下的所有供应商+数量
                                String[] spNum = allSupplerNum.split(",");
                                List<String> newSupplerNum = new ArrayList<>();
                                List<String> news = new ArrayList<>();
                                for (int k=0;k<aLike.size();k++){
                                    news.add("");
                                }
                                for (int f = 0; f < spNum.length; f++) {
                                    String strNum = spNum[f];
                                    String[] snum = strNum.split("-");
                                    String str = snum[0];
                                    if (aLike.contains(str)) {
                                        for (int h=0;h<aLike.size();h++){
                                            if (aLike.get(h).equals(str)){
                                                StringBuilder sb = new StringBuilder();
                                                sb.append(str);
                                                sb.append("(");
                                                sb.append(snum[1]);
                                                sb.append(")");
                                                news.set(h,sb.toString());
                                                break;
                                            }
                                        }
                                        newSupplerNum = news;
                                    }
                                }
                              //  leadList.setAllSuplierNum(newSupplerNum.toString());
                                leadList.setNewSupplerNum(newSupplerNum);
                                list.add(leadList);

                            }
                        }
                    }



                }

            }
        }
        return list;

    }

    /**
     * 查询合同明细
     * @param queryData
     * @param settings
     * @return
     */
    @Override
    public QueryResult queryDetail(QueryData queryData, PageSettings settings) {
       String name = queryData.getName();//获取领导名称
       String dept = queryData.getDept();//获取领导部门
       String supperName = queryData.getSupperName();//获取供应商名称
        if (name !=null && dept !=null && supperName !=null){
            supperName = supperName.substring(0,supperName.indexOf("("));
            Query query = new PagedQuery(settings);
            query.select("a.supplier_name,a.contract_code,a.contract_name,a.contract_type_name,a.contract_amount,a.undertake_dept,a.purchase_way,a.purchase_time")
                    .from("t_purchase_contract a,t_candidater_option b")
                    .where("b.approver ='"+name+"' and a.undertake_dept='"+dept+"' and a.supplier_name='"+supperName+"' and a.purchase_result_code = b.canditater_result_code");
            generalDao.execute(query);
            QueryResult queryResult = query.getResult();
            return queryResult;
        }

        return null;
    }


}

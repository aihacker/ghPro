package com.gpdi.mdata.sys.serviceimpl.reportform.info;

import com.gpdi.mdata.sys.dao.report.ContractTypeWayDao;
import com.gpdi.mdata.sys.dao.report.FileRulersDao;
import com.gpdi.mdata.sys.dao.report.TenderingrulesDao;
import com.gpdi.mdata.sys.entity.report.ContractTypeWay;
import com.gpdi.mdata.sys.entity.report.FileRulers;
import com.gpdi.mdata.sys.entity.report.Legal;
import com.gpdi.mdata.sys.entity.report.TenderingrulesWay;
import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.ListQuery;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/4 21:23
 * @modifier:
 */
@Service
@Transactional(readOnly = true) //@Transactional:事务管理控制,readOnly=false表明所注解的方法或类是增加,删除,修改数据
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private FileRulersDao fileRulersDao;

    @Autowired
    private TenderingrulesDao tenderingrulesDao;

    @Autowired
    private ContractTypeWayDao typeWayDao;

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query =new PagedQuery<>(settings);
       // query.select("a.project_name,a.project_code,a.purchase_way,b.tendering_company,b.project_leader,b.credit_code,b.opening_time,b.bid_price,b.is_it_eligible,b.not_reason,a.list_of_jedes").from("t_public_company_info a,t_public_company_info_rel b").where("a.project_code =b.project_code");
        String sql = "{call purcahse()}";
        generalDao.execute(sql);
        //query.select("a.project_name,a.project_code,a.purchase_way,a.opening_time,b.tendering_company,b.credit_code,b.is_it_eligible,b.not_reason,g.legal_representative,g.shareholder_one,g.shareholder_two,g.shareholder_three").from("t_public_company_info as a,t_public_company_info_rel as b,t_supplier_info_online as g").where("a.project_code = b.project_code and b.tendering_company = g.company_name").orderBy("a.project_name desc");
        query.select("a.project_name,a.project_code,a.purchase_way,a.opening_time,b.tendering_company,b.credit_code,b.is_it_eligible,b.not_reason,g.legal_representative,g.shareholder_one,g.shareholder_two,g.senior_admin_one,senior_admin_two ,c.s AS relName").from("t_public_company_info as a,t_public_company_info_rel as b,t_supplier_info_online as g,( select project_code, s, count(s) as total from temp1 group by project_code, s having count(s)>1 ) c ").where("a.project_code = b.project_code and b.tendering_company = g.company_name AND a.project_code=c.project_code").orderBy("a.project_name desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public QueryResult queryrulers(QueryData queryData, QuerySettings settings) {
        Query query =new PagedQuery<>(settings);
        query.select("*").from("t_file_rules");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();

        return queryResult;

    }

    @Override
    public QueryResult querylegal(YtcardData ytcardData, QuerySettings settings) {
        Query query =new PagedQuery<>(settings);
        String company = ytcardData.getName();
        String where ="";
        if (StrFuncs.notEmpty(company)){
            where += "and company_name='"+company+"'";
            query.put("company",company);
        }
        query.select("*").from("t_supplier_info_online")
        .where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
       /* List<String> op = queryResult.getRows();
        if(op ==null || op.size()== 0){
            String sqlupdate = "INSERT INTO t_supplier_t1 (company_name) VALUES ('"+company+"')";
            generalDao.execute(sqlupdate);
            System.out.println("用机器人更新公司");
        }*/

        return queryResult;
    }
//=====================根据规则编号查询采购方式适用列表===================
    @Override
    public QueryResult queryTenderWay(YtcardData ytcardData, QuerySettings settings) {
       Query query = new ListQuery();
        String code = ytcardData.getCode();
        if (code ==null || code ==""){
            code = "1000";
        }
        query.select("*").from("t_file_rules_rel").where("parent='"+code+"'");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    //=====================根据规则编号查询查询法定必须招标的规定金额===================
    @Override
    public List<Legal> queryTenderlegal(YtcardData ytcardData) {
        String code = ytcardData.getCode();
        if (code ==null || code ==""){
            code = "1000";
        }
        String sql = "SELECT title_three,money FROM t_contract_type_way WHERE title_one='工程建设项目' AND roles_number=? GROUP BY title_three";
        List<Legal> list = generalDao.queryList(Legal.class,sql,code);
        return list;

    }



    //=====================根据规则编号查询招标规则说明文档==============
    @Override
    public List<FileRulers> queryFileRules(YtcardData ytcardData) {
        String code = ytcardData.getCode();
        if (code ==null || code ==""){
            code = "1000";
        }
        String sql = "SELECT * FROM t_file_rules WHERE rule_number =?";
        List<FileRulers> fileRuler = generalDao.queryList(FileRulers.class,sql,code);
        return fileRuler;
    }
    //=============查询最大的规则编码==============
    @Override
    public String queryRulesMaxId() {
        String sql = "SELECT MAX(rule_number) FROM t_file_rules";
        String numbers = generalDao.queryValue(String.class,sql);
        return numbers;
    }
    //===========保存招标规则文件======================
    @Override
    @Transactional
    public boolean saveFileRulers(FileRulers fileRulers,Map map,List<TenderingrulesWay> listWay,Integer number) {
        //=============1.保存规则文件==========
        if(fileRulers !=null){
            String basisName = fileRulers.getFileBasisName();
            String startDate = fileRulers.getStartDate();
            String fileNumber = fileRulers.getFileNumber();
            String fileAbolishName = fileRulers.getFileAbolishName();
            String fileAbolishNumber = fileRulers.getFileAbolishNumber();
            //更新之前的废止文件数据
            String sql0 ="UPDATE t_file_rules SET end_date ='"+startDate+"' WHERE 1 ORDER BY id DESC LIMIT 1";
            generalDao.execute(sql0);
            //插入新数据
            fileRulersDao.save(fileRulers);
        }
        //=============2.保存采购方式==========
        if (listWay !=null && listWay.size()>0){
            for (int i=0;i<listWay.size();i++){
                tenderingrulesDao.save(listWay.get(i));
            }
        }
        //============3.保存工程建设法定中的招标规定金额=================
        if (map !=null && map.size()>0){
            Map<String,Integer> mapRel = map;
            Integer projectMoney = mapRel.get("projectMoney");//工程施工类金额
            Integer designMoney =mapRel.get("designMoney");//工程设计监理类金额
            Integer purchaseMoney = mapRel.get("purchaseMoney");//物资采购类金额
            Integer nopjOneMoney = mapRel.get("nopjOneMoney");//非工程货物中必须招标的金额
            Integer integerMoney = mapRel.get("integerMoney");//综合服务中必须招标的金额

            //===============4.1 保存综合服务中的规定招标金额====================
            String sql1 = "SELECT DISTINCT codes,type_name,title_one,title_two,title_three,money,roles_number FROM t_contract_type_way WHERE title_three = '综合服务' AND roles_number =1000";
            List<ContractTypeWay> typeWays = generalDao.queryList(ContractTypeWay.class,sql1);
            if(typeWays !=null && !typeWays.equals("")){
                for (int b=0;b<typeWays.size();b++){
                    ContractTypeWay tpway = typeWays.get(b);
                    tpway.setMoney(integerMoney);
                    tpway.setRolesNumber(number);
                    typeWayDao.save(tpway);
                }
            }
            //===============4.2 保存非工程货物中的规定招标金额====================
            String sql2 = "SELECT DISTINCT codes,type_name,title_one,title_two,title_three,money,roles_number FROM t_contract_type_way WHERE title_three = '非工程货物' AND roles_number =1000";
            List<ContractTypeWay> typeWays2 = generalDao.queryList(ContractTypeWay.class,sql2);
            if(typeWays2 !=null && !typeWays2.equals("")){
                for (int c=0;c<typeWays2.size();c++){
                    ContractTypeWay tpway2 = typeWays2.get(c);
                    tpway2.setMoney(nopjOneMoney);
                    tpway2.setRolesNumber(number);
                    typeWayDao.save(tpway2);
                }
            }

            //===============4.3 保存工程货物中物资采购类类的规定招标金额====================
            String sql3 = "SELECT DISTINCT codes,type_name,title_one,title_two,title_three,money,roles_number FROM t_contract_type_way WHERE title_three = '物资采购' AND roles_number =1000";
            List<ContractTypeWay> typeWays3 = generalDao.queryList(ContractTypeWay.class,sql3);
            if(typeWays3 !=null && !typeWays3.equals("")){
                for (int d=0;d<typeWays3.size();d++){
                    ContractTypeWay tpway3 = typeWays3.get(d);
                    tpway3.setMoney(purchaseMoney);
                    tpway3.setRolesNumber(number);
                    typeWayDao.save(tpway3);
                }
            }
            //===============4.4 保存工程设计监理类类的规定招标金额====================
            String sql4 = "SELECT DISTINCT codes,type_name,title_one,title_two,title_three,money,roles_number FROM t_contract_type_way WHERE title_three = '工程设计/监理' AND roles_number =1000";
            List<ContractTypeWay> typeWays4 = generalDao.queryList(ContractTypeWay.class,sql4);
            if(typeWays4 !=null && !typeWays4.equals("")){
                for (int d=0;d<typeWays4.size();d++){
                    ContractTypeWay tpway4 = typeWays4.get(d);
                    tpway4.setMoney(designMoney);
                    tpway4.setRolesNumber(number);
                    typeWayDao.save(tpway4);
                }
            }

            //===============4.5 保存工程设计监理类类的规定招标金额====================
            String sql5 = "SELECT DISTINCT codes,type_name,title_one,title_two,title_three,money,roles_number FROM t_contract_type_way WHERE title_three = '工程施工(系统集成)' AND roles_number =1000";
            List<ContractTypeWay> typeWays5 = generalDao.queryList(ContractTypeWay.class,sql5);
            if(typeWays5 !=null && !typeWays5.equals("")){
                for (int e=0;e<typeWays5.size();e++){
                    ContractTypeWay tpway5 = typeWays5.get(e);
                    tpway5.setMoney(projectMoney);
                    tpway5.setRolesNumber(number);
                    typeWayDao.save(tpway5);
                }
            }


        }




        return true;
    }


}

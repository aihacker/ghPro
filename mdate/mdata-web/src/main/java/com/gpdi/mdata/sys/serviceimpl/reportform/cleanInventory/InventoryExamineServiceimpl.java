package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;

import com.gpdi.mdata.sys.dao.report.CleanRelDao;
import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.InventoryExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.reportform.thread.CleanOutThread;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.GeneralDao2;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @description:即时清结数据查看
 * @author: WangXiaoGang
 * @data: Created in 2018/8/10 11:19
 * @modifier:
 */
@Service
@Transactional(readOnly = false)
public class InventoryExamineServiceimpl implements InventoryExamineService {
    protected static final Logger log = Logger.getLogger(InventoryExamineServiceimpl.class);

    @Autowired
    private GeneralDao generalDao;
    @Autowired
    private GeneralDao2 generalDao2;

    @Autowired
    CleanRelDao cleanRelDao;

    @Override
    public QueryResult queryInventory(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);//创建分页对象
        query.select("*").from("t_clean_list_immediately");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public void save(String startTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curreTime = df.format((System.currentTimeMillis()));
        System.out.println("系统当前时间:"+curreTime);
        String sql = "insert into i_jsqj_excel (start_time,end_time,branch,account,date,service_tem) values ('"+startTime+"','"+endTime+"','纪检','jijian1','"+curreTime+"','即时清结excel导出')";
        System.out.println(generalDao2+"有取到吗");
        int ss = generalDao2.execute(sql);//插入成功,返回1
        System.out.println("返回的数据是什么:"+ss);
    }



    /*@Override
    public List<CleanInventoryRel> cleanAll() {
        String sql = "SELECT\n" +
                "\ttitle,\n" +
                "\tflow_path_number,\n" +
                "\tsupplier_code,\n" +
                "\tsupplier_name,\n" +
                "\tmaterial_code,\n" +
                "\tmaterial_name,\n" +
                "\tdraft_department,\n" +
                "\tpurchase_amount,\n" +
                "\tdraft_time\n" +
                "FROM\n" +
                "\tt_clean_list_immediately\n" +
                "GROUP BY\n" +
                "\tflow_path_number";
        List<CleanInventoryRel> list = generalDao.queryList(CleanInventoryRel.class, sql);
        return list;
    }*/

    @Override
    public boolean saveClean(List<CleanInventory> list) {
        List<CleanInventoryRel> listRe = new LinkedList<>();
            if (list !=null && list.size()>0) {
                for (int a=0,b=1;b<list.size()&& a<=b;a++,b++){

                        CleanInventory clean1 = list.get(a);
                        CleanInventory clean2 = list.get(b);

                            if (!clean1.getFlowPathNumber().equals(clean2.getFlowPathNumber())){
                                CleanInventoryRel rel = new CleanInventoryRel();
                                rel.setContractName(clean1.getTitle());
                                rel.setContractCode(clean1.getFlowPathNumber());
                                rel.setSupplierCode(clean1.getSupplierCode());
                                rel.setSupplierName(clean1.getSupplierName());
                                rel.setMaterialCode(clean1.getMaterialCode());
                                rel.setMaterialName(clean1.getMaterialName());
                                rel.setUndertakeMan(clean1.getDraftPeople());
                                rel.setUndertakeDept(clean1.getDraftDepartment());
                                rel.setContractAmount(clean1.getPurchaseAmount());
                                rel.setPurchaseTime(clean1.getDraftTime());
                                String cleanCode = clean1.getFlowPathNumber();//即时清结编号
                                String ngDateTime = clean1.getDraftTime();//拟稿时间
                                listRe.add(rel);
                                cleanRelDao.save(rel);
                                String sqlq = "SELECT contract_code FROM t_clean_candidater WHERE contract_code= '"+cleanCode+"'";
                                List<String> re = generalDao.queryList(String.class,sqlq);
                                if (re ==null || re.size()<1){
                                    insertData(cleanCode,ngDateTime);//插入机器人中间表
                                }

                    }
                }
                //========================启动外网机器人取数============
                CleanOutThread out = new CleanOutThread(listRe);
                out.start();

            }else{
                log.error("未获取到即时清结的数据");
                return false;
            }
             return true;
    }

    //========================将流程编号和时间插入机器人中间表触发机器人取数============
    public boolean insertData(String cleanCode, String ngDateTime) {
        System.out.println("即时清结编号:"+cleanCode+"和时间:"+ngDateTime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curreTime = df.format((System.currentTimeMillis()));
        System.out.println("系统当前时间:"+curreTime);
        String sql = "insert into i_jsqj_one (jsqj_num,dtime,branch,account,date,service_tem) values ('"+cleanCode+"','"+ngDateTime+"','纪检','jijian1','"+curreTime+"','单条即时清结')";
        int res = 0;//插入成功,返回1
        try {
            System.out.println("generalDao2是多少:"+generalDao2);
            res = generalDao2.execute(sql);
            if (res==1){
                log.info("插入即时清结模板二数据成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入即时清结模板二数据失败");
        }
        return false;
    }



}

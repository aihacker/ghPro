package wxgh.sys.service.weixin.four.excel;


import com.libs.common.type.TypeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.app.sys.api.FileApi;
import wxgh.data.four.excel.FourDetailExcelData;
import wxgh.data.four.excel.FourProjectContentData;
import wxgh.data.four.excel.FourProjectExcelData;
import wxgh.data.four.excel.InsertProject;
import wxgh.param.four.excel.FourExcelParam;
import wxgh.sys.service.weixin.pub.SysFileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-04  17:06
 * --------------------------------------------------------- *
 */
@Service
@Transactional(readOnly = true)
public class FourExcelService {

    private static final Logger log = Logger.getLogger(FourExcelService.class);

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private SysFileService sysFileService;

    public static Map<String, Integer> deptMap = new HashMap<>();
    public static Map<String, Integer> marketMap = new HashMap<>();
    public static Map<String, Integer> projectMap = new HashMap<>();

    // 导入
    @Transactional
    public void importDetails(List<FourDetailExcelData> list) {
        for (FourDetailExcelData fourDetail : list) {
            Integer pfId = insertProject(fourDetail.getProjectName());
            Integer fpcId = insertProjectContent(pfId, fourDetail.getProjectContent());
            fourDetail.setFpcId(fpcId);
            fourDetail.setFpId(pfId);
            fourDetail.setStatus(1);
            fourDetail.setIsExport(1);
            System.out.println("deptId:" + fourDetail.getDeptId());
        }

        SQL sql = new SQL.SqlBuilder()
                .field("fp_id, fpc_id, brand, model_name, numb, remark, dept_id, buy_time, condit, cond_str")
                .field("unit, imgs, plan_update, useful_life, repair_count, price_source, price, status, mid, is_export")
                .value(":fpId, :fpcId, :brand, :modelName, :numb, :remark, :deptId, :buyTime, :condit, :condStr")
                .value(":unit, :images, :planUpdate, :usefulLife, :repairCount, :priceSource, :price, :status, :mid, :isExport")
                .insert("four_details")
                .build();
        pubDao.executeBatch(sql.sql(), list);
    }
    // end

    // 导出
    public List<FourDetailExcelData> getList(FourExcelParam param) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_four_details fd")
                .field("d.name as companyName")
                .field("m.name as deptName, p.name as projectName, pc.name as projectContent")
                .field("fd.brand, fd.model_name as modelName, fd.numb, fd.remark, fd.buy_time as buyTime")
                .field("fd.condit, fd.cond_str as condStr, fd.unit, fd.useful_life as usefulLife")
                .field("fd.price_source as priceSource, fd.price, fd.plan_update as planUpdate, fd.machine_location")
                .field("(select count(*) from t_four_repair where four_id = fd.id) as repairCount")
                .join("t_dept d", "fd.dept_id = d.deptid")
                .join("t_four_project p", "p.id = fd.fp_id")
                .join("t_four_project_content pc", "pc.id = fd.fpc_id")
                .join("t_marketing m", "m.id = fd.mid")
                .sys_file("fd.img_ids")
                .order("fd.id");

        if (param.getUserid() != null)
            sql.where("fd.user_id = :userid");
        if (param.getDeptid() != null)
            sql.where("fd.dept_id = :deptid");
        if (param.getFpcId() != null)
            sql.where("fd.fpc_id = :fpcId");
        if (param.getFpId() != null)
            sql.where("fd.fp_id = :fpId");
        if (param.getMid() != null)
            sql.where("fd.mid = :mid");
        if (param.getBuyTime() != null)
            sql.where("DATE_FORMAT(fd.buy_time, '%Y') = " + pub.utils.DateUtils.formatDate(param.getBuyTime(), "yyyy"));
        if (param.getPlanUpdate() != null)
            sql.where("DATE_FORMAT(fd.plan_update, '%Y') = " + pub.utils.DateUtils.formatDate(param.getPlanUpdate(), "yyyy"));

        return pubDao.queryList(sql.select().build().sql(), param, FourDetailExcelData.class);
    }
    // end

    /**
     * 获取分公司deptid
     *
     * @param name
     * @return
     */
    public Integer getCompany(String name) {
        SQL sql = new SQL.SqlBuilder()
                .table("dept d")
                .field("d.deptid")
                .join("dept dp", "dp.id = d.parentid")
                .where("d.name = ?")
                .where("dp.deptid = 1")
                .limit("1")
                .select()
                .build();
        Integer deptid = pubDao.query(Integer.class, sql.sql(), name);
        if (deptid == null)
            return 0;
        return deptid;
    }


    /**
     * 分公司 /  营销中心
     *
     * @param mName
     * @param dName
     * @return
     */
    public Integer getMarket(String mName, String dName) {
        String key = mName + "-" + dName;
        if (marketMap.containsKey(key))
            if (marketMap.get(key) != null)
                return marketMap.get(key);
        SQL sql = new SQL.SqlBuilder()
                .table("dept d")
                .field("m.id")
                .join("marketing m", "m.deptid = d.deptid")
                .where("m.name = ?")
                .where("d.name = ?")
                .limit("1")
                .select()
                .build();
        Integer mid = pubDao.query(Integer.class, sql.sql(), mName, dName);
        marketMap.put(key, mid);
        return mid;
    }

    /**
     * 分公司 / 项目部
     *
     * @param name
     * @param parentName
     * @return
     */
    public Integer getDept(String name, String parentName) {
        String key = parentName + "-" + name;
        if (deptMap.containsKey(key))
            if (deptMap.get(key) != null)
                return deptMap.get(key);

        SQL sql = new SQL.SqlBuilder()
                .table("dept d")
                .join("dept dp", "d.parentid = dp.id")
                .field("d.id")
                .where("d.name = ? and dp.name = ?")
                .limit("1")
                .select()
                .build();
        Integer dept = null;
        dept = pubDao.query(Integer.class, sql.sql(), name, parentName);
        // 二级
        if (dept == null) {
            SQL sql2 = new SQL.SqlBuilder()
                    .table("dept d")
                    .field("d.id")
                    .where("d.name = ? and parentid = 130")
                    .limit("1")
                    .select()
                    .build();
            dept = pubDao.query(Integer.class, sql2.sql(), name);
        }
        if (dept == null) {
            // 部门名称不存在
            System.out.println("d:" + name);
            System.out.println("dp:" + parentName);
            return 0;
        }
        deptMap.put(key, dept);
        return dept;
    }

    @Transactional
    public Integer insertProject(String name) {
        // 判断是否存在
        if (projectMap.containsKey(name))
            if (projectMap.get(name) != null)
                return projectMap.get(name);

        SQL sql = new SQL.SqlBuilder()
                .table("four_project")
                .field("id")
                .where("name = ? ")
                .limit("1")
                .select()
                .build();
        Integer id = pubDao.query(Integer.class, sql.sql(), name);
        if (id != null) {
            projectMap.put(name, id);
            return id;
        }

        InsertProject insertProject = new InsertProject();
        insertProject.setName(name);
        SQL sqlInsert = new SQL.SqlBuilder()
                .field("name")
                .value(":name")
                .insert("four_project")
                .build();
        Integer insertId = pubDao.insertAndGetKey(sqlInsert.sql(), insertProject);
        if (insertId > 0)
            projectMap.put(name, insertId);
        return insertId;
    }

    @Transactional
    public Integer insertProjectContent(Integer fpId, String name) {
        FourProjectContentData data = new FourProjectContentData();
        data.setPfId(fpId);
        data.setName(name);

        String select = "select id from t_four_project_content where fp_id = ? and name = ? limit 1";
        Integer isNull = pubDao.query(Integer.class, select, fpId, name);
        if (isNull != null)
            return isNull;

        SQL sql = new SQL.SqlBuilder()
                .field("fp_id, name")
                .value(":pfId, :name")
                .insert("four_project_content")
                .build();
        Integer id = pubDao.insertAndGetKey(sql.sql(), data);
        return id;
    }

    @Transactional
    public void insertAllDetails(FourProjectExcelData fourProject) {
        Integer pfId = insertProject(fourProject.getProjectName());
        List<String> list = new ArrayList<>();
        List<String> images = TypeUtils.strToList(fourProject.getImage());

        if (list.size() > 0)
            fourProject.setImage(TypeUtils.listToStr(list));
        fourProject.setFpId(pfId);
        SQL sql = new SQL.SqlBuilder()
                .field("fp_id, picture, contain, scale, introduction, remark")
                .value(":fpId, :image, :numb, :scale, :introduction, :remark")
                .insert("four_all_details")
                .build();
        pubDao.insertAndGetKey(sql.sql(), fourProject);
    }

    @Transactional
    public void insertFourDetail(FourDetailExcelData fourDetail) {
        Integer deptId = getDept(fourDetail.getDeptName(), fourDetail.getParentDeptName());
        Integer pfId = insertProject(fourDetail.getProjectName());
        Integer fpcId = insertProjectContent(pfId, fourDetail.getProjectContent());
        fourDetail.setFpcId(fpcId);
        fourDetail.setFpId(pfId);
        fourDetail.setDeptId(deptId);
        fourDetail.setStatus(1);

        List<String> list = new ArrayList<>();
        List<String> images = TypeUtils.strToList(fourDetail.getImages());


        fourDetail.setImages(TypeUtils.listToStr(list));

        SQL sql = new SQL.SqlBuilder()
                .field("fp_id, fpc_id, brand, model_name, numb, remark, dept_id, buy_time, condit, cond_str")
                .field("unit, imgs, plan_update, useful_life, repair_count, price_source, price, status")
                .value(":fpId, :fpcId, :brand, :modelName, :numb, :remark, :deptId, :buyTime, :condit, :condStr")
                .value(":unit, :images, :planUpdate, :usefulLife, :repairCount, :priceSource, :price, :status")
                .insert("four_details")
                .build();
        pubDao.insertAndGetKey(sql.sql(), fourDetail);
    }

    public List<String> listCompanys() {
        String sql = "select name from t_dept where parentid=?";
        return pubDao.queryList(String.class, sql, 1);
    }

    public List<String> getMarketings() {
        String sql = "select name from t_marketing";
        return pubDao.queryList(String.class, sql);
    }

    public List<String> getProjects() {
        String sql = "select name from t_four_project";
        return pubDao.queryList(String.class, sql);
    }
}

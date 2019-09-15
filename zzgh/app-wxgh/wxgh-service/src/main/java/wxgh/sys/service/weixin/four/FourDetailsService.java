package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.utils.StrUtils;
import wxgh.data.four.*;
import wxgh.entity.four.FourDetails;
import wxgh.entity.four.FourProject;
import wxgh.entity.four.FourProjectContent;
import wxgh.param.four.FourDetailsParam;
import wxgh.param.four.FourExportListParam;
import wxgh.param.four.FourListParam;
import wxgh.param.four.FourRukuListParam;
import wxgh.sys.dao.four.FourDetailsDao;
import wxgh.sys.dao.four.FourProjectContentDao;
import wxgh.sys.dao.four.FourProjectDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Service
@Transactional(readOnly = true)
public class FourDetailsService {

    @Autowired
    private FourDetailsDao fourDetailsDao;
    @Autowired
    private PubDao generalDao;

    public List<FourDetails> getDetails(FourDetailsParam query) {
        return fourDetailsDao.getDetails(query);
    }

    public FourDetails getDetail(Integer id) {
        FourDetailsParam query = new FourDetailsParam();
        query.setId(id);
        return fourDetailsDao.getDetail(query);
    }

    /**
     * 获取推送信息的详情
     * @param id
     * @return
     */
    public FourDetails getDetailByPush(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_four_details f")
                .field("f.*")
                .field("d.name AS deptName")
                .field("fp.name AS fpName")
                .field("fpc.name AS fpcName")
                .field("m.name AS marketName")
                .field("u.name as username")
//                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, f.img_ids)) path")
//                .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, f.img_ids)) thumb")
                .join("t_four_project fp", "fp.id = f.fp_id")
                .join("t_four_project_content fpc", "fpc.id = f.fpc_id")
                .join("t_marketing m", "m.id = f.mid")
                .join("t_dept d", "d.deptid = m.deptid")
                .join("user u", "u.userid = f.user_id")
                .where("f.id = ?")
                .select();
        return generalDao.query(FourDetails.class, sql.build().sql(), id);
    }

    public List<FourDetails> getdeptStrList() {
        return fourDetailsDao.getdeptStrList();
    }

    public List<FourDetails> getList(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.getList(fourDetailsQuery);
    }

    public FourDetails getOne(FourDetailsParam fourDetailsParam) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_four_details f")
                .field("f.*")
                .field("d.name AS deptName")
                .field("fp.name AS fpName")
                .field("fpc.name AS fpcName")
                .field("m.name AS marketName")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, f.img_ids)) path")
                .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, f.img_ids)) thumb")
                .join("t_four_project fp", "fp.id = f.fp_id")
                .join("t_four_project_content fpc", "fpc.id = f.fpc_id")
                .join("t_marketing m", "m.id = f.mid")
                .join("t_dept d", "d.deptid = m.deptid");

        if (fourDetailsParam.getMid() != null)
            sql.where("f.mid = :mid");
        if (fourDetailsParam.getDeptId() != null)
            sql.where("FIND_IN_SET(f.dept_id, query_dept_child(:deptId)) > 0")
                    .where("m.deptid = :deptId");
        if (fourDetailsParam.getId() != null)
            sql.where("f.id = :id");
        if (fourDetailsParam.getDeptStr() != null)
            sql.where("f.dept_str = :deptStr");
        if (fourDetailsParam.getSearch() != null)
            sql.where("CONCAT(fpc.name,f.brand,f.model_name) LIKE concat('%', :search, '%')");
        if (fourDetailsParam.getFpcName() != null)
            sql.where("fpc.name = :fpcName");
        if (fourDetailsParam.getBrand() != null)
            sql.where("f.`brand` = :brand");
        if (fourDetailsParam.getCondit() != null)
            sql.where("f.`condit` = :condit");
        if (fourDetailsParam.getCondStr() != null)
            sql.where("f.`cond_str` = :condStr");
        if (fourDetailsParam.getStatus() != null)
            sql.where("f.status = :status");


        if (fourDetailsParam.getType() != null)
            switch (fourDetailsParam.getType()) {
                case 1:
                    sql.order("fpc.`name`");
                    break;
                case 2:
                    sql.order("f.`brand`");
                    break;
                case 3:
                    sql.order("f.`condit`");
                    break;
                case 4:
                    sql.order("f.`cond_str`");
                    break;
                default:
                    sql.order("f.buy_time");
                    break;
            }

        return generalDao.query(sql.select().build().sql(), fourDetailsParam, FourDetails.class);
//        return generalDao.queryPage(sql, fourDetailsParam, FourDetails.class);
//        return fourDetailsDao.getOne(fourDetailsQuery);
    }

    @Transactional
    public void addFD(FourDetailsData fourDetailsData) {
        fourDetailsDao.addFD(fourDetailsData);
    }

    public List<String> searchDeptByDeptName(String deptName) {
        String sql = "select dept_str as deptStr\n" +
                "from t_four_details where dept_str is not null \n" +
                "and dept_str like '%" + deptName + "%' group by dept_str;";
        return generalDao.queryList(String.class, sql);
    }

    /**
     * 获取四小台账用户入库数据
     *
     * @param param
     * @return
     */
    public List<FourDetailsRukuData> getRukuList(FourRukuListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("four_details fd")
                .field("fd.*")
                .field("fpc.name as projectContent")
                .field("fp.name as projectName")
                .field("d.name as companyName")
                .field("m.name as deptName")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, fd.imgs)) path")
                .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, fd.imgs)) thumb")
                .field("u.name as username")
                .join("four_project fp", "fp.id = fd.fp_id")
                .join("four_project_content fpc", "fpc.id = fd.fpc_id")
                .join("dept d", "d.deptid = fd.dept_id")
                .join("marketing m", "fd.mid = m.id")
                .join("user u", "u.userid = fd.user_id")
                .where("(fd.is_export = 0 or fd.is_export = '' or fd.is_export is null)");
        if(param.getStatus() != null)
            sql.where("fd.status = :status");
        else
            sql.where("fd.status = 0");
        if (param.getDeptid() != null)
            sql.where("fd.dept_id = :deptid");
        if (param.getFpcId() != null)
            sql.where("fd.fpc_id = :fpcId");
        if (param.getFpId() != null)
            sql.where("fd.fp_id = :fpId");
        if (param.getMid() != null)
            sql.where("fd.mid = :mid");
        return generalDao.queryPage(sql, param, FourDetailsRukuData.class);
    }

    /**
     * 获取导入的四小台账数据
     *
     * @param param
     * @return
     */
    public List<FourDetailsExportData> getExportList(FourExportListParam param) {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("four_details fd")
                .field("fd.*")
                .field("fpc.name as projectContent")
                .field("fp.name as projectName")
                .field("d.name as companyName")
                .field("m.name as deptName")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, fd.img_ids)) path")
                .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, fd.img_ids)) thumb")
                .join("four_project fp", "fp.id = fd.fp_id")
                .join("four_project_content fpc", "fpc.id = fd.fpc_id")
                .join("dept d", "d.deptid = fd.dept_id")
                .join("marketing m", "fd.mid = m.id")
                .where("fd.is_export = 1");
        if (param.getImg() != null)
            if (param.getImg().equals(1))
                sql.where("fd.img_ids is not null");
            else if (param.getImg().equals(2))
                sql.where("fd.img_ids is null");
        if (param.getDeptid() != null)
            sql.where("fd.dept_id = :deptid");
        if (param.getFpcId() != null)
            sql.where("fd.fpc_id = :fpcId");
        if (param.getFpId() != null)
            sql.where("fd.fp_id = :fpId");
        if (param.getMid() != null)
            sql.where("fd.mid = :mid");
        sql.order("fd.id", Order.Type.DESC);
        return generalDao.queryPage(sql, param, FourDetailsExportData.class);
    }

    @Transactional
    public void deleteExport(String id) {
        String slq = SQL.deleteByIds("t_four_details", id);
        generalDao.execute(slq);
    }

    @Transactional
    public void savePic(Integer id, String img) {
        SQL sql = new SQL.SqlBuilder()
                .set("img_ids = ?")
                .where("id = ?")
                .update("four_details")
                .build();
        generalDao.execute(sql.sql(), img, id);
    }

    public List<ListInfo> searchItemByName(String name, String deptStr) {
        String sql = "select * from (select d.id,d.buy_time as time,\n" +
                "fp.name as fpName,\n" +
                "fpc.name as fpcName\n" +
                "from t_four_details d \n" +
                "join t_four_project fp on fp.id=d.fp_id \n" +
                "join t_four_project_content fpc on d.fpc_id=fpc.id\n" +
                "where d.dept_str='" + deptStr + "' group by d.fpc_id,d.fp_id,d.brand order by d.buy_time) as x \n" +
                "where x.fpName like '%" + name + "%' or x.fpcName like '%" + name + "%';";
        return generalDao.queryList(ListInfo.class, sql);
    }

    public List<FourDetails> getListWithMarketing(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.getListWithMarketing(fourDetailsQuery);
    }

    @Transactional
    public void apply(Integer id, Integer status){
        String sql = "UPDATE t_four_details SET status = ? WHERE id = ?";
        generalDao.execute(sql, status, id);
    }

    @Transactional
    public void addFourDetail(FourDetails d) {

        if (d.getFpcId() == null && !StrUtils.empty(d.getFpcName())) {

            String sql = "select id from t_four_project_content where fp_id=? and name=? order by fp_id limit 1";

            FourProjectContent fourProjectContent = new FourProjectContent();
            fourProjectContent.setName(d.getFpcName());
            fourProjectContent.setFpId(d.getFpId());

            Integer id1 = generalDao.query(Integer.class, sql, fourProjectContent.getFpId(), fourProjectContent.getName());

            if (id1 == null) {
                fourProjectContentDao.save(fourProjectContent);
            } else {
                fourProjectContent.setId(id1);
            }
            d.setFpcId(fourProjectContent.getId());
        }
        fourDetailsDao.save(d);
    }

    public List<DetailInfo> groupDetail(Integer deptid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "select pc.name as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_marketing m on m.id = d.mid\n" +
                    "join t_four_project_content pc on d.fpc_id = pc.id\n" +
                    "where m.deptid = ? and d.status=1\n" +
                    "group by pc.name order by pc.name";
        } else if (type == 2) { //品牌
            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_marketing m on m.id = d.mid\n" +
                    "where m.deptid = ? and d.status=1\n" +
                    "group by d.brand order by d.brand";
        } else if (type == 3) { //设备情况
            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_marketing m on m.id = d.mid\n" +
                    "where m.deptid = ? and d.status=1\n" +
                    "group by d.condit order by d.condit";
        } else if (type == 4) { //资产所属
            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_marketing m on m.id = d.mid\n" +
                    "where m.deptid = ? and d.status=1\n" +
                    "group by d.cond_str order by d.cond_str";
        }
        return generalDao.queryList(DetailInfo.class, sql, deptid);
    }

    public Integer groupNumsDetail(Integer deptid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "SELECT COUNT(*) FROM t_four_details f LEFT JOIN t_marketing m ON f.`mid` = m.`id` WHERE m.`deptid` = ?";
        }
//        else if (type == 2) { //品牌
//            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "join t_marketing m on m.id = d.mid\n" +
//                    "where m.deptid = ? and d.status=1\n" +
//                    "group by d.brand order by d.brand";
//        } else if (type == 3) { //设备情况
//            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "join t_marketing m on m.id = d.mid\n" +
//                    "where m.deptid = ? and d.status=1\n" +
//                    "group by d.condit order by d.condit";
//        } else if (type == 4) { //资产所属
//            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "join t_marketing m on m.id = d.mid\n" +
//                    "where m.deptid = ? and d.status=1\n" +
//                    "group by d.cond_str order by d.cond_str";
//        }
        return generalDao.query(Integer.class, sql, deptid);
    }

    public List<DetailInfo> groupDetailByMid(Integer mid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "select pc.name as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_four_project_content pc on d.fpc_id = pc.id\n" +
                    "where d.mid = ? and d.status=1\n" +
                    "group by pc.name order by pc.name";
        } else if (type == 2) { //品牌
            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1\n" +
                    "group by d.brand order by d.brand";
        } else if (type == 3) { //设备情况
            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1\n" +
                    "group by d.condit order by d.condit";
        } else if (type == 4) { //资产所属
            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1\n" +
                    "group by d.cond_str order by d.cond_str";
        }
        return generalDao.queryList(DetailInfo.class, sql, mid);
    }

    public Integer groupNumsByMid(Integer mid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "SELECT COUNT(*) FROM t_four_details f WHERE f.mid = ?";
        }
//        else if (type == 2) { //品牌
//            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1\n" +
//                    "group by d.brand order by d.brand";
//        } else if (type == 3) { //设备情况
//            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1\n" +
//                    "group by d.condit order by d.condit";
//        } else if (type == 4) { //资产所属
//            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1\n" +
//                    "group by d.cond_str order by d.cond_str";
//        }
        return generalDao.query(Integer.class, sql, mid);
    }

    public List<DetailInfo> groupDetailByMidFpid(Integer mid, Integer type, Integer fpId) {
        String sql = "";
        if (type == 1) { //名称
            sql = "select pc.name as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_four_project_content pc on d.fpc_id = pc.id\n" +
                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
                    "group by pc.name order by pc.name";
        } else if (type == 2) { //品牌
            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
                    "group by d.brand order by d.brand";
        } else if (type == 3) { //设备情况
            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
                    "group by d.condit order by d.condit";
        } else if (type == 4) { //资产所属
            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
                    "group by d.cond_str order by d.cond_str";
        }
        return generalDao.queryList(DetailInfo.class, sql, mid, fpId);
    }

    public Integer groupNumsByMidFpid(Integer mid, Integer type, Integer fpId) {
        String sql = "";
        if (type == 1) { //名称
            sql = "SELECT COUNT(*) FROM t_four_details f WHERE f.mid = ? AND f.fp_id = ?";
        }
//        else if (type == 2) { //品牌
//            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
//                    "group by d.brand order by d.brand";
//        } else if (type == 3) { //设备情况
//            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
//                    "group by d.condit order by d.condit";
//        } else if (type == 4) { //资产所属
//            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.mid = ? and d.status=1 and d.fp_id = ? \n" +
//                    "group by d.cond_str order by d.cond_str";
//        }
        return generalDao.query(Integer.class, sql, mid, fpId);
    }

    public List<DetailInfo> groupDetailByFoshan(Integer foshanDeptid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "select pc.name as name,sum(d.numb) as numb from t_four_details d\n" +
                    "join t_four_project_content pc on d.fpc_id = pc.id where d.status=1\n" +
                    "group by pc.name order by pc.name";
        } else if (type == 2) { //品牌
            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.status=1 group by d.brand order by d.brand";
        } else if (type == 3) { //设备情况
            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.status=1 group by d.condit order by d.condit";
        } else if (type == 4) { //资产所属
            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
                    "where d.status=1 group by d.cond_str order by d.cond_str";
        }
        return generalDao.queryList(DetailInfo.class, sql);
    }

    public Integer getNumsByFoshan(Integer foshanDeptid, Integer type) {
        String sql = "";
        if (type == 1) { //名称
            sql = "SELECT COUNT(*) FROM t_four_details WHERE STATUS = 1";
        }
//        else if (type == 2) { //品牌
//            sql = "select d.brand as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.status=1 group by d.brand order by d.brand";
//        } else if (type == 3) { //设备情况
//            sql = "select d.condit as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.status=1 group by d.condit order by d.condit";
//        } else if (type == 4) { //资产所属
//            sql = "select d.cond_str as name,sum(d.numb) as numb from t_four_details d\n" +
//                    "where d.status=1 group by d.cond_str order by d.cond_str";
//        }
        return generalDao.query(Integer.class, sql);
    }

    public List<FourDetails> getDetails(Integer deptid, Integer type, String name) {
        String sql = "";
        if (type == 1) {
            sql = "SELECT f.*, fpc.name AS fpcName\n" +
                    "FROM t_four_details f\n" +
                    "JOIN t_four_project fp ON fp.id = f.fp_id\n" +
                    "JOIN t_four_project_content fpc ON fpc.id = f.fpc_id\n" +
                    "JOIN t_marketing m ON m.id = f.mid\n" +
                    "where m.deptid = ? and fpc.name=?\n" +
                    "order by fpc.name";
        } else if (type == 2) {
            sql = "SELECT f.*, fpc.name AS fpcName\n" +
                    "FROM t_four_details f\n" +
                    "JOIN t_four_project fp ON fp.id = f.fp_id\n" +
                    "JOIN t_four_project_content fpc ON fpc.id = f.fpc_id\n" +
                    "JOIN t_marketing m ON m.id = f.mid\n" +
                    "where m.deptid = ? and f.brand=?\n" +
                    "order by fpc.name";
        } else if (type == 3) {
            sql = "SELECT f.*, fpc.name AS fpcName\n" +
                    "FROM t_four_details f\n" +
                    "JOIN t_four_project fp ON fp.id = f.fp_id\n" +
                    "JOIN t_four_project_content fpc ON fpc.id = f.fpc_id\n" +
                    "JOIN t_marketing m ON m.id = f.mid\n" +
                    "where m.deptid = ? and f.condit=?\n" +
                    "order by fpc.name";
        } else if (type == 4) {
            sql = "SELECT f.*, fpc.name AS fpcName\n" +
                    "FROM t_four_details f\n" +
                    "JOIN t_four_project fp ON fp.id = f.fp_id\n" +
                    "JOIN t_four_project_content fpc ON fpc.id = f.fpc_id\n" +
                    "JOIN t_marketing m ON m.id = f.mid\n" +
                    "where m.deptid = ? and f.cond_str=?\n" +
                    "order by fpc.name";
        }
        return generalDao.queryList(FourDetails.class, sql, deptid, name);
    }

    public List<FourDetails> getDetails(FourListParam query) {
        return fourDetailsDao.getDetails(query);
    }

    // 获取更换的设备
    public List<FourDetails> getRepair(FourListParam param){

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_four_details f")
                .field("f.*, d.name AS deptName, fp.name AS fpName, fpc.name AS fpcName,m.name as marketName")
                .join("t_four_project fp", "fp.id = f.fp_id")
                .join("t_four_project_content fpc", "fpc.id = f.fpc_id")
                .join("t_marketing m", "m.id = f.mid")
                .join("t_dept d", "d.deptid = m.deptid")
                .where("f.id not in (select de_id from t_four_propagate where mid = :mid)");
                // 排除已经添加的更换信息
//                .join("t_four_propagate p", "p.de_id = fp.id and p.mid = fp.mid");

        if(param.getDeptid() != null)
            sql.where("m.deptid = :deptid");
        if(param.getMid() != null)
            sql.where("f.mid = :mid");
        if(param.getFpid() != null)
            sql.where("f.fp_id = :fpid");
        if(param.getFpcName() != null)
            sql.where("fpc.name = :fpcName");
        if(param.getBrand() != null)
            sql.where("f.brand = :brand");
        if(param.getCondit() != null)
            sql.where("f.condit = :condit");
        if(param.getBelong() != null)
            sql.where("f.cond_str = :belong");
        if(param.getSearchKey() != null)
            sql.where("(fpc.name LIKE :searchKey OR f.brand LIKE :searchKey:)");
        sql.order("fpc.name");
        return generalDao.queryList(sql.select().build().sql(), param, FourDetails.class);
    }

    public Integer getCount(FourListParam query) {
        return fourDetailsDao.getCount(query);
    }

    @Transactional
    public Integer delete(Integer id) {
        fourDetailsDao.delete(id);
        return -1;
    }

    public List<String> groupField(String field) {
        String sql = "select " + field + " from t_four_details GROUP BY " + field;
        return generalDao.queryList(String.class, sql);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer updateDetails(FourDetails details) {
        return fourDetailsDao.updateDetails(details);
    }

    public List<DetailInfo> groupDetailByListIn(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.groupDetailByListIn(fourDetailsQuery);
    }

    public FourDetails getImgs(Integer id) {
        String sql = "select imgs, img_ids from t_four_details where id = ?";
        return generalDao.query(FourDetails.class, sql, id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void addDetails(List<FourDetails> detailses) {
        String sql = "select id from t_four_project where name=?";

        String sql1 = "select id from t_four_project_content where fp_id=? and name=? order by fp_id limit 1";

        for (FourDetails d : detailses) {
            if (!StrUtils.empty(d.getFpcName())) {

                FourProject fourProject = new FourProject();
                fourProject.setName(d.getFpName());

                Integer id = generalDao.query(Integer.class, sql, fourProject.getName());
                if (id == null) {
                    fourProjectDao.save(fourProject);
                } else {
                    fourProject.setId(id);
                }

                FourProjectContent fourProjectContent = new FourProjectContent();
                fourProjectContent.setName(d.getFpcName());
                fourProjectContent.setFpId(fourProject.getId());

                Integer id1 = generalDao.query(Integer.class, sql1, fourProjectContent.getFpId(), fourProjectContent.getName());

                if (id1 == null) {
                    fourProjectContentDao.save(fourProjectContent);
                } else {
                    fourProjectContent.setId(id1);
                }

                d.setFpId(fourProject.getId());
                d.setFpcId(fourProjectContent.getId());
            }

            fourDetailsDao.updateDetails(d);
        }
    }

    public FourDetails get(Integer id) {
        return fourDetailsDao.get(id);
    }


    public List<FourDetails> getMarketFour(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.getMarketFour(fourDetailsQuery);
    }

    public List<FourDetails> getPerFourProject(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.getPerFourProject(fourDetailsQuery);
    }

    public FourDetails getMarketName(FourDetailsParam fourDetailsQuery) {
        return fourDetailsDao.getMarketName(fourDetailsQuery);
    }

    @Autowired
    private FourProjectDao fourProjectDao;

    @Autowired
    private FourProjectContentDao fourProjectContentDao;

}


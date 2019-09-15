package wxgh.sys.service.admin.party.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.party.member.Branch;
import wxgh.data.party.member.PartyDept;
import wxgh.data.pub.NameValue;

import java.util.List;

/**
 * Created by Sheng on 2017/9/5.
 */
@Service
@Transactional(readOnly = true)
public class PartyDeptService {

    @Autowired
    private PubDao pubDao;

    public List<PartyDept> deptList(Integer parentid) {
        String sql = "select d.name as deptname, d.parentid, d.id as deptid," +
                "(select count(*)>0 from t_party_dept where parentid = d.id) as hasChild " +
                "from t_party_dept d where d.parentid = ?";
        List<PartyDept> list = pubDao.queryList(PartyDept.class,sql,parentid);
        if(list.size() == 0){
            String sql2 = "select d.name as deptname,d.parentid,d.id as deptid from t_party_dept d where d.id = ?";
            List<PartyDept> list2 = pubDao.queryList(PartyDept.class, sql2, parentid);
            return list2;
        }else{
            return list;
        }
    }

    public String get_dept_parent(Integer parentid){
        String sql = "select name from t_party_dept where id = ?";
        return pubDao.query(String.class,sql,parentid);
    }

    public List<Branch> getParty(Integer id) {
        String sql = "select d.id,d.name as branch from t_party_dept d where d.parentid = ? and d.is_party = 0";
        return pubDao.queryList(Branch.class, sql, id);
    }

    public List<Branch> getPartysByid(Integer id) {
        String sql = "select d.id,d.name as branch from t_party_dept d where d.id = ?";
        return pubDao.queryList(Branch.class, sql, id);
    }

    public List<NameValue> getPartyByParentId(Integer parentid) {
        if (parentid == null) parentid = 1;
        String sql = "select name, id as value from t_party_dept where parentid = ?";
        return pubDao.queryList(NameValue.class, sql, parentid);
    }

    public wxgh.entity.party.member.PartyDept get(Integer id) {
        String sql = "select * from t_party_dept where id=?";
        return pubDao.query(wxgh.entity.party.member.PartyDept.class, sql, id);
    }

    public List<PartyDept> partyDeptList(Integer parentid) {
        if (parentid == 1) {
            String sql = "select id as deptid,name as deptname,parentid from t_party_dept where is_party=? and parentid != 0";
            return pubDao.queryList(PartyDept.class, sql, 1);
        } else {
            String sql = "select id as deptid,name as deptname,parentid from t_party_dept where parentid=? and is_party = 0";
            return pubDao.queryList(PartyDept.class, sql, parentid);
        }
    }

    public List<PartyDept> allDeptList(){
        String sql = "select id as deptid,name as deptname from t_party_dept where parentid != 0";
        return pubDao.queryList(PartyDept.class, sql);
    }

    public Integer getCount(Integer party_id) {
        String sql = "select count(*) from t_party_dept_user where party_id=?";
        return pubDao.query(Integer.class,sql,party_id);
    }

    public Integer getParentCount(Integer party_id){
        String sql = "select count(*) from t_party_dept_user tu inner join t_party_dept td\n" +
                "on tu.party_id=td.id";
        if(party_id==2){
            sql+=" where td.parentid=2";
        }
        if(party_id==3){
            sql+=" where td.parentid=3";
        }
        if(party_id==4){
            sql+=" where td.parentid=4";
        }
        if(party_id==5){
            sql+=" where td.parentid=5";
        }
        if(party_id==6){
            sql+=" where td.parentid=6";
        }
        if(party_id==29){
            sql+=" where td.parentid=29";
        }
        return pubDao.query(Integer.class,sql);
    }
}

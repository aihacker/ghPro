package wxgh.sys.service.weixin.party.branch;
/*

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.branch.PartyLedger;
import wxgh.entity.party.member.PartyDept;

import java.util.Date;*/

/**
* @Version 1.0
* --------------------------------------------------------- *
* @Description >_
* --------------------------------------------------------- *
* @Author zrr
* --------------------------------------------------------- *
* @Email <16511660@qq.com>
* --------------------------------------------------------- *
* @Date 2018-06-7  15:08
* --------------------------------------------------------- *
*/
/*@Service
@Transactional(readOnly = true)*/
public class PartyLedgerService {
   /* @Autowired
    private PubDao pubDao;

    @Transactional
    *//**
     *  添加发展党员
     * *//*
    public Integer addLedger(PartyLedger partyLedger) {
        partyLedger.setUserid(UserSession.getUserid());
        partyLedger.setCreateTime(new Date());
        partyLedger.setStatus(0);
        String sql = new SQL.SqlBuilder()
                .table("party_ledger")
                .field("name, sex, birthday, dept,train_people,conditions,add_time,type,create_time,party_id,group_id,file_id,status,party_receive,grade,"
                        +"honor,recom_sort,national,pass_time")
                .value(":name,:sex,:birthday,:dept,:trainPeople,:conditions,:addTime,:type,:createTime,:partyId,:groupId,:fileId,:status,:partyReceive,:grade,"
                        +":honor,:recomSort,:national,:passTime")
                .insert()
                .build()
                .sql();
        return pubDao.insertAndGetKey(sql, partyLedger);
    }


    @Transactional
    *//**
     *  查询部门信息
     * *//*
    public PartyDept getLedger(String userid) {
        //通过用户Id查询支部名称以及支部Id
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept a")
                .field("a.id,a.name,a.parentid,a.group_id groupId,a.is_party isParty")
                .join("t_party_dept_user b", "a.id = b.party_id", Join.Type.LEFT)
                .where("b.userid = ?")
                .select()
                .build()
                .sql();
        System.out.println(sql);
        PartyDept pd =  pubDao.query(PartyDept.class, sql,userid);
        return pd;
    }*/
}

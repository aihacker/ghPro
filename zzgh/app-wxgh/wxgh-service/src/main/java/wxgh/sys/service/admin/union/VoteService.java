package wxgh.sys.service.admin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.union.vote.VoteList;
import wxgh.param.union.vote.VoteParam;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-15
 **/
@Service
@Transactional(readOnly = true)
public class VoteService {

    public List<VoteList> voteList(VoteParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder();
        sql.table("voted v").join("user u", "u.userid = v.userid")
                .join("dept d", "d.deptid = v.deptid")
                .field("v.id, v.theme, v.start_time, v.effective_time, v.create_time, v.`status`, v.userid")
                .field("u.`name` as username, d.name as deptname");
        //.field("o.id as o_id, o.options as o_options, o.ticketNum");
        if (param.getStatus() != null) {
            sql.where("v.status = :status");
        }


        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }
        /*sql.join("voted_option o", "v.id = o.voteId");

        final Map<Integer, VoteList> map = new LinkedHashMap<>();
        pubDao.queryList(sql.select().build().sql(), param, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Integer voteId = rs.getInt("id");
                VoteList voteList = map.getOrDefault(voteId, null);

                VoteOption voteOption = new VoteOption();
                voteOption.setId(rs.getInt("o_id"));
                voteOption.setOptions(rs.getString("o_options"));
                voteOption.setTicketNum(rs.getInt("ticketNum"));

                if (voteList == null) {
                    voteList = new VoteList();
                    voteList.setId(voteId);
                    voteList.setTheme(rs.getString("theme"));
                    voteList.setStatus(rs.getInt("status"));
                    voteList.setCreateTime(rs.getDate("create_time"));
                    voteList.setDeptname(rs.getString("deptname"));
                    voteList.setEffectiveTime(rs.getDate("effective_time"));
                    voteList.setStartTime(rs.getDate("start_time"));
                    voteList.setUserid(rs.getString("userid"));
                    voteList.setUsername(rs.getString("username"));

                    List<VoteOption> options = new ArrayList<>();
                    voteList.setOptions(options);

                    options.add(voteOption);
                    map.put(voteId, voteList);
                } else {
                    voteList.getOptions().add(voteOption);
                }
            }
        });

        List<VoteList> voteLists = new ArrayList<>();
        for (Map.Entry<Integer, VoteList> entry : map.entrySet()) {
            voteLists.add(entry.getValue());
        }*/
        return pubDao.queryList(sql.select().build().sql(), param, VoteList.class);
    }

    @Transactional
    public void apply(Integer id, Integer status) {
        String sql = new SQL.SqlBuilder()
                .table("voted")
                .set("status = ?")
                .where("id = ?")
                .update()
                .build().sql();
        pubDao.execute(sql, status, id);
    }

    @Autowired
    private PubDao pubDao;


}

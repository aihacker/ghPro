package wxgh.sys.service.weixin.party.beauty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.sys.dao.party.beauty.WorkFileDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */
@Service
@Transactional(readOnly = true)
public class WorkFileService {

    @Autowired
    private WorkFileDao workFileDao;

    @Autowired
    private PubDao pubDao;

    @Transactional
    public void save(WorkFile workFile) {
        workFile.setStatus(1);
        workFile.setAddTime(new Date());
        workFileDao.save(workFile);
    }

    @Transactional
    public void saveList(List<WorkFile> files) {
        for (WorkFile file : files) {
            save(file);
        }
    }

    public List<WorkFile> getFiles(Integer workId, Integer type) {
        String sql = "select id, thumb, path from t_work_file where work_id=? and status=? and type=?";
        return pubDao.queryList(WorkFile.class, sql, workId, 1, type);
    }

    public Map<Integer, List<WorkFile>> getMatchFiles(Integer matchId) {
        String sql = "select f.id, f.thumb, f.path, f.work_id from t_work_file f\n" +
                "join t_sheying_match_join j on f.work_id = j.id\n" +
                "where j.mid = ? and f.status=?";
        List<WorkFile> files = pubDao.queryList(WorkFile.class, sql, matchId, 1);
        Map<Integer, List<WorkFile>> fileMap = new HashMap<>();
        for (WorkFile f : files) {
            List<WorkFile> tmp = fileMap.getOrDefault(f.getWorkId(), null);
            if (tmp == null) {
                tmp = new ArrayList<>();
                tmp.add(f);
                fileMap.put(f.getWorkId(), tmp);
            } else {
                fileMap.get(f.getWorkId()).add(f);
            }
        }
        return fileMap;
    }

    @Transactional
    public void delete(Integer id) {
        workFileDao.delete(id);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_work_file set status=? where id=?";
        pubDao.execute(sql, status, id);
    }

    public WorkFile getFile(Integer id) {
        return workFileDao.get(id);
    }

    
}

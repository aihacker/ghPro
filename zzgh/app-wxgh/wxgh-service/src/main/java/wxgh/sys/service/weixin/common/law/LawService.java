package wxgh.sys.service.weixin.common.law;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.law.Law;
import wxgh.sys.dao.common.law.LawDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 14:33
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class LawService {

    @Autowired
    private LawDao lawDao;

    public List<Law> getLaws() {
        return lawDao.getFieldLaws();
    }

    public Law getLaw(Integer id) {
        return lawDao.get(id);
    }
}


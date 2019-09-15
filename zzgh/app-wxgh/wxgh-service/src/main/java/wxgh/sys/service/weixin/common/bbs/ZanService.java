package wxgh.sys.service.weixin.common.bbs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.Zan;
import wxgh.sys.dao.common.bbs.ZanDao;

import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 11:10
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class ZanService {

    @Autowired
    private ZanDao zanDao;

    @Transactional
    public void save(Zan zan) {
        zan.setAddTime(new Date());
        zanDao.save(zan);
    }

}


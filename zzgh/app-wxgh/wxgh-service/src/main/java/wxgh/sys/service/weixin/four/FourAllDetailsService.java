package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.four.FourAllDetails;
import wxgh.param.four.FourAllDetailsParam;
import wxgh.sys.dao.four.FourAllDetailsDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Service
@Transactional(readOnly = true)
public class FourAllDetailsService{

    @Autowired
    private FourAllDetailsDao fourAllDetailsDao;
//    @Autowired
//    private GeneralDao generalDao;

    public FourAllDetails getAllDetails(FourAllDetailsParam query) {
        return fourAllDetailsDao.getAllDetails(query);
    }

    public List<FourAllDetails> getList(FourAllDetailsParam query) {
        return fourAllDetailsDao.getList(query);
    }

}


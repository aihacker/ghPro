package wxgh.sys.service.weixin.four;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.four.FourSmall;
import wxgh.param.four.FourSmallParam;
import wxgh.sys.dao.four.FourSmallDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/17.
 * <p>
 * Date： 2016/8/17
 */
@Service
@Transactional(readOnly = true)
public class FourSmallService{

    public List<FourSmall> getSmalls(FourSmallParam query) {
        return fourSmallDao.getSmalls(query);
    }

    public FourSmall getSmall(Integer id) {
        FourSmallParam query = new FourSmallParam();
        query.setId(id);
        return fourSmallDao.getSmall(query);
    }

    @Transactional
    public Integer addSmall(FourSmall fourSmall) {
        fourSmallDao.save(fourSmall);
        return fourSmall.getId();
    }

    @Autowired
    private FourSmallDao fourSmallDao;
}

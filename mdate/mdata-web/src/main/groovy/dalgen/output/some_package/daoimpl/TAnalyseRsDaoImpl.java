/*
 * @(#) TAnalyseRsDaoImpl.java Jun 17, 2016
 * Copyright 2016 GPDI. All right reserved.
 */
package some_package.daoimpl;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import some_package.dao.TAnalyseRsDao;
import some_package.entity.TAnalyseRs;

/**
* @author zoe
* @version 1.0 Jun 17, 2016
*/
@Repository
@SuppressWarnings("unchecked")
public class TAnalyseRsDaoImpl extends MyBatisDao<TAnalyseRs> implements TAnalyseRsDao {

	public TAnalyseRsDaoImpl() {
		super(TAnalyseRs.class);
	}

}

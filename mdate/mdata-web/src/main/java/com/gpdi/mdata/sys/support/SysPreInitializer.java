package com.gpdi.mdata.sys.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import pub.dao.mybatis.MyBatisDao;
import pub.dao.sql.dialect.DialectManager;
import pub.dao.sql.dialect.MysqlDialect;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 2015/6/22
 */
public class SysPreInitializer implements InitializingBean {

    Logger logger = LogManager.getLogger(SysPreInitializer.class.getName());

    @Override
    public void afterPropertiesSet() {

//        logger.debug("HELLO@@@");
        //System.out.println(" ---- SysPreInitializer ----");
        DialectManager.currentDialect = new MysqlDialect();
        //MyBatisDao.defaultValueGenerator = new IdentityGenerator();
        MyBatisDao.generators.put("uuid", new UuidGenerator());

    }


}

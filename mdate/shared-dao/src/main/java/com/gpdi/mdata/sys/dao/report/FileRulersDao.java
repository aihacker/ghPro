package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.FileRulers;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:保存招标规则文件
 * @author: WangXiaoGang
 * @data: Created in 2018/10/13 11:24
 * @modifier:
 */
@Repository
public class FileRulersDao extends MyBatisDao<FileRulers>{
    public FileRulersDao() {
        super(FileRulers.class);
    }
}

package com.gpdi.mdata.sys.service.reportform.cleanInventory;

import com.gpdi.mdata.sys.entity.report.CleanResult;

import java.util.List;

/**
 *即时清结机器人回调及储存数据
 */
public interface CleanCallBackService {

    /**
     * 查询及保存回调数据
     * @param code
     * @return
     */
    List<CleanResult> getCompanys(int code);//根据id获取数据
    boolean  saveRsult(List<CleanResult> cleanResults);

}

package com.gpdi.mdata.sys.entity.system;

import java.util.List;

/**
 * Created by Administrator on 2017-05-17.
 */
public class QueryData {
    private  Integer parentId;
    private  String moduleCode;
    private List<Integer> list;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}

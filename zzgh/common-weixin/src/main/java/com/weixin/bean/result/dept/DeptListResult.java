package com.weixin.bean.result.dept;

import com.weixin.bean.ErrResult;
import com.weixin.bean.dept.Dept;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class DeptListResult extends ErrResult {

    private List<Dept> department;

    public List<Dept> getDepartment() {
        return department;
    }

    public void setDepartment(List<Dept> department) {
        this.department = department;
    }
}

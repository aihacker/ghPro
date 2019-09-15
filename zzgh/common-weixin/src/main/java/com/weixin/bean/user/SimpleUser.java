package com.weixin.bean.user;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SimpleUser extends SmallUser {

    private List<Integer> department;

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }
}

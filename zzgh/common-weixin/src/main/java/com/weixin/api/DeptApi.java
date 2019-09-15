package com.weixin.api;

import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.dept.Dept;
import com.weixin.bean.result.dept.DeptAddResult;
import com.weixin.bean.result.dept.DeptListResult;
import com.weixin.utils.WeixinHttp;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class DeptApi {

    /**
     * 创建部门
     *
     * @param dept
     * @throws WeixinException
     */
    public static Integer create(Dept dept) throws WeixinException {
        String url = Weixin.getTokenURL("department/create");
        DeptAddResult result = WeixinHttp.post(url, dept, DeptAddResult.class);

        return result.getId();
    }

    /**
     * 更新部门
     *
     * @param dept
     * @throws WeixinException
     */
    public static void update(Dept dept) throws WeixinException {
        String url = Weixin.getTokenURL("department/update");
        WeixinHttp.post(url, dept, ErrResult.class);
    }

    /**
     * 删除部门
     *
     * @param deptid
     * @throws WeixinException
     */
    public static void delete(Integer deptid) throws WeixinException {
        String url = Weixin.getTokenURL("department/delete?id=%d", deptid);
        WeixinHttp.get(url, ErrResult.class);
    }

    /**
     * 获取部门列表
     *
     * @param deptid 部门id。获取指定部门及其下的子部门
     * @return
     * @throws WeixinException
     */
    public static List<Dept> list(Integer deptid) throws WeixinException {
        String url = Weixin.getTokenURL("department/list");
        if (deptid != null) {
            url += "&id=" + deptid;
        }
        DeptListResult result = WeixinHttp.get(url, DeptListResult.class);
        return result.getDepartment();
    }
}

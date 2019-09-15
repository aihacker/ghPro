package wxgh.app.sys.task.impl;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.DeptApi;
import com.weixin.api.UserApi;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.sys.task.UserAsync;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.sys.service.wxadmin.union.DeptUserService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
@Component
public class UserAsyncImpl implements UserAsync {

    @Override
    public void updateUser(User user) {
        try {
            UserApi.update(userToWx(user));
        } catch (WeixinException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user) {
        try {
            UserApi.create(userToWx(user));
        } catch (Exception e) {
            /*processError(e);*/
        }
    }

    @Override
    public void delUser(String id) {
        try {
            List<String> userids = deptUserService.getUserids(id);
            if (!TypeUtils.empty(userids)) {
                UserApi.batchdelete(userids);
            }
        } catch (WeixinException e) {
        }
    }

    @Override
    public void delDept(Integer id) {
        try {
            DeptApi.delete(id);
        } catch (WeixinException e) {
            processError(e);
        }
    }

    @Override
    public void updateDept(Dept dept) {
        com.weixin.bean.dept.Dept wxDept = new com.weixin.bean.dept.Dept();
        wxDept.setName(dept.getName());
        wxDept.setOrder(dept.getOrder());
        wxDept.setId(dept.getDeptid());
        try {
            DeptApi.update(wxDept);
        } catch (WeixinException e) {
            processError(e);
        }
    }

    private com.weixin.bean.user.User userToWx(User user) {
        com.weixin.bean.user.User u = new com.weixin.bean.user.User();
        u.setEmail(user.getEmail());
        u.setName(user.getName());
        u.setUserid(user.getUserid());
        u.setMobile(user.getMobile());
        u.setPosition(user.getPosition());

        List<Integer> deptIds = new ArrayList<>();
        deptIds.add(deptUserService.getDeptId(user.getDeptid()));
        u.setDepartment(deptIds);
        return u;
    }

    private void processError(WeixinException e) {
        try {
            File file = new File(com.libs.common.file.FileUtils.getTemp(), "wxgh_user_error.log");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file, e.getMessage(), "UTF-8", true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Autowired
    private DeptUserService deptUserService;
}
